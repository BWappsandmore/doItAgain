package at.bwappsandmore.doitagain.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<T : BaseViewModel> : DaggerAppCompatActivity() {

    lateinit var viewModel: T

    abstract fun getLayoutResource(): Int
    abstract fun getViewModelClass(): Class<T>
    abstract fun getViewModelFactory(): ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
        viewModel = ViewModelProvider(this, getViewModelFactory()).get(getViewModelClass())
        lifecycle.addObserver(viewModel)
    }

    fun replaceFragment(@IdRes layoutId: Int, fragment: Fragment, backStack: Boolean = false) {
        val transfer = supportFragmentManager.beginTransaction()
            .replace(layoutId, fragment)
        if (backStack) {
            transfer.addToBackStack(fragment.tag)
        }
        transfer.commit()
    }

    fun addFragment(@IdRes layoutId: Int, fragment: Fragment, backStack: Boolean = false) {
        val transfer = supportFragmentManager.beginTransaction()
            .add(layoutId, fragment)
        if (backStack) {
            transfer.addToBackStack(fragment.tag)
        }
        transfer.commit()
    }
}