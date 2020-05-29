package global.msnthrp.scanner.creator.model

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat

data class AvailableCode(

    val format: BarcodeFormat,

    val bitmap: Bitmap
)