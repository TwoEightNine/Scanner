package global.msnthrp.scanner.utils

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import androidx.core.content.FileProvider
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File
import java.io.FileOutputStream

fun copyToClip(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.text = text
}

fun shareText(context: Context, text: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}

fun shareImage(context: Context, bitmap: Bitmap, text: String? = null) {
    val i = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        putExtra(Intent.EXTRA_STREAM, saveBitmap(context, bitmap))
        putExtra(Intent.EXTRA_TEXT, text)
    }
    context.startActivity(Intent.createChooser(i, null))
}

fun saveBitmap(context: Context, bmp: Bitmap, dir: File? = context.externalCacheDir): Uri? {
    var bmpUri: Uri? = null
    try {
        val file = File(dir, "${System.currentTimeMillis()}_${bmp.hashCode()}.png")
        val out = FileOutputStream(file)
        bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
        out.close()
        bmpUri = getUriForFile(context, file)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return bmpUri
}

fun getUriForFile(context: Context?, file: File): Uri? {
    context ?: return null

    val authority = "${context.applicationContext.packageName}.provider"
    return FileProvider.getUriForFile(context, authority, file)
}

fun showKeyboard(context: Context?) {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun createCodeOrGetError(
    barcodeFormat: BarcodeFormat,
    data: String,
    width: Int,
    height: Int
): Pair<Bitmap?, String?> =
    try {
        val multiFormatWriter = MultiFormatWriter()
        val bitMatrix = multiFormatWriter.encode(
            data, barcodeFormat,
            width, height
        )
        val barcodeEncoder = BarcodeEncoder()
        Pair(barcodeEncoder.createBitmap(bitMatrix), null)
    } catch (e: Exception) {
        e.printStackTrace()
        Pair(null, e.message)
    }