package global.msnthrp.scanner.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import global.msnthrp.scanner.R


fun showAlert(context: Context?, message: String, onDismissed: (() -> Unit)? = null) {
    if (context == null) return
    val dialog = AlertDialog.Builder(context, R.style.AlertDialogTheme)
        .setMessage(message)
        .setPositiveButton(R.string.button_ok) { _, _ -> onDismissed?.invoke() }
        .setOnDismissListener { onDismissed?.invoke() }
    if ((context as? AppCompatActivity)?.isFinishing == false) {
        dialog.show()
    }
}

fun showYesNo(context: Context?, message: String, onPressed: (Boolean) -> Unit) {
    if (context == null) return
    val dialog = AlertDialog.Builder(context, R.style.AlertDialogTheme)
        .setMessage(message)
        .setPositiveButton(R.string.button_yes) { _, _ -> onPressed(true) }
        .setNegativeButton(R.string.button_no) { _, _ -> onPressed(false) }
    if ((context as? AppCompatActivity)?.isFinishing == false) {
        dialog.show()
    }
}