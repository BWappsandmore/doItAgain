package room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface DoItAgainDao {
    @Query("SELECT * FROM DoItAgainEntity")
    fun getAll(): LiveData<List<DoItAgainEntity>>

    @Query("SELECT * FROM DoItAgainEntity WHERE engagement like :engagement")
    fun findByEngagement(engagement: String): LiveData<List<DoItAgainEntity>>

    @Insert
    fun insert(doItAgain: DoItAgainEntity)

    @Insert
    fun insertAll(vararg doItAgain: DoItAgainEntity)

    @Delete
    fun delete(doItAgain: DoItAgainEntity)

    @Delete
    fun deleteAll(vararg doItAgain: DoItAgainEntity)

    @Update
    fun updateDoItAgain(vararg doItAgain: DoItAgainEntity)
}