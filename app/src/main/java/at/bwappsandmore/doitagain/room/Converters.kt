package at.bwappsandmore.doitagain.room

import androidx.room.TypeConverter
import org.joda.time.DateTime

class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromTimestamp(value: Long?): DateTime? {
            return value?.let { DateTime(it) }
        }

        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: DateTime?): Long? {
            return date?.millis
        }
    }
}