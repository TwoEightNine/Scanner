package global.msnthrp.scanner.utils

import android.view.View


fun View.setVisible(visible: Boolean) {
    if (visible) show() else hide()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}