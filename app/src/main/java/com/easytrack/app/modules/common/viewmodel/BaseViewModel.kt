package com.easytrack.app.modules.common.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.easytrack.app.BuildConfig
import com.easytrack.app.modules.common.data.*
import com.easytrack.app.utils.rx.RxSchedulers
import com.easytrack.app.utils.ui.Event
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

/**
 * @author EEV
 *
 */
abstract class BaseViewModel<P : InitialDataProvider>(
    protected val schedulers: RxSchedulers
) : ViewModel(), IBaseViewModel<P> {

    private var inited = false
    protected val uiState = MutableLiveData<UIState>()
    protected val showErrorConnectionDialog = MutableLiveData<Event<Boolean>>()

    override fun getUIState() = uiState
    override fun getShowErrorConnectionDialogLiveData(): LiveData<Event<Boolean>> =
        showErrorConnectionDialog

    protected val subscriptions = CompositeDisposable()

    init {
        setDefaultUIState()
    }

    final override fun setDefaultUIState() {
        uiState.value = EmptyUIState
    }

    /**
     * Проверяем был ли уже вызван метод.
     * Достаем данные из провайдера данных.
     * Вызываем метод onInitialized() для активации стартового состояния экрана.
     */
    final override fun provideInitialData(provider: P) {
        if (inited) {
            return
        }

        try {
            extractInitialData(provider)
        } catch (e: KotlinNullPointerException) {
            uiState.value = ErrorUIState(IllegalStateException("Model wasn't initialized"))
            return
        }

        inited = true
        onInitialized()
    }

    /**
     * Достаем данные из
     * @param provider Провайдер данных, переданный с предыдущего экрана
     */
    @Throws(KotlinNullPointerException::class)
    protected open fun extractInitialData(provider: P) {
        // Empty
    }

    /**
     * Проверяем что начальное состояние экрана корректное.
     * Вызываем метод init() для активации стартового состояния экрана.
     */
    private fun onInitialized() {
        if (uiState.value is ErrorUIState) {
            return
        } else {
            initialize()
        }
    }

    /**
     * Активируем стартовое состояние экрана.
     */
    protected open fun initialize() {

    }

    protected fun simpleAction(
        completable: Completable
    )
    {
        subscriptions.add(completable.compose(schedulers.getIOToMainTransformerCompletable())
            .subscribe()
        )
    }

    protected fun action(
        completable: Completable,
        loadingStyle: LoadingStyle = LoadingStyle.DEFAULT
    ) {
        subscriptions.add(completable.compose(schedulers.getIOToMainTransformerCompletable())
            .doOnSubscribe { setLoadingUIState(loadingStyle) }
            .subscribe({
                setDefaultUIState()
            }, { throwable ->
            })
        )
    }

    protected fun <T> action(
        single: Single<T>,
        loadingStyle: LoadingStyle = LoadingStyle.DEFAULT
    ) {
        subscriptions.add(single.compose(schedulers.getIOToMainTransformerSingle())
            .doOnSubscribe { setLoadingUIState(loadingStyle) }
            .subscribe({ result ->
                setDefaultUIState()
            }, { throwable ->
                Log.d("ViewModel", throwable.toString())
            })
        )
    }

    protected fun <T> retryAction(
        single: Single<T>,
        successAction: (T) -> Unit,
        errorAction: ((throwable: Throwable) -> Unit) = ::handleError,
        loadingStyle: LoadingStyle = LoadingStyle.DEFAULT
    ) {
        subscriptions.add(single
            .retryWhen { throwableFlowable ->
                throwableFlowable.flatMap { throwable ->
                    getRetryFlowable(throwable, loadingStyle)
                }
            }
            .compose(schedulers.getIOToMainTransformerSingle())
            .doOnSubscribe { setLoadingUIState(loadingStyle) }
            .subscribe({ result ->
                setDefaultUIState()
                successAction.invoke(result)
            }, { throwable ->
                errorAction(throwable)
            })
        )
    }

    protected fun retryAction(
        completable: Completable,
        successAction: (() -> Unit)? = null,
        errorAction: ((throwable: Throwable) -> Unit) = ::handleError,
        loadingStyle: LoadingStyle = LoadingStyle.DEFAULT
    ) {
        subscriptions.add(completable
            .retryWhen { throwableFlowable ->
                throwableFlowable.flatMap { throwable ->
                    getRetryFlowable(throwable, loadingStyle)
                }
            }
            .compose(schedulers.getIOToMainTransformerCompletable())
            .doOnSubscribe { setLoadingUIState(loadingStyle) }
            .subscribe({
                setDefaultUIState()
                successAction?.invoke()
            }, { throwable ->
                errorAction(throwable)
            })
        )
    }

    private fun getRetryFlowable(
        throwable: Throwable,
        loadingStyle: LoadingStyle
    ): Flowable<Boolean> {
        return Flowable.create<Boolean>({ emitter ->
            uiState.postValue(RetryUIState(throwable) {
                uiState.postValue(LoadingUIState(loadingStyle))
                emitter.onNext(true)
                emitter.onComplete()
            })
        }, BackpressureStrategy.DROP).observeOn(schedulers.getIOScheduler())
    }

    private fun setLoadingUIState(style: LoadingStyle) {
        uiState.value = LoadingUIState(style)
    }

    protected open fun handleError(throwable: Throwable) {
        if (BuildConfig.DEBUG) throwable.printStackTrace()
        uiState.value = ErrorUIState(throwable)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}
