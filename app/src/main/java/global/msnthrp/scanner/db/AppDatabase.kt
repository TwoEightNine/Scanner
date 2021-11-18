package global.msnthrp.scanner.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import global.msnthrp.scanner.db.dao.CodeDao
import global.msnthrp.scanner.db.models.Code

@Database(entities = [Code::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun codeDao(): CodeDao

    companion object {

        private var db: AppDatabase? = null

        fun init(context: Context) {
            db ?: synchronized(this) {
                db ?: createDatabase(context)
                    .let { db = it }
            }
        }

        fun get(): AppDatabase = db ?: throw IllegalStateException(
            "AppDatabase should be initialized before calling get()!" +
                    "Use init() for achieving this."
        )

        private fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_room.db"
            )
                .addMigrations(*Migrations.migrations)
                .build()
        }
    }

    object Migrations {

        val migrations by lazy {
            arrayOf<Migration>(
                migration1to2
            )
        }

        private val migration1to2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Code ADD COLUMN name TEXT")
                database.execSQL("ALTER TABLE Code ADD COLUMN is_favorite INTEGER NOT NULL DEFAULT 0")
            }
        }

    }
}