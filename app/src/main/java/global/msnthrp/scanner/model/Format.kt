package global.msnthrp.scanner.model

import com.google.zxing.BarcodeFormat

data class Format(
    val barcodeFormat: BarcodeFormat,

    val isSquare: Boolean,

    val sample: String
)

val availableFormats = listOf(
    Format(
        barcodeFormat = BarcodeFormat.AZTEC,
        isSquare = true,
        sample = "hatrid mun sigra"
    ),
    Format(
        barcodeFormat = BarcodeFormat.QR_CODE,
        isSquare = true,
        sample = "hatrid mun sigra"
    ),
    Format(
        barcodeFormat = BarcodeFormat.DATA_MATRIX,
        isSquare = true,
        sample = "hatrid mun sigra"
    ),
    Format(
        barcodeFormat = BarcodeFormat.CODABAR,
        isSquare = false,
        sample = "31117013206375"
    ),
    Format(
        barcodeFormat = BarcodeFormat.EAN_8,
        isSquare = false,
        sample = "90311017"
    ),
    Format(
        barcodeFormat = BarcodeFormat.EAN_13,
        isSquare = false,
        sample = "5901234123457"
    ),
    Format(
        barcodeFormat = BarcodeFormat.UPC_A,
        isSquare = false,
        sample = "042100005264"
    ),
    Format(
        barcodeFormat = BarcodeFormat.UPC_E,
        isSquare = false,
        sample = "11318972"
    ),
    Format(
        barcodeFormat = BarcodeFormat.PDF_417,
        isSquare = false,
        sample = "sample"
    ),
    Format(
        barcodeFormat = BarcodeFormat.ITF,
        isSquare = false,
        sample = "00012345600012"
    )
)

val availableBarcodeFormats = availableFormats.map { it.barcodeFormat }