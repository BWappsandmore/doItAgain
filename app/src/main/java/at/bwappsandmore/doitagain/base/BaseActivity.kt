package at.bwappsandmore.doitagain.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<E : ViewDataBinding,T : BaseViewModel> : AppCompatActivity() {

    lateinit var viewModel   : T

    lateinit var dataBinding : E

    abstract fun inject()

    abstract fun getLayoutResource(): Int

    abstract fun getViewModelClass() : Class<T>

    abstract fun getViewModelFactory() : ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, getLayoutResource())
        inject()
        viewModel = ViewModelProvider(this, getViewModelFactory()).get(getViewModelClass())
        lifecycle.addObserver(viewModel)
    }



    fun addFragment(@IdRes layoutId : Int, fragment: Fragment, backStack : Boolean  = false) {
        val transfer = supportFragmentManager.beginTransaction()
            .replace(layoutId, fragment)
        if (backStack){
            transfer.addToBackStack(fragment.tag)
        }
        transfer.commit()
    }
}