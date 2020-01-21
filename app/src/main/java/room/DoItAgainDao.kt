package room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface DoItAgainDao {
    @Query("SELECT * FROM DoItAgainEntity")
    fun getAll(): LiveData<List<DoItAgainEntity>>

    @Query("SELECT * FROM DoItAgainEntity WHERE engagement like :engagement")
    fun findByEngagement(engagement: String): LiveData<List<DoItAgainEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(doItAgain: DoItAgainEntity)

    @Insert
    suspend fun insertAll(vararg doItAgain: DoItAgainEntity)

    @Delete
    suspend fun delete(doItAgain: DoItAgainEntity)

    @Delete
    suspend fun deleteAll(vararg doItAgain: DoItAgainEntity)

    @Update
    suspend fun updateDoItAgain(vararg doItAgain: DoItAgainEntity)
}