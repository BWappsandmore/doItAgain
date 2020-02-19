package at.bwappsandmore.doitagain.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InsertNewActionViewModel : ViewModel() {
    val liveData = MutableLiveData<String>()

    fun insertActionCommunicator(act: String) {
        liveData.value = act
    }
}
