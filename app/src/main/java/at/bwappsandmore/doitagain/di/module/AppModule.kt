package at.bwappsandmore.doitagain.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import at.bwappsandmore.doitagain.repository.AppRepository
import at.bwappsandmore.doitagain.room.AppDatabase
import at.bwappsandmore.doitagain.room.DoItAgainDao
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.GlobalScope
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Context): AppDatabase {
        return AppDatabase.getDatabase(app, GlobalScope)
    }

    @Singleton
    @Provides
    fun provideDaoDoitAgain(appDatabase: AppDatabase): DoItAgainDao {
        return appDatabase.doItAgainDao()
    }

    @Singleton
    @Provides
    fun provideAppRepository(dao: DoItAgainDao): AppRepository {
        return AppRepository(dao)
    }

    @Provides
    fun provideSharedPreferences(app: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app)
    }
}