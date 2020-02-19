import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import at.bwappsandmore.doitagain.di.module.ApplicationModule
import at.bwappsandmore.doitagain.doItAgainApp
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class]
)

interface AppComponent : AndroidInjector<doItAgainApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): AppComponent
    }
}