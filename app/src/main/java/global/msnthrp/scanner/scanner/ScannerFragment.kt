package global.msnthrp.scanner.scanner

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.Result
import global.msnthrp.scanner.R
import global.msnthrp.scanner.about.AboutActivity
import global.msnthrp.scanner.base.BaseFragment
import global.msnthrp.scanner.db.models.Code
import global.msnthrp.scanner.preview.CodePreviewActivity
import global.msnthrp.scanner.utils.setTopInsetPadding
import global.msnthrp.scanner.view.ScannerView
import kotlinx.android.synthetic.main.fragment_scanner.*

class ScannerFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(ScannerViewModel::class.java)
    }
    private val adapter by lazy {
        CodesMiniAdapter(context ?: return@lazy null, ::onCodeClicked)
    }

    override fun getLayoutId() = R.layout.fragment_scanner

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scannerView.callback = ScannerCallback()

        rvCodes.layoutManager = LinearLayoutManager(context).apply {
            stackFromEnd = true
        }
        rvCodes.adapter = adapter

        ivInfo.setOnClickListener {
            AboutActivity.launch(context)
        }
        ivInfo.setTopInsetPadding()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.newCodes.observe(viewLifecycleOwner, Observer(::onCodesUpdated))
    }

    override fun onResume() {
        super.onResume()
        scannerView.start()
    }

    override fun onPause() {
        super.onPause()
        scannerView.pause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            scannerView.start()
        }
    }

    private fun onCodesUpdated(codes: List<Code>) {
        adapter?.update(codes)
    }

    private fun onCodeClicked(code: Code) {
        CodePreviewActivity.launch(context, code)
    }

    companion object {

        const val REQUEST_CODE = 2548

        fun newInstance() = ScannerFragment()
    }

    private inner class ScannerCallback : ScannerView.Callback {

        override fun onScanned(result: Result) {
            viewModel.addCode(result)
            scannerView.start()
        }

        override fun onPermissionsRequested() {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.CAMERA),
                REQUEST_CODE
            )
        }
    }
}