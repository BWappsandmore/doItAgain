package at.bwappsandmore.doitagain.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.base.BaseSharedFragment
import at.bwappsandmore.doitagain.databinding.InsertNewDataFragmentBinding
import at.bwappsandmore.doitagain.enums.ActionType
import at.bwappsandmore.doitagain.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.insert_new_data_fragment.*
import org.joda.time.DateTime

class InsertNewDataFragment : BaseSharedFragment<InsertNewDataFragmentBinding, SharedViewModel>() {

    companion object {
        private const val nameKey = "name"
        private const val dateKey = "date"

        fun getInstance(name: String, date: Long): InsertNewDataFragment {
            val fragment = InsertNewDataFragment()
            val bundle = Bundle()
            bundle.putString(nameKey, name)
            bundle.putLong(dateKey, date)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var date: Long = 0
    lateinit var name: String
    private var dateActivity = DateTime.now()

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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        backIb.setOnClickListener {
            closeThisAndOpenNewFragment()
        }

        okIb.setOnClickListener {
            promptActivityEt.text.isNotEmpty().apply {

                viewModel.findActivity(promptActivityEt.text.toString())
                dataBinding.name = promptActivityEt.text.toString()

                //viewModel.activityAction(doItAgainActivity.id, doItAgainActivity, ActionType.FindByActivity)


//                if (doItAgainActivity.doItAgainActivity.isEmpty())
//                viewModel.insert(
//                    DoItAgainEntity(
//                        0,
//                        promptActivityEt.text.toString(),
//                        viewModel.calculateDays(dateActivity), dateActivity
//                    )
//                )
                /*               else
                                   viewModel.update(DoItAgainEntity(
                                       0,
                                       promptActivityEt.text.toString(),
                                       viewModel.calculateDays(dateActivity), dateActivity
                                   ))*/

                closeThisAndOpenNewFragment()
            }
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            dateActivity = DateTime(year, month + 1, dayOfMonth, 0, 1)
        }
    }

    private fun closeThisAndOpenNewFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .remove(this)
            .commit()
        (activity as MainActivity).addFragment(R.id.container, DisplayDataFragment())
    }
}
