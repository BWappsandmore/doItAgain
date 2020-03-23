package at.bwappsandmore.doitagain.ui

import android.os.Bundle
import android.view.View
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.base.BaseSharedFragment
import at.bwappsandmore.doitagain.viewModel.SharedViewModel
import at.bwappsandmore.doitagain.databinding.EditDataFragmentBinding
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import kotlinx.android.synthetic.main.insert_new_data_fragment.*
import org.joda.time.DateTime

class EditEntryFragment : BaseSharedFragment<EditDataFragmentBinding, SharedViewModel>() {
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
        dataBinding.viewModel = viewModel
        dataBinding.fragment = this
        dataBinding.doItAgainEntity = doItAgainEntity
        calendarView.date = doItAgainEntity.dateActivity.millis

        backIb.setOnClickListener {
            closeThisAndOpenNewFragment()
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            dateActivity = DateTime(year, month + 1, dayOfMonth, 0, 1)
            doItAgainEntity.dateActivity = dateActivity
        }
    }

    fun onClickAction() {
        viewModel.updateEntity(
            promptActivityEt.text.toString(),
            viewModel.calculateDays(doItAgainEntity.dateActivity),
            doItAgainEntity.dateActivity,
            doItAgainEntity.id
        )
        closeThisAndOpenNewFragment()
    }
}