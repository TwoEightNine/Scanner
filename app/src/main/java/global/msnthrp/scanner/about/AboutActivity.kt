package global.msnthrp.scanner.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import global.msnthrp.scanner.BuildConfig
import global.msnthrp.scanner.R
import global.msnthrp.scanner.base.BaseActivity
import kotlinx.android.synthetic.main.activity_about.*


class AboutActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_about

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(getString(R.string.app_name_with_version, BuildConfig.VERSION_NAME))
        mapOf(
            llIconsPixel to URL_ICONS_PIXEL,
            llIconsDmitri to URL_ICONS_DMITRI,
            llLibraryZxing to URL_ZXING,
            llAuthorPlay to URL_AUTHOR_PLAY,
            llAuthorGit to URL_AUTHOR_GIT
        ).forEach { entry ->
            entry.key.setOnClickListener { openUrl(entry.value) }
        }
    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        })
    }

    companion object {

        const val URL_ICONS_PIXEL = "https://www.flaticon.com/authors/pixel-perfect"
        const val URL_ICONS_DMITRI = "https://www.flaticon.com/authors/dmitri13"

        const val URL_ZXING = "https://github.com/zxing/zxing"

        const val URL_AUTHOR_GIT = "https://github.com/TwoEightNine/"
        const val URL_AUTHOR_PLAY = "https://play.google.com/store/apps/developer?id=TwoEightNine"

        fun launch(context: Context?) {
            context?.startActivity(Intent(context, AboutActivity::class.java))
        }
    }

}