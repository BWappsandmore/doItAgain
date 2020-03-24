package at.bwappsandmore.doitagain.ui

import android.os.Bundle
import android.view.View
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.base.BaseSharedFragment
import at.bwappsandmore.doitagain.databinding.InsertNewDataFragmentBinding
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import at.bwappsandmore.doitagain.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.insert_new_data_fragment.*
import org.joda.time.DateTime

class InsertNewDataFragment : BaseSharedFragment<InsertNewDataFragmentBinding, SharedViewModel>() {

    private var dateActivity = DateTime.now()

    override fun getLayoutResource(): Int = R.layout.insert_new_data_fragment
    override fun getViewModelClass(): Class<SharedViewModel> = SharedViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.viewModel = viewModel
        dataBinding.fragment = this

        backIb.setOnClickListener {
            closeThisAndOpenNewFragment()
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            dateActivity = DateTime(year, month + 1, dayOfMonth, 0, 1)
        }
    }

    fun onClickAction(){
        viewModel.insertDoItAgainActivity(DoItAgainEntity(0, promptActivityEt.text.toString(), dateActivity))
        closeThisAndOpenNewFragment()
    }
}
