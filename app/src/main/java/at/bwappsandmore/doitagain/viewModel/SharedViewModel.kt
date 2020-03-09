package at.bwappsandmore.doitagain.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import at.bwappsandmore.doitagain.enums.ActionType
import at.bwappsandmore.doitagain.base.BaseViewModel
import at.bwappsandmore.doitagain.repository.AppRepository
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import org.joda.time.DateTime
import org.joda.time.Days.daysBetween

abstract class SharedViewModel : BaseViewModel() {
    abstract fun findActivityById(id: Int)
    abstract fun resetCounter(doItAgainEntity: DoItAgainEntity)
    abstract fun calculateDays(dateActivity: DateTime): Int
    abstract fun findActivity(activityName: String)
    abstract fun activityAction(activityId: Int, doItAgainEntity: DoItAgainEntity, action: ActionType)
    abstract fun getDateTime(dateTime: DateTime): DateTime
    abstract fun getActivities() : LiveData<List<DoItAgainEntity>>
}

class SharedViewModelImpl(repository: AppRepository) : SharedViewModel() {

    private val activityId = MutableLiveData<Triple<Int, DoItAgainEntity, ActionType>>()

    private lateinit var dateTime: DateTime

    private val repo: LiveData<AppRepository.RepoResponse> = Transformations.map(activityId) {
        repository.activityRepoHandler(it.first, it.second, it.third)
    }

    val actionResult = Transformations.switchMap(repo) {
        it.actionResult
    }

    private val liveDataActivities = Transformations.switchMap(repo) {
        it.doItAgainEntity
    }
    override fun getActivities():LiveData<List<DoItAgainEntity>> = liveDataActivities

    override fun calculateDays(dateActivity: DateTime): Int {
        return if (dateActivity >= DateTime.now()) 0
        else daysBetween(dateActivity.toLocalDate(), DateTime.now().toLocalDate()).days
    }

    override fun resetCounter(doItAgainEntity: DoItAgainEntity) {
        doItAgainEntity.daysSinceCounter = 0
        doItAgainEntity.dateActivity = DateTime.now()
        activityAction(doItAgainEntity.id, doItAgainEntity, ActionType.UPDATE)
    }

    override fun activityAction(
        activityId: Int,
        doItAgainEntity: DoItAgainEntity,
        action: ActionType
    ) {
        this.activityId.postValue(Triple(activityId, doItAgainEntity, action))
    }

    override fun getDateTime(dateTime: DateTime): DateTime {
        this.dateTime = dateTime
        return this.dateTime
    }

    override fun findActivityById(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findActivity(activityName: String) {
        Log.d(null, "ViewModel.findActivity: $activityName and date $dateTime")
        activityAction(0, DoItAgainEntity(0, activityName, calculateDays(dateTime), dateTime), ActionType.FindByActivity)
        getActivities()
    }
}

