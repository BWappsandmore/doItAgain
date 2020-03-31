package at.bwappsandmore.doitagain.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.base.BaseActivity
import at.bwappsandmore.doitagain.databinding.ActivityMainBinding
import at.bwappsandmore.doitagain.dl.AppModule
import at.bwappsandmore.doitagain.dl.DaggerAppComponent
import at.bwappsandmore.doitagain.repository.AppRepository
import at.bwappsandmore.doitagain.viewModel.SharedViewModel
import at.bwappsandmore.doitagain.viewModel.SharedViewModelImpl
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, SharedViewModel>() {

    @Inject
    lateinit var repository: AppRepository

    private var isChecked = false

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
            viewModel.setReminder(false, intent.getIntExtra("EntityID", 0))
        }
    }

    override fun onBackPressed() {
        replaceFragment(R.id.container, DisplayDataFragment())
        super.onBackPressed()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val checkable = menu?.findItem(R.id.checkable_menu)
        checkable?.isChecked = isChecked
        return true
    }

    // comment-in if needed
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.checkable_menu -> {
                if (!isChecked) {
                    isChecked = !item.isChecked
                    item.isChecked = isChecked
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                true
            }
            else -> false
        }
    }

    override fun inject() {
        DaggerAppComponent.builder().appModule(AppModule(application)).build().inject(this)
    }


}
