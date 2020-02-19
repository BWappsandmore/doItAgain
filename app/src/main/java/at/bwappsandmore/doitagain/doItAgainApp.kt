package at.bwappsandmore.doitagain

import DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class doItAgainApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.factory().create(this)
}