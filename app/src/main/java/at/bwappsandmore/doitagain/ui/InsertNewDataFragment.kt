package at.bwappsandmore.doitagain.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.base.BaseSharedFragment
import at.bwappsandmore.doitagain.databinding.InsertNewDataFragmentBinding
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import at.bwappsandmore.doitagain.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.insert_new_data_fragment.*
import org.joda.time.DateTime

class InsertNewDataFragment : BaseSharedFragment<InsertNewDataFragmentBinding, SharedViewModel>() {

    companion object {
        private const val nameKey = "name"
        private const val dateKey = "date"
    }

    private var name: String = ""
    private var date: Long = 0
    private var dateActivity = DateTime.now()
    private var listEntities = listOf<DoItAgainEntity>()

    override fun getLayoutResource(): Int = R.layout.insert_new_data_fragment
    override fun getViewModelClass(): Class<SharedViewModel> = SharedViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(nameKey, "")
            date = it.getLong(dateKey)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.viewModel = viewModel
        dataBinding.name = name
        calendarView.date = if (date != 0L) date else DateTime.now().millis

        backIb.setOnClickListener {
            closeThisAndOpenNewFragment()
        }
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            dateActivity = DateTime(year, month + 1, dayOfMonth, 0, 1)
            viewModel.getDateTime(dateActivity)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getActivities().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                Log.d("viewModel.getActivities", "Entity not in DB")
                viewModel.insertDoItAgainActivity(
                    DoItAgainEntity(
                        0,
                        promptActivityEt.text.toString(),
                        viewModel.calculateDays(dateActivity),
                        dateActivity
                    )
                )
                Log.i("viewModel.getActivities", "Entity inserted in DB")

            } else listEntities = it
        })
    }

    private fun closeThisAndOpenNewFragment() {

        listEntities.forEach { entity ->
            viewModel.updateDoItAgainActivity(DoItAgainEntity(entity.id, entity.doItAgainActivity, viewModel.calculateDays(entity.dateActivity), entity.dateActivity))
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .remove(this)
            .commit()
        (activity as MainActivity).addFragment(R.id.container, DisplayDataFragment())
    }
}
