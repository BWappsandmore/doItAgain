package at.bwappsandmore.doitagain

import at.bwappsandmore.doitagain.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class DoItAgainApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication>  = DaggerAppComponent.factory().create(this)
}

