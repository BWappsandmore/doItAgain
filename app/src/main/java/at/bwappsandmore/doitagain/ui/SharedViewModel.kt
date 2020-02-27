package at.bwappsandmore.doitagain.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import at.bwappsandmore.doitagain.repository.AppRepository
import at.bwappsandmore.doitagain.room.AppDatabase
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import org.joda.time.DateTime
import org.joda.time.Days.daysBetween

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AppRepository
    val allActivities: LiveData<List<DoItAgainEntity>>
    val findActivity = MutableLiveData<List<DoItAgainEntity>>()

    init {
        val doItAgainDao = AppDatabase.getDatabase(application, viewModelScope).doItAgainDao()
        repository =
            AppRepository(doItAgainDao)
        allActivities = repository.allActivities
    }

    fun calculateDays(dateActivity: DateTime): Int {
        return if (dateActivity >= DateTime.now()) 0
        else daysBetween(dateActivity.toLocalDate(), DateTime.now().toLocalDate()).days

    }

    fun resetCounter(doItAgainEntity: DoItAgainEntity) {
        doItAgainEntity.daysSinceCounter = 0
        doItAgainEntity.dateActivity = DateTime.now()
        update(doItAgainEntity)
    }

    fun findActivityById(id: Int) = viewModelScope.launch {
        repository.findById(id)
    }

    fun findByActivity(doitagainactivity: String) = viewModelScope.launch {
        repository.findByActivity(doitagainactivity)
    }

    fun insert(doItAgainEntity: DoItAgainEntity) = viewModelScope.launch {
        repository.insert(doItAgainEntity)
    }

    fun update(doItAgainEntity: DoItAgainEntity) = viewModelScope.launch {
        repository.update(doItAgainEntity)
    }

    fun delete(doItAgainEntity: DoItAgainEntity) = viewModelScope.launch {
        repository.delete(doItAgainEntity)
    }
}

