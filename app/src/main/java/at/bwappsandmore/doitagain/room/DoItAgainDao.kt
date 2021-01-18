package at.bwappsandmore.doitagain.room

import androidx.lifecycle.LiveData
import androidx.room.*
import org.joda.time.DateTime

@Dao
interface DoItAgainDao {

    @Query("SELECT * FROM DoItAgainEntity")
    fun getAll(): LiveData<List<DoItAgainEntity>>

    @Query("SELECT * FROM DoItAgainEntity WHERE name = :name")
    fun findByActivity(name: String): LiveData<List<DoItAgainEntity>>

    @Query("SELECT * FROM DoItAgainEntity WHERE id = :id")
    fun findActivityById(id: Int): LiveData<DoItAgainEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(doItAgain: DoItAgainEntity)

    @Insert
    suspend fun insertAll(vararg doItAgain: DoItAgainEntity)

    @Query("UPDATE DoItAgainEntity SET name =:name, dateActivity= :dateActivity WHERE id= :id")
    fun updateEntity(name: String, dateActivity: DateTime, id: Int): Int

    @Query("UPDATE DoItAgainEntity SET maxValue =:maxValue  WHERE id= :id")
    fun updateMaxValueDate(maxValue: Int, id: Int): Int

    @Query("UPDATE DoItAgainEntity SET dateActivity= :dateActivity WHERE id= :id")
    fun updateDateActivity(dateActivity: DateTime, id: Int)

    @Query("UPDATE DoItAgainEntity SET hasReminderSet =:hasReminderSet WHERE id= :id")
    fun setReminder(hasReminderSet: Boolean, id: Int): Int

    @Query("UPDATE DoItAgainEntity SET maxValue =:maxValue WHERE id= :id")
    fun setMaxValue(maxValue: Int, id: Int): Int

    @Query("SELECT * FROM DoItAgainEntity WHERE id = :id")
    fun getMaxValue(id: Int): LiveData<DoItAgainEntity>

    @Update
    suspend fun updateDoItAgain(entity: DoItAgainEntity)

    @Update
    suspend fun updateAllEntities(listEntities: List<DoItAgainEntity>)

    @Delete
    suspend fun delete(doItAgain: DoItAgainEntity)

    @Delete
    suspend fun deleteAll(vararg doItAgain: DoItAgainEntity)
}