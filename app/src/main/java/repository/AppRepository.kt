package repository

import androidx.lifecycle.LiveData
import room.DoItAgainDao
import room.DoItAgainEntity

class AppRepository (private val doItAgainDao: DoItAgainDao) {
    val allActivities: LiveData<List<DoItAgainEntity>> = doItAgainDao.getAll()

    suspend fun insert(doItAgainEntity: DoItAgainEntity) {
        doItAgainDao.insert(doItAgainEntity)
    }
}