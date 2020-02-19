package at.bwappsandmore.doitagain.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DoItAgainEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "engagement")
    @NonNull
    var engagement: String,

    @ColumnInfo(name = "daysSinceCounter")
    @NonNull
    var daysSinceCounter: Int
)