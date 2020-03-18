package at.bwappsandmore.doitagain.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.adapter.ActivitiesAdapter
import at.bwappsandmore.doitagain.base.BaseSharedFragment
import at.bwappsandmore.doitagain.databinding.DisplayDataFragmentBinding
import at.bwappsandmore.doitagain.enums.ActionType
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import at.bwappsandmore.doitagain.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.display_data_fragment.*

class DisplayDataFragment : BaseSharedFragment<DisplayDataFragmentBinding, SharedViewModel>() {

    override fun getLayoutResource(): Int = R.layout.display_data_fragment
    override fun getViewModelClass(): Class<SharedViewModel> = SharedViewModel::class.java

    //private var listEntities = listOf<DoItAgainEntity>()

    companion object {
        private const val nameKey = "name"
        private const val dateKey = "date"
        private const val doItAgainActivityKey = "doItAgainActivity"

        fun getInstance(name: String, date: Long): InsertNewDataFragment {
            val fragment = InsertNewDataFragment()
            val bundle = Bundle()
            bundle.putString(nameKey, name)
            bundle.putLong(dateKey, date)
            fragment.arguments = bundle
            return fragment
        }

        fun getInstanceDelFragment(doItAgainActivity:DoItAgainEntity): DeleteEntryFragment {
            val fragment = DeleteEntryFragment()
            val bundle = Bundle()
            bundle.putParcelable(doItAgainActivityKey, doItAgainActivity)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var activitiesAdapter = ActivitiesAdapter({ doItAgainActivity, actionId ->
        when (actionId) {
            ActionType.ResetCounter -> viewModel.resetCounter(doItAgainActivity)
            ActionType.UPDATE -> {
                val fragmentManager = (activity as MainActivity).supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                    .remove(this@DisplayDataFragment)
                val fragment = getInstance(
                    doItAgainActivity.doItAgainActivity,
                    doItAgainActivity.dateActivity.millis
                )
                fragmentTransaction.add(R.id.container, fragment)
                fragmentTransaction.commit()
            }
            else -> Log.d(null, "Finish all options")
        }
    }, { doItAgainActivity, actionId ->
        when (actionId) {
            ActionType.DELETE -> {
                val fragmentManager = (activity as MainActivity).supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                    .add(R.id.container, getInstanceDelFragment(doItAgainActivity)).addToBackStack(null)
                fragmentTransaction.commit()
            }
            else -> Log.d(null, "Finish all options")
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.displayStoredActivities()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.display_data_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerview.apply {
            addItemDecoration(DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
            adapter = activitiesAdapter
        }

        viewModel.displayStoredActivities().observe(viewLifecycleOwner, Observer {
            activitiesAdapter.setActivities(it)
            Log.d("displayStoredActivities", it.toString())
            //viewModel.updateListEntities(it)
        })

/*        listEntities.forEach { entity ->
            viewModel.updateDoItAgainActivity(
                DoItAgainEntity(
                    entity.id,
                    entity.doItAgainActivity,
                    viewModel.calculateDays(entity.dateActivity),
                    entity.dateActivity
                )
            )
        }*/

        fab.setOnClickListener {
            (activity as MainActivity).addFragment(R.id.container, InsertNewDataFragment(), true)
        }
    }
}
