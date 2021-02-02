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
    abstract fun updateEntity(name: String, dateActivity: DateTime, id: Int)
    abstract fun setReminder(hasReminderSet: Boolean, id: Int)

    abstract fun deleteDoItAgainActivity(entity: DoItAgainEntity)

    abstract fun resetCounter(doItAgainEntity: DoItAgainEntity)

    abstract fun calculateDays(dateActivity: DateTime): Int

    abstract fun getActivity(): LiveData<DoItAgainEntity>
    abstract fun getActivities(): LiveData<List<DoItAgainEntity>>

    abstract fun displayStoredActivities(): LiveData<List<DoItAgainEntity>>

    abstract fun getMaxValue(id: Int): LiveData<Int>
    abstract fun setMaxValue(maxValueFromDB: Int, valueCalculated: Int, id: Int)
    abstract fun updateMaxValueDate(maxValue: Int, id: Int)
    abstract fun updateDateActivity(dateActivity: DateTime, id: Int)
}

class SharedViewModelImpl(private val repository: AppRepository) : SharedViewModel() {

    private val activityId = MutableLiveData<Int>()

    private val activityName = MutableLiveData<String>()

    private val getAllActivities: LiveData<List<DoItAgainEntity>> = repository.findAll()

    override fun displayStoredActivities() = getAllActivities


    override fun getActivity(): LiveData<DoItAgainEntity> = Transformations.switchMap(activityId) {
        repository.findByActivityId(it)
    }

    override fun getMaxValue(id: Int) = repository.getMaxValue(id)


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
        repository.updateEntity(name, dateActivity, id)
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

    override fun setMaxValue(maxValueFromDB: Int, valueCalculated: Int, id: Int) {

        if (valueCalculated > maxValueFromDB) {
            repository.apply {
                setMaxValue(valueCalculated, id)
                updateMaxValueDate(valueCalculated, id)
            }
            Log.d("VM setMaxValue", "setting $valueCalculated")
        }
    }


    override fun updateMaxValueDate(maxValue: Int, id: Int) {
        repository.updateMaxValueDate(maxValue, id)
    }

    override fun updateDateActivity(dateActivity: DateTime, id: Int) {
        repository.updateDateActivity(dateActivity, id)
    }

    override fun resetCounter(doItAgainEntity: DoItAgainEntity) {
        //updateDoItAgainActivity(doItAgainEntity)
        updateDateActivity(DateTime.now(), doItAgainEntity.id)
        Log.d("VM resetCounter", "update date")
    }

    override fun requestFindActivityById(id: Int) {
        this.activityId.postValue(id)
    }

    override fun requestFindActivity(activityName: String) {
        this.activityName.postValue(activityName)
    }
}

