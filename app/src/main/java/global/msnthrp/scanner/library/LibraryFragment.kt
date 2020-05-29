package global.msnthrp.scanner.library

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import global.msnthrp.scanner.R
import global.msnthrp.scanner.base.BaseFragment
import global.msnthrp.scanner.db.models.Code
import global.msnthrp.scanner.preview.CodePreviewActivity
import global.msnthrp.scanner.utils.setTopInsetPadding
import kotlinx.android.synthetic.main.fragment_library.*

class LibraryFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(LibraryViewModel::class.java)
    }
    private val adapter by lazy {
        CodesAdapter(context ?: return@lazy null, ::onCodeClicked)
    }

    override fun getLayoutId() = R.layout.fragment_library

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCodes.layoutManager = LinearLayoutManager(context)
        rvCodes.adapter = adapter

        rvCodes.setTopInsetPadding()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.codes.observe(viewLifecycleOwner, Observer(::onCodesLoaded))
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCodes()
    }

    private fun onCodesLoaded(codes: List<Code>) {
        adapter?.update(codes)
    }

    private fun onCodeClicked(code: Code) {
        CodePreviewActivity.launch(context, code)
    }

    companion object {

        fun newInstance() = LibraryFragment()
    }
}