package at.bwappsandmore.doitagain.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.ui.DisplayDataFragment
import at.bwappsandmore.doitagain.ui.MainActivity

abstract class BaseFragment<T : BaseViewModel> : Fragment() {


    lateinit var viewModel: T

    abstract fun getLayoutResource(): Int
    abstract fun getViewModelClass(): Class<T>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            viewModel = ViewModelProvider(it).get(getViewModelClass())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycle.addObserver(viewModel)
        return inflater.inflate(getLayoutResource(), container, false)
    }

    fun closeThisAndOpenNewFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .remove(this)
            .commit()
        (activity as MainActivity).replaceFragment(R.id.container, DisplayDataFragment())
    }

    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }
}