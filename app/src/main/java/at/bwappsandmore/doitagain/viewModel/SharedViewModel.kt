package at.bwappsandmore.doitagain.viewModel

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

    abstract fun resetCounter(doItAgainEntity: DoItAgainEntity)

    abstract fun calculateDays(dateActivity: DateTime): Int
    abstract fun getDateTime(dateTime: DateTime): DateTime

    abstract fun getActivity() : LiveData<DoItAgainEntity>
    abstract fun getActivities() : LiveData<List<DoItAgainEntity>>
}

class SharedViewModelImpl(private val repository: AppRepository) : SharedViewModel() {

    private val activityId = MutableLiveData<Int>()

    private val activityName = MutableLiveData<String>()

    override fun getActivity(): LiveData<DoItAgainEntity> = Transformations.switchMap(activityId){
        repository.findByActivityId(it)
    }

    override fun getActivities(): LiveData<List<DoItAgainEntity>> = Transformations.switchMap(activityName){
        repository.findByActivity(it)
    }

    private lateinit var dateTime: DateTime

    override fun calculateDays(dateActivity: DateTime): Int {
        return if (dateActivity >= DateTime.now()) 0
        else daysBetween(dateActivity.toLocalDate(), DateTime.now().toLocalDate()).days
    }

    override fun resetCounter(doItAgainEntity: DoItAgainEntity) {
        doItAgainEntity.daysSinceCounter = 0
        doItAgainEntity.dateActivity = DateTime.now()
    }

    override fun getDateTime(dateTime: DateTime): DateTime {
        this.dateTime = dateTime
        return this.dateTime
    }

    override fun requestFindActivityById(id: Int) {
        this.activityId.postValue(id)
    }

    override fun requestFindActivity(activityName: String) {
        this.activityName.postValue(activityName)
    }
}

