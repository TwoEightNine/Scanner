package global.msnthrp.scanner.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import global.msnthrp.scanner.App
import global.msnthrp.scanner.R
import global.msnthrp.scanner.model.availableBarcodeFormats
import global.msnthrp.scanner.utils.hide
import global.msnthrp.scanner.utils.show
import kotlinx.android.synthetic.main.view_scanner.view.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerView(
    context: Context,
    attributeSet: AttributeSet
) : RelativeLayout(context, attributeSet) {

    private val formats = availableBarcodeFormats

    var callback: Callback? = null

    private val resultHandler = ResultHandler()

    private var started = false

    init {
        View.inflate(context, R.layout.view_scanner, this)
        setBackgroundColor(0xff333333.toInt())
        zxingScanner.setFormats(formats)
        tvPermissions.setOnClickListener {
            callback?.onPermissionsRequested()
        }
    }

    /**
     * call this in Activity#onResume()
     */
    fun start() {
        if (hasPermissions()) {
            if (!started) {
                zxingScanner.setResultHandler(resultHandler)
                zxingScanner.startCamera()
                started = true
            } else {
                zxingScanner.resumeCameraPreview(resultHandler)
            }
            tvPermissions.hide()
        } else {
            tvPermissions.show()
        }
    }

    /**
     * call this in Activity#onPause
     */
    fun pause() {
        zxingScanner.stopCamera()
        started = false
    }

    private fun hasPermissions() =
        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED

    interface Callback {

        fun onScanned(result: Result)

        fun onPermissionsRequested()
    }

    private inner class ResultHandler : ZXingScannerView.ResultHandler {

        override fun handleResult(rawResult: Result) {
            if (rawResult.barcodeFormat in formats) {
                callback?.onScanned(rawResult)
            } else {
                zxingScanner.resumeCameraPreview(resultHandler)
            }
        }
    }

}