package at.bwappsandmore.doitagain.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface DoItAgainDao {
    @Query("SELECT * FROM DoItAgainEntity")
    fun getAll(): LiveData<List<DoItAgainEntity>>

    @Query("UPDATE DoItAgainEntity SET doItAgainActivity= :doitagainactivity WHERE id = :id")
    fun updateActivity(doitagainactivity: String, id: Int): Int

    @Query("SELECT * FROM DoItAgainEntity WHERE doItAgainActivity= :doitagainactivity")
    fun findByActivity(doitagainactivity: String): LiveData<DoItAgainEntity>

    @Query("SELECT * FROM DoItAgainEntity WHERE id = :id")
    fun findActivityById(id: Int): LiveData<List<DoItAgainEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(doItAgain: DoItAgainEntity)

    @Insert
    suspend fun insertAll(vararg doItAgain: DoItAgainEntity)

    @Delete
    suspend fun delete(doItAgain: DoItAgainEntity)

    @Delete
    suspend fun deleteAll(vararg doItAgain: DoItAgainEntity)

    @Update
    suspend fun updateDoItAgain(doItAgain: DoItAgainEntity)
}