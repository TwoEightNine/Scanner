package global.msnthrp.scanner.view

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog

class LoaderDialog(context: Context) : AlertDialog(context, false, null) {

    init {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setView(ProgressBar(context).apply {
            indeterminateDrawable.colorFilter = PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        })
    }
}