package at.bwappsandmore.doitagain.di.module

import at.bwappsandmore.doitagain.ui.MainActivity
import at.bwappsandmore.doitagain.viewModel.SharedViewModel
import dagger.Module
import dagger.Provides

@Module
abstract class ViewModelModule {

    companion object {
        @Provides
        fun providesSharedViewModel(activity: MainActivity): SharedViewModel = activity.viewModel
    }
}