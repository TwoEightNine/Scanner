package global.msnthrp.scanner

import android.app.Application
import global.msnthrp.scanner.db.AppDatabase
import global.msnthrp.scanner.utils.Prefs

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
        Prefs.init(this)
    }
}