package com.easytrack.app.modules.common.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.easytrack.app.BuildConfig
import com.easytrack.app.di.ViewModelFactory
import com.easytrack.app.modules.common.data.*
import com.easytrack.app.modules.common.viewmodel.IBaseViewModel
import com.easytrack.app.modules.common.viewmodel.InitialDataProvider
import com.easytrack.app.utils.ui.EventObserver
import com.easytrack.app.utils.ui.ThrowableHandler
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * @author EEV
 *
 */
abstract class BaseActivity<DataProvider : InitialDataProvider,
        out IViewModel : IBaseViewModel<DataProvider>,
        DataBinding : ViewDataBinding> :
    AppCompatActivity() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    @Suppress("UNCHECKED_CAST")
    protected val viewModel: IViewModel by lazy {
        (ViewModelProviders.of(
            this,
            viewModelFactory
        ).get(getViewModelClass()) as IViewModel).apply {
            provideInitialData(getInitialDataProvider())
        }
    }

    private val uiStatesObserver = Observer<UIState> { uiState -> handleUIState(uiState!!) }
    private val showErrorConnectionDialogObserver =
        EventObserver<Boolean> { isShow -> if (isShow) handleShowErrorConnectionDialog() }

    protected lateinit var binding: DataBinding

    //implement in interface

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    abstract fun getViewModelClass(): Class<out ViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        if (!BuildConfig.DEBUG) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        setupUI()
        subscribeOnUIModel()
    }

    protected open fun setupUI() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
    }

    private fun subscribeOnUIModel() {
        viewModel.getUIState().observe(this, uiStatesObserver)
        viewModel.getShowErrorConnectionDialogLiveData()
            .observe(this, showErrorConnectionDialogObserver)
    }

    private fun handleShowErrorConnectionDialog() {
        //showMessageDialog(R.string.connection_lost)
        showMessageDialog("OK")
    }

    protected open fun handleUIState(uiState: UIState) {
        when (uiState) {
            is EmptyUIState -> handleDefaultState()
            is ErrorUIState -> handleUIError(uiState.throwable)
            is RetryUIState -> handleUIRetry(uiState.throwable, uiState.retry)
            is LoadingUIState -> handleLoadingState(uiState.style)
        }
    }

    protected open fun handleDefaultState() {
    }

    protected open fun handleUIError(throwable: Throwable) {

    }

    protected open fun handleUIRetry(throwable: Throwable, retry: () -> Unit) {
        AlertDialog.Builder(this)
            .apply {
                setCancelable(true)
                setMessage(ThrowableHandler.getCauseMessage(throwable, this@BaseActivity))
                setPositiveButton("OK") { _, _ -> retry.invoke() }
            }.show()
    }

    protected open fun handleConnectionError() {
        AlertDialog.Builder(this)
            .apply {
                setCancelable(true)
                setMessage("OK")
                setPositiveButton("OK") { _, _ -> }
            }.show()
    }

    protected open fun handleLoadingState(style: LoadingStyle) {
    }

    fun showMessageDialog(@StringRes messageId: Int) {
        showMessageDialog(getString(messageId))
    }

    fun showMessageDialog(message: String) {
        showMessageDialog(null, message)
    }

    inline fun showMessageDialog(
        title: String?,
        message: String,
        crossinline action: () -> Unit = {}
    ) {
        AlertDialog.Builder(this)
            .apply {
                setTitle(title)
                setMessage(message)
                setPositiveButton("OK") { _, _ -> action.invoke() }
            }
    }

    inline fun showMessageDialog(
        title: String?,
        positiveButton: String,
        negativeButton: String,
        crossinline action: () -> Unit = {}
    ) {
        AlertDialog.Builder(this)
            .apply {
                setMessage(title)
                setNegativeButton(negativeButton) { _, _ -> }
                setPositiveButton(positiveButton) { _, _ -> action.invoke() }
            }
    }

    fun showDialog(dialogFragment: DialogFragment) {
        if (null == supportFragmentManager.findFragmentByTag(dialogFragment::class.java.name)) {
            val ft = supportFragmentManager.beginTransaction()
            ft.add(dialogFragment, dialogFragment::class.java.name)
            ft.commit()
        }
    }

    protected open fun goBack() {
        supportFinishAfterTransition()
    }

    protected abstract fun getInitialDataProvider(): DataProvider
}