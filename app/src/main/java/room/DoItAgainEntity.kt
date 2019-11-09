package room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DoItAgainEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "engagement") var engagement: String,
    @ColumnInfo(name = "daysSinceCounter") var daysSinceCounter: Int
)