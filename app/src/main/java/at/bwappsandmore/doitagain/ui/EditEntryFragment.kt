package at.bwappsandmore.doitagain.ui

import android.os.Bundle
import android.view.View
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.base.BaseFragment
import at.bwappsandmore.doitagain.viewModel.SharedViewModel
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import org.joda.time.DateTime
import kotlinx.android.synthetic.main.insert_new_data_fragment.*

class EditEntryFragment : BaseFragment<SharedViewModel>() {



    override fun getLayoutResource(): Int = R.layout.edit_data_fragment
    override fun getViewModelClass(): Class<SharedViewModel> = SharedViewModel::class.java

    companion object {
        private const val doItAgainActivityKey = "doItAgainActivity"
    }

    private lateinit var doItAgainEntity: DoItAgainEntity
    private var dateActivity = DateTime.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            doItAgainEntity = it.getParcelable(doItAgainActivityKey)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarView.date = doItAgainEntity.dateActivity.millis

        promptActivityEt.setText(doItAgainEntity.name)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            dateActivity = DateTime(year, month + 1, dayOfMonth, 0, 1)
            doItAgainEntity.dateActivity = dateActivity
        }

        backIb.setOnClickListener {
            closeThisAndOpenNewFragment()
        }

        okIb.setOnClickListener {
            onClickAction()
        }
    }

    private fun onClickAction() {
        viewModel.updateEntity(
            promptActivityEt.text.toString(),
            doItAgainEntity.dateActivity,
            doItAgainEntity.id
        )
        closeThisAndOpenNewFragment()
    }
}