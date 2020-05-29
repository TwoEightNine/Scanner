package global.msnthrp.scanner.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import global.msnthrp.scanner.db.dao.CodeDao
import global.msnthrp.scanner.db.models.Code

@Database(entities = [Code::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun codeDao(): CodeDao

    companion object {

        private var db: AppDatabase? = null

        fun init(context: Context) {
            db ?: synchronized(this) {
                db ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "app_room.db"
                )
                    .build()
                    .let { db = it }
            }
        }

        fun get(): AppDatabase = db ?: throw IllegalStateException(
            "AppDatabase should be initialized before calling get()!" +
                    "Use init() for achieving this."
        )
    }
}