package at.bwappsandmore.doitagain.room

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime

@Parcelize
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
) : Parcelable