package global.msnthrp.scanner.preview

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Patterns
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import global.msnthrp.scanner.R
import global.msnthrp.scanner.base.BaseActivity
import global.msnthrp.scanner.db.models.Code
import global.msnthrp.scanner.model.availableFormats
import global.msnthrp.scanner.utils.*
import kotlinx.android.synthetic.main.activity_code.*
import java.io.File

class CodePreviewActivity : BaseActivity() {

    private val code by lazy {
        intent?.extras?.getParcelable(ARG_CODE) as? Code
    }
    private val couldBeSaved by lazy {
        intent?.extras?.getBoolean(ARG_COULD_BE_SAVED) == true
    }
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(CodePreviewViewModel::class.java)
    }
    private var codeBitmap: Bitmap? = null

    override fun getLayoutId() = R.layout.activity_code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        code?.also { code ->
            val metaRes = if (code.isScanned) {
                R.string.code_meta_scanned
            } else {
                R.string.code_meta_created
            }
            setTitle(getString(metaRes, code.codeType))

            val isUrl = Patterns.WEB_URL.matcher(code.data).matches()
            tvData.text = when {
                isUrl -> Html.fromHtml(wrapAsHtmlLink(code.data))
                else -> code.data
            }
            if (isUrl) {
                tvData.movementMethod = LinkMovementMethod.getInstance()
            }

            tvDate.text = code.timeStamp.toStringTime()

            val format = BarcodeFormat.valueOf(code.codeType)
            val (bitmap, error) = createCodeOrGetError(
                format, code.data,
                CODE_SIZE, if (isCode2D(format)) CODE_SIZE else CODE_SIZE_1D
            )
            rlCode.setVisible(bitmap != null)
            tvError.setVisible(error != null)

            bitmap?.also {
                codeBitmap = bitmap
                ivCode.setImageBitmap(bitmap)
            }
            tvError.text = error
        }
        ivCopy.setOnClickListener {
            code?.also {
                copyToClip(this, it.data)
            }
        }
        ivShareData.setOnClickListener {
            code?.also {
                shareText(this, it.data)
            }
        }
        ivDownload.setOnClickListener {
            codeBitmap?.also {
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.also { dir ->
                    saveBitmap(this, it, dir)
                }
            }
        }
        ivShareCode.setOnClickListener {
            codeBitmap?.also {
                shareImage(this, it, code?.data)
            }
        }
        btnAddToLibrary.setOnClickListener {
            code?.also {
                viewModel.addToLibrary(it)
            }
            Snackbar.make(svContent, R.string.code_added, Snackbar.LENGTH_LONG).show()
            btnAddToLibrary.hide()
        }
        btnAddToLibrary.setVisible(couldBeSaved)
        svContent.setBottomInsetPadding()
    }

    private fun isCode2D(format: BarcodeFormat) = format in formats2D

    private fun wrapAsHtmlLink(url: String): String = "<a href=\"$url\">$url</a>"

    companion object {

        private val formats2D = availableFormats
            .filter { it.isSquare }
            .map { it.barcodeFormat }

        const val CODE_SIZE = 800
        const val CODE_SIZE_1D = 400

        const val ARG_CODE = "code"
        const val ARG_COULD_BE_SAVED = "couldBeSaved"

        fun launch(context: Context?, code: Code, couldBeSaved: Boolean = false) {
            context?.startActivity(Intent(context, CodePreviewActivity::class.java).apply {
                putExtra(ARG_CODE, code)
                putExtra(ARG_COULD_BE_SAVED, couldBeSaved)
            })
        }
    }
}