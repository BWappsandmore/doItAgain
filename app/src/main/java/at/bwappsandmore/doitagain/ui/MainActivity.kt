package at.bwappsandmore.doitagain.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.adapter.ActivitiesAdapter
import at.bwappsandmore.doitagain.base.BaseActivity
import at.bwappsandmore.doitagain.databinding.ActivityMainBinding
import at.bwappsandmore.doitagain.dl.AppModule
import at.bwappsandmore.doitagain.dl.DaggerAppComponent
import at.bwappsandmore.doitagain.enums.ActionType
import at.bwappsandmore.doitagain.repository.AppRepository
import at.bwappsandmore.doitagain.viewModel.SharedViewModel
import at.bwappsandmore.doitagain.viewModel.SharedViewModelImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.display_data_fragment.*
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityMainBinding, SharedViewModel>() {

    @Inject
    lateinit var repository: AppRepository

    override fun getLayoutResource(): Int = R.layout.activity_main

    override fun getViewModelClass(): Class<SharedViewModel> = SharedViewModel::class.java

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SharedViewModelImpl(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        replaceFragment(R.id.container, DisplayDataFragment())

        if (intent.hasExtra("EntityID")) {
            viewModel.setReminder(false, intent.getIntExtra("EntityID",0))
            Log.d("onCreate","Intent has arrived")
        }
    }

    override fun onBackPressed() {
        replaceFragment(R.id.container, DisplayDataFragment())
        super.onBackPressed()
    }

    // comment-out if needed
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun inject() {
        DaggerAppComponent.builder().appModule(AppModule(application)).build().inject(this)
    }


}
