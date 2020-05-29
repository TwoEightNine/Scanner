package global.msnthrp.scanner.utils

import android.util.Base64
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*


fun encodeBase64(src: String): String = Base64.encodeToString(src.toByteArray(), Base64.DEFAULT)

fun decodeBase64(base: String) = String(Base64.decode(base, Base64.DEFAULT))

fun Long.toStringTime(skipHours: Boolean = false): String {
    val date = Calendar.getInstance().apply {
        time = Date(this@toStringTime)
    }
    val dayDate = date.get(Calendar.DAY_OF_MONTH)
    val monthDate = date.get(Calendar.MONTH)
    val yearDate = date.get(Calendar.YEAR)

    val today = Calendar.getInstance()
    val dayToday = today.get(Calendar.DAY_OF_MONTH)
    val monthToday = today.get(Calendar.MONTH)
    val yearToday = today.get(Calendar.YEAR)

    val sameDay = yearToday == yearDate && monthToday == monthDate && dayToday == dayDate
    val sameYear = yearToday == yearDate
    val format = StringBuilder()
    if (sameDay) {
        format.append("HH:mm")
    } else {
        format.append("dd MMM")
        if (!sameYear) {
            format.append(" yyyy")
        }
        if (!skipHours) {
            format.append(" HH:mm")
        }
    }
    return SimpleDateFormat(format.toString(), Locale.getDefault())
        .format(date.time)
        .toLowerCase()
}