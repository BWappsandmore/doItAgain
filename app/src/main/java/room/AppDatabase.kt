package room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [DoItAgainEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun doItAgainDao(): DoItAgainDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "doitagain-database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ): RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.doItAgainDao())
                }
            }
        }

        suspend fun populateDatabase(doItAgainDao: DoItAgainDao) {
            doItAgainDao.deleteAll()

            var engagement = DoItAgainEntity(1, "play guitar", 100)
            doItAgainDao.insert(engagement)
            engagement = DoItAgainEntity(2,"make breakfast",2)
            doItAgainDao.insert(engagement)
            engagement = DoItAgainEntity(2,"go out with friends",20)
            doItAgainDao.insert(engagement)
        }
    }
}