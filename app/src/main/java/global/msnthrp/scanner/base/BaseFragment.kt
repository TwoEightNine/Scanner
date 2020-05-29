package global.msnthrp.scanner.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import global.msnthrp.scanner.utils.showAlert
import global.msnthrp.scanner.view.LoaderDialog

abstract class BaseFragment : Fragment() {

    private var loaderDialog: LoaderDialog? = null

    protected val baseActivity: BaseActivity?
        get() = activity as? BaseActivity

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(getLayoutId(), null)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getDefaultViewModel()?.also {
            initDefaultError(it)
        }
    }

    protected fun setTitle(title: String) {
        baseActivity?.supportActionBar?.title = title
    }

    protected open fun getDefaultViewModel(): BaseViewModel? = null

    /**
     * passes error message from [BaseViewModel.errorLiveData] to [showAlert]
     * it is the most used case
     */
    protected fun initDefaultError(viewModel: BaseViewModel) {
        viewModel.error.observe(viewLifecycleOwner, Observer { errorEvent ->
            errorEvent.getValue()?.also {
                showAlert(context, it)
            }
        })
    }

    /**
     * binds loading state from [BaseViewModel.loadingLiveData]
     * no need to write it in every child
     */
    protected fun initDefaultLoading(viewModel: BaseViewModel) {
        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                showLoader()
            } else {
                hideLoader()
            }
        })
    }

    private fun showLoader() {
        context?.also {
            loaderDialog = LoaderDialog(it)
            loaderDialog?.show()
        }
    }

    private fun hideLoader() {
        loaderDialog?.dismiss()
        loaderDialog = null
    }
}