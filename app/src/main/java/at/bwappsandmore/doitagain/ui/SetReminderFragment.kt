package at.bwappsandmore.doitagain.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import at.bwappsandmore.doitagain.base.BaseFragment
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import at.bwappsandmore.doitagain.viewModel.SharedViewModel
import at.bwappsandmore.doitagain.worker.NotifyWorker
import at.bwappsandmore.doitagain.enums.TransferNotifications
import org.joda.time.DateTime
import org.joda.time.Days
import java.lang.Integer.parseInt
import java.lang.Long.parseLong
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.fragment_set_reminder.*

class SetReminderFragment : BaseFragment<SharedViewModel>() {

    companion object {
        private const val doItAgainActivityKey = "doItAgainActivity"
    }

    override fun getLayoutResource(): Int = R.layout.fragment_set_reminder
    override fun getViewModelClass(): Class<SharedViewModel> = SharedViewModel::class.java

    private lateinit var doItAgainEntity: DoItAgainEntity
    private lateinit var workManager: WorkManager

    private var calcDays = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            doItAgainEntity = it.getParcelable(doItAgainActivityKey)!!
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        okIb.setOnClickListener {
            onAcceptAction()
        }

        backIb.setOnClickListener {
            onBackAction()
            closeThisAndOpenNewFragment()
        }
    }

    private fun onAcceptAction() {
        calcDays = if (daysET.text.toString().isNotEmpty()) {
            Days.daysBetween(
                doItAgainEntity.dateActivity.toLocalDate(),
                DateTime.now().toLocalDate()
            ).days + parseInt(daysET.text.toString())
        } else Days.daysBetween(
            doItAgainEntity.dateActivity.toLocalDate(),
            DateTime.now().toLocalDate()
        ).days

        val data = Data.Builder()
            .putString(TransferNotifications.TITLE.type, doItAgainEntity.name)
            .putString(TransferNotifications.DAYS.type, calcDays.toString())
            .putInt("EntityID", doItAgainEntity.id)
            .build()
        workManager = WorkManager.getInstance(requireContext())
        val notificationBuilder = OneTimeWorkRequest.Builder(NotifyWorker::class.java)
            .setInitialDelay(parseLong(daysET.text.toString()), TimeUnit.DAYS)
            .setInputData(data)
            .addTag(doItAgainEntity.name)
            .build()
        workManager.enqueue(notificationBuilder)
        Toast.makeText(
            context,
            requireContext().resources.getString(R.string.toast_notify) + " " + daysET.text.toString() + " " + requireContext().resources.getString(
                R.string.days
            ) + ".",
            Toast.LENGTH_LONG
        ).show()
        closeThisAndOpenNewFragment()
    }

    private fun onBackAction() {
        doItAgainEntity.hasReminderSet = !doItAgainEntity.hasReminderSet
        viewModel.setReminder(doItAgainEntity.hasReminderSet, doItAgainEntity.id)
        closeThisAndOpenNewFragment()
    }
}