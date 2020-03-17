package at.bwappsandmore.doitagain.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface DoItAgainDao {

    @Query("SELECT * FROM DoItAgainEntity")
    fun getAll(): LiveData<List<DoItAgainEntity>>

    @Query("SELECT * FROM DoItAgainEntity WHERE doItAgainActivity = :activity")
    fun findByActivity(activity: String): LiveData<List<DoItAgainEntity>>

    @Query("SELECT * FROM DoItAgainEntity WHERE id = :id")
    fun findActivityById(id: Int): LiveData<DoItAgainEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(doItAgain: DoItAgainEntity)

    @Insert
    suspend fun insertAll(vararg doItAgain: DoItAgainEntity)

    @Query("UPDATE DoItAgainEntity SET doItAgainActivity= :doitagainactivity WHERE id = :id")
    fun updateActivity(doitagainactivity: String, id: Int): Int

    @Update
    suspend fun updateDoItAgain(entity: DoItAgainEntity)

    @Update
    suspend fun updateAllEntities(listEntities : List<DoItAgainEntity>)

    @Delete
    suspend fun delete(doItAgain: DoItAgainEntity)

    @Delete
    suspend fun deleteAll(vararg doItAgain: DoItAgainEntity)
}