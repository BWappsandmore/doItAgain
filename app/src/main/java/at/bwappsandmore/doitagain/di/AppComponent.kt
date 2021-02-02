package at.bwappsandmore.doitagain.di

import android.content.Context
import at.bwappsandmore.doitagain.DoItAgainApp
import at.bwappsandmore.doitagain.di.module.ActivityModule
import at.bwappsandmore.doitagain.di.module.AppModule
import at.bwappsandmore.doitagain.repository.AppRepository
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityModule::class])

interface AppComponent : AndroidInjector<DoItAgainApp> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): AppComponent
    }
}