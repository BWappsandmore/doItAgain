package at.bwappsandmore.doitagain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import at.bwappsandmore.doitagain.room.DoItAgainDao
import at.bwappsandmore.doitagain.room.DoItAgainEntity

class AppRepository(private val doItAgainDao: DoItAgainDao, private val doitagainactivity: String) {
    val allActivities: LiveData<List<DoItAgainEntity>> = doItAgainDao.getAll()
    val findActivity: LiveData<List<DoItAgainEntity>> = doItAgainDao.findByActivity(doitagainactivity = doitagainactivity)

    suspend fun insert(doItAgainEntity: DoItAgainEntity) {
        doItAgainDao.insert(doItAgainEntity)
    }

    suspend fun update(doItAgainEntity: DoItAgainEntity) {
        doItAgainDao.updateDoItAgain(doItAgainEntity)
    }

    fun findById(id: Int) {
        doItAgainDao.findActivityById(id)
    }

    fun findByActivity(doitagainactivity: String): LiveData<List<DoItAgainEntity>> {
        return doItAgainDao.findByActivity(doitagainactivity)
    }



}