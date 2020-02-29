package at.bwappsandmore.doitagain.dl

import at.bwappsandmore.doitagain.ui.MainActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(target : MainActivity)
}