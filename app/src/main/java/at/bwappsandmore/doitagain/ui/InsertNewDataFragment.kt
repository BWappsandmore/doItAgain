package at.bwappsandmore.doitagain.ui

import android.os.Bundle
import android.view.View
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.base.BaseFragment
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import at.bwappsandmore.doitagain.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.insert_new_data_fragment.*
import org.joda.time.DateTime

class InsertNewDataFragment : BaseFragment<SharedViewModel>() {

    private var dateActivity = DateTime.now()

    override fun getLayoutResource(): Int = R.layout.insert_new_data_fragment
    override fun getViewModelClass(): Class<SharedViewModel> = SharedViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backIb.setOnClickListener {
            closeThisAndOpenNewFragment()
        }

        okIb.setOnClickListener {
            onClickAction()
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            dateActivity = DateTime(year, month + 1, dayOfMonth, 0, 1)
        }
    }

    private fun onClickAction(){
        viewModel.insertDoItAgainActivity(DoItAgainEntity(0, promptActivityEt.text.toString(), dateActivity))
        closeThisAndOpenNewFragment()
    }
}
