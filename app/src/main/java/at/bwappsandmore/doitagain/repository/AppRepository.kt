package at.bwappsandmore.doitagain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import at.bwappsandmore.doitagain.ActionType
import at.bwappsandmore.doitagain.room.DoItAgainDao
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AppRepository(private val doItAgainDao: DoItAgainDao) {

    data class RepoResponse(
        val actionResult   :  LiveData<Boolean>,
        val doItAgainEntity: LiveData<List<DoItAgainEntity>>
    )

    fun activityRepoHandler(activityId : Int, doItAgainEntity: DoItAgainEntity, action: ActionType) : RepoResponse {
        val actionResult     = MutableLiveData<Boolean>()
        GlobalScope.launch {
            when(action){
                ActionType.INSERT -> {
                    doItAgainDao.insert(doItAgainEntity)
                    actionResult.postValue(true)
                }
                ActionType.UPDATE -> {
                    doItAgainDao.updateDoItAgain(doItAgainEntity)
                    actionResult.postValue(true)
                }
                ActionType.DELETE -> {
                    doItAgainDao.delete(doItAgainEntity)
                    actionResult.postValue(true)
                }
                else -> {
                    //not Implemented yet
                }
            }
        }
        return RepoResponse(actionResult, when(action){
            ActionType.FindById       -> doItAgainDao.findActivityById(activityId)
            ActionType.FindByActivity -> doItAgainDao.findByActivity(doItAgainEntity.doItAgainActivity)
            else -> MutableLiveData<List<DoItAgainEntity>>()
        })
    }

}