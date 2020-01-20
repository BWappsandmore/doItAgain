package ui


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import room.AppDatabase


class ShowDBEntriesViewModel : AndroidViewModel(
    Application()
) {

    val appDatabase = Room.databaseBuilder(
        getApplication(),
        AppDatabase::class.java, "doitagain-list.db"
    ).build()
}
