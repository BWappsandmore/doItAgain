package at.bwappsandmore.doitagain.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel(), LifecycleObserver {
    private val disposables =  CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}