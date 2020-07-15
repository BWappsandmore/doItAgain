package at.bwappsandmore.doitagain.dl

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import at.bwappsandmore.doitagain.repository.AppRepository
import at.bwappsandmore.doitagain.room.AppDatabase
import at.bwappsandmore.doitagain.room.DoItAgainDao
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.GlobalScope

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideDatabase(): AppDatabase {
        return AppDatabase.getDatabase(application, GlobalScope)
    }

    @Provides
    fun provideDaoDoitAgain(appDatabase: AppDatabase): DoItAgainDao {
        return appDatabase.doItAgainDao()
    }

    @Provides
    fun provideAppRepository(dao: DoItAgainDao): AppRepository {
        return AppRepository(dao)
    }

    @Provides
    fun provideSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }
}