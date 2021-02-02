package at.bwappsandmore.doitagain.di.module

import at.bwappsandmore.doitagain.di.module.scope.ActivityScope
import at.bwappsandmore.doitagain.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    fun injectMainActivity(): MainActivity
}