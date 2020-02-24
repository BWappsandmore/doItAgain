package at.bwappsandmore.doitagain.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity
data class DoItAgainEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "doItAgainActivity")
    @NonNull
    var doItAgainActivity: String,

    @ColumnInfo(name = "daysSinceCounter")
    @NonNull
    var daysSinceCounter: Int,

    @ColumnInfo(name = "dateActivity")
    @NonNull
    var dateActivity: DateTime
)