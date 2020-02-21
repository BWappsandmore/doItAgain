package at.bwappsandmore.doitagain.repository

import androidx.lifecycle.LiveData
import at.bwappsandmore.doitagain.room.DoItAgainDao
import at.bwappsandmore.doitagain.room.DoItAgainEntity

class AppRepository (private val doItAgainDao: DoItAgainDao) {
    val allActivities: LiveData<List<DoItAgainEntity>> = doItAgainDao.getAll()

    suspend fun insert(doItAgainEntity: DoItAgainEntity) {
        doItAgainDao.insert(doItAgainEntity)
    }

    suspend fun update(doItAgainEntity: DoItAgainEntity) {
        doItAgainDao.updateDoItAgain(doItAgainEntity)
    }
}