package ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import repository.AppRepository
import room.AppDatabase
import room.DoItAgainEntity

class ShowDBEntriesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository
    val allActivities: LiveData<List<DoItAgainEntity>>

    init {
        val doItAgainDao = AppDatabase.getDatabase(application, viewModelScope).doItAgainDao()
        repository = AppRepository(doItAgainDao)
        allActivities = repository.allActivities
    }

    fun insert(doItAgainEntity: DoItAgainEntity) = viewModelScope.launch {
        repository.insert(doItAgainEntity)
    }
}

