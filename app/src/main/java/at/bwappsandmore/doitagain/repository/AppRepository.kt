package at.bwappsandmore.doitagain.repository

import androidx.lifecycle.LiveData

import androidx.lifecycle.map
import at.bwappsandmore.doitagain.room.DoItAgainDao
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.joda.time.DateTime

interface LocalRepository {
    fun insertDoItAgainEntity(entity: DoItAgainEntity)
    fun updateDoItAgainEntity(entity: DoItAgainEntity)
    fun updateListEntities(listEntity: List<DoItAgainEntity>)
    fun updateEntity(name: String, dateActivity: DateTime, id: Int)
    fun setReminder(hasReminderSet: Boolean, id: Int)
    fun removeDoItAgainEntity(entity: DoItAgainEntity)
    fun findAll(): LiveData<List<DoItAgainEntity>>
    fun findByActivity(activity: String): LiveData<List<DoItAgainEntity>>
    fun findByActivityId(activityId: Int): LiveData<DoItAgainEntity>
    fun getMaxValue(activityId: Int): LiveData<Int>
    fun setMaxValue(maxValue: Int, activityId: Int)
    fun updateMaxValueDate(maxValue: Int, id: Int)
    fun updateDateActivity(dateActivity: DateTime, id: Int)
}

class AppRepository(private val doItAgainDao: DoItAgainDao) : LocalRepository {

    override fun insertDoItAgainEntity(entity: DoItAgainEntity) {
        GlobalScope.launch {
            doItAgainDao.insert(entity)
        }
    }

    override fun updateDoItAgainEntity(entity: DoItAgainEntity) {
        GlobalScope.launch {
            doItAgainDao.updateDoItAgain(entity)
        }
    }

    override fun updateListEntities(listEntity: List<DoItAgainEntity>) {
        GlobalScope.launch {
            doItAgainDao.updateAllEntities(listEntity)
        }
    }

    override fun updateEntity(name: String, dateActivity: DateTime, id: Int) {
        GlobalScope.launch {
            doItAgainDao.updateEntity(name, dateActivity, id)
        }
    }

    override fun removeDoItAgainEntity(entity: DoItAgainEntity) {
        GlobalScope.launch {
            doItAgainDao.delete(entity)
        }
    }

    override fun setReminder(hasReminderSet: Boolean, id: Int) {
        GlobalScope.launch {
            doItAgainDao.setReminder(hasReminderSet, id)
        }
    }

    override fun findByActivity(activity: String) = doItAgainDao.findByActivity(activity)

    override fun findAll() = doItAgainDao.getAll()

    override fun findByActivityId(activityId: Int) = doItAgainDao.findActivityById(activityId)
    override fun getMaxValue(activityId: Int): LiveData<Int> = doItAgainDao.getMaxValue(activityId).map { entity -> entity.maxValue }

    override fun setMaxValue(maxValue: Int, activityId: Int) {
        GlobalScope.launch {
            doItAgainDao.setMaxValue(maxValue, activityId)
        }
    }

    override fun updateMaxValueDate(maxValue: Int, id: Int) {
        GlobalScope.launch {
            doItAgainDao.updateMaxValueDate(maxValue, id)
        }
    }

    override fun updateDateActivity(dateActivity: DateTime, id: Int) {
        GlobalScope.launch {
            doItAgainDao.updateDateActivity(dateActivity, id)
        }
    }
}