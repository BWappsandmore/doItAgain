package at.bwappsandmore.doitagain.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import at.bwappsandmore.doitagain.enums.ActionType
import at.bwappsandmore.doitagain.base.BaseViewModel
import at.bwappsandmore.doitagain.repository.AppRepository
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import org.joda.time.DateTime
import org.joda.time.Days.daysBetween



abstract class SharedViewModel : BaseViewModel(){
    abstract fun findActivityById(id : Int)
}


class SharedViewModelImpl(repository: AppRepository) : SharedViewModel() {

    private val activityId = MutableLiveData<Triple<Int,DoItAgainEntity, ActionType>>()

    private val repo: LiveData<AppRepository.RepoResponse> = Transformations.map(activityId) {
        repository.activityRepoHandler(it.first, it.second, it.third)
    }

    val actionResult = Transformations.switchMap(repo) {
        it.actionResult
    }

    val activities = Transformations.switchMap(repo) {
        it.doItAgainEntity
    }

    val findActivity = MutableLiveData<List<DoItAgainEntity>>()

    fun calculateDays(dateActivity: DateTime): Int {
        return if (dateActivity >= DateTime.now()) 0
        else daysBetween(dateActivity.toLocalDate(), DateTime.now().toLocalDate()).days

    }

    fun resetCounter(doItAgainEntity: DoItAgainEntity) {
        doItAgainEntity.daysSinceCounter = 0
        doItAgainEntity.dateActivity = DateTime.now()
        //activityAction(doItAgainEntity, ActionType.UPDATE)
    }

    fun activityAction(activityId : Int, doItAgainEntity: DoItAgainEntity, action: ActionType) {
        this.activityId.postValue(Triple(activityId, doItAgainEntity, action))
    }

    override fun findActivityById(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}

