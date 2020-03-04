package com.easytrack.app.modules.common.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.easytrack.app.di.ViewModelFactory
import com.easytrack.app.modules.common.data.*
import com.easytrack.app.modules.common.viewmodel.IBaseViewModel
import com.easytrack.app.modules.common.viewmodel.InitialDataProvider
import com.easytrack.app.utils.ui.EventObserver
import com.easytrack.app.utils.ui.ThrowableHandler
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * @author EEV
 *
 */
abstract class BaseFragment<DataProvider : InitialDataProvider,
        IViewModel : IBaseViewModel<DataProvider>,
        DataBinding : ViewDataBinding> : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

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

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    abstract fun getViewModelClass(): Class<out ViewModel>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        subscribeOnUIModel()
    }

    protected open fun setupUI() {
    }

    private fun subscribeOnUIModel() {
        viewModel.getUIState().observe(viewLifecycleOwner, uiStatesObserver)
        viewModel.getShowErrorConnectionDialogLiveData()
            .observe(viewLifecycleOwner, showErrorConnectionDialogObserver)
    }

    private fun handleShowErrorConnectionDialog() {
        (activity as BaseActivity<*, *, *>).showMessageDialog("FF")
    }

    protected open fun handleUIState(uiState: UIState) {
        when (uiState) {
            is ErrorUIState -> handleUIError(uiState.throwable)
            is RetryUIState -> handleUIRetry(uiState.throwable, uiState.retry)
            is LoadingUIState -> handleLoadingState(uiState.style)
        }
    }

    protected open fun handleUIError(throwable: Throwable) {
            showMessageDialog(
                null,
                ThrowableHandler.getCauseMessage(throwable, activity!!)
            ) { viewModel.setDefaultUIState() }

    }

    protected open fun handleUIRetry(throwable: Throwable, retry: () -> Unit) {
        AlertDialog.Builder(activity!!)
            .apply {
                setCancelable(true)
                setMessage(ThrowableHandler.getCauseMessage(throwable, activity!!))
                setPositiveButton("FF") { _, _ -> retry.invoke() }
            }.show()
    }

    protected open fun handleLoadingState(style: LoadingStyle) {
        // empty
    }

    protected open fun handleConnectionError() {
        AlertDialog.Builder(activity as BaseActivity<*, *, *>)
            .apply {
                setCancelable(true)
                setMessage("FF")
                setPositiveButton("FF") { _, _ -> }
            }.show()
    }

    private inline fun showMessageDialog(
        title: String?,
        message: String,
        crossinline action: () -> Unit = {}
    ) {
        (activity as BaseActivity<*, *, *>).showMessageDialog(title, message, action)
    }

    fun showMessageDialog(messageId: Int) {
        (activity as BaseActivity<*, *, *>).showMessageDialog(messageId)
    }

    fun showMessageDialog(message: String) {
        (activity as BaseActivity<*, *, *>).showMessageDialog(message)
    }

    fun showMessageDialog(title: String?, message: String) {
        (activity as BaseActivity<*, *, *>).showMessageDialog(title, message)
    }

    inline fun showMessageDialog(
        title: String?,
        positiveButton: String,
        negativeButton: String,
        crossinline action: () -> Unit = {}
    ) {
        (activity as BaseActivity<*, *, *>).showMessageDialog(
            title, positiveButton, negativeButton, action)
    }

    fun showDialog(dialogFragment: DialogFragment) {
        if (null == fragmentManager?.findFragmentByTag(dialogFragment::class.java.name)) {
            fragmentManager!!.beginTransaction().apply {
                dialogFragment.setTargetFragment(this@BaseFragment, 0)
                add(dialogFragment, dialogFragment::class.java.name)
            }.commit()
        }
    }

    protected abstract fun getInitialDataProvider(): DataProvider
}