package global.msnthrp.scanner.main

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import global.msnthrp.scanner.R
import global.msnthrp.scanner.base.BaseActivity
import global.msnthrp.scanner.base.BaseFragment
import global.msnthrp.scanner.creator.CreatorFragment
import global.msnthrp.scanner.library.LibraryFragment
import global.msnthrp.scanner.scanner.ScannerFragment
import global.msnthrp.scanner.utils.setBottomInsetPadding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    // Icons made by <a href="https://www.flaticon.com/authors/pixel-perfect" title="Pixel perfect">Pixel perfect</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>
    // Icons made by <a href="https://www.flaticon.com/authors/dmitri13" title="dmitri13">dmitri13</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>

    private val scannerFragment by lazy {
        ScannerFragment.newInstance()
    }
    private val libraryFragment by lazy {
        LibraryFragment.newInstance()
    }
    private val creatorFragment by lazy {
        CreatorFragment.newInstance()
    }

    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
        }
        bottomNavView.setOnNavigationItemSelectedListener(BottomViewListener())
        bottomNavView.selectedItemId = R.id.menu_scan

        bottomNavView.setBottomInsetPadding()
    }

    private fun showFragment(fragment: BaseFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContent, fragment)
            .commit()
    }

    override fun getNavBarColor() = Color.TRANSPARENT

    private inner class BottomViewListener : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            showFragment(
                when (item.itemId) {
                    R.id.menu_library -> libraryFragment
                    R.id.menu_generate -> creatorFragment
                    else -> scannerFragment
                }
            )
            return true
        }

    }
}