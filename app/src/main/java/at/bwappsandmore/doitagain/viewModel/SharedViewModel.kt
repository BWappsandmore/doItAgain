package at.bwappsandmore.doitagain.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import at.bwappsandmore.doitagain.base.BaseViewModel
import at.bwappsandmore.doitagain.repository.AppRepository
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import org.joda.time.DateTime
import org.joda.time.Days.daysBetween

abstract class SharedViewModel : BaseViewModel() {

    abstract fun requestFindActivityById(id: Int)
    abstract fun requestFindActivity(activityName: String)

    abstract fun insertDoItAgainActivity(entity: DoItAgainEntity)

    abstract fun updateDoItAgainActivity(entity: DoItAgainEntity)
    abstract fun updateEntity(name: String, dateActivity:DateTime, id:Int)
    abstract fun setReminder(hasReminderSet: Boolean, id: Int)

    abstract fun deleteDoItAgainActivity(entity: DoItAgainEntity)

    abstract fun resetCounter(doItAgainEntity: DoItAgainEntity)

    abstract fun calculateDays(dateActivity: DateTime): Int

    abstract fun getActivity(): LiveData<DoItAgainEntity>
    abstract fun getActivities(): LiveData<List<DoItAgainEntity>>

    abstract fun displayStoredActivities(): LiveData<List<DoItAgainEntity>>
}

class SharedViewModelImpl(private val repository: AppRepository) : SharedViewModel() {

    private val activityId = MutableLiveData<Int>()

    private val activityName = MutableLiveData<String>()

    private val getAllActivities: LiveData<List<DoItAgainEntity>> = repository.findAll()

    override fun displayStoredActivities() = getAllActivities

    override fun getActivity(): LiveData<DoItAgainEntity> = Transformations.switchMap(activityId) {
        repository.findByActivityId(it)
    }

    override fun getActivities(): LiveData<List<DoItAgainEntity>> =
        Transformations.switchMap(activityName) {
            repository.findByActivity(it)
        }

    override fun insertDoItAgainActivity(entity: DoItAgainEntity) {
        repository.insertDoItAgainEntity(entity)
    }

    override fun updateDoItAgainActivity(entity: DoItAgainEntity) {
        repository.updateDoItAgainEntity(entity)
    }

    override fun updateEntity(name: String, dateActivity: DateTime, id: Int) {
        repository.updateEntity(name,  dateActivity, id)
    }

    override fun setReminder(hasReminderSet: Boolean, id: Int) {
        repository.setReminder(hasReminderSet, id)
    }

    override fun deleteDoItAgainActivity(entity: DoItAgainEntity) {
        repository.removeDoItAgainEntity(entity)
    }

    override fun calculateDays(dateActivity: DateTime): Int {
        return if (dateActivity >= DateTime.now()) 0
        else daysBetween(dateActivity.toLocalDate(), DateTime.now().toLocalDate()).days
    }

    override fun resetCounter(doItAgainEntity: DoItAgainEntity) {
        Log.d("resetCounter", "viewModel.resetCounter")
        doItAgainEntity.dateActivity = DateTime.now()
        updateDoItAgainActivity(doItAgainEntity)
    }

    override fun requestFindActivityById(id: Int) {
        this.activityId.postValue(id)
    }

    override fun requestFindActivity(activityName: String) {
        this.activityName.postValue(activityName)
    }
}

