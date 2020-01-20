package room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DoItAgainEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun doItAgainDao(): DoItAgainDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        fun destroyInstance() {
            instance = null
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "doitagain-list.db"
        )
            .build()
    }
}