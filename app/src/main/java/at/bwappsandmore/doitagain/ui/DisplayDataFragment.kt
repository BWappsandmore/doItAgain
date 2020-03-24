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

    companion object {

        private const val doItAgainActivityKey = "doItAgainActivity"

        fun getInstanceEditFragment(doItAgainEntity: DoItAgainEntity): EditEntryFragment {
            val fragment = EditEntryFragment()
            val bundle = Bundle()
            bundle.putParcelable(doItAgainActivityKey, doItAgainEntity)
            fragment.arguments = bundle
            return fragment
        }

        fun getInstanceDelFragment(doItAgainEntity: DoItAgainEntity): DeleteEntryFragment {
            val fragment = DeleteEntryFragment()
            val bundle = Bundle()
            bundle.putParcelable(doItAgainActivityKey, doItAgainEntity)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var activitiesAdapter = ActivitiesAdapter({ doItAgainEntity, actionId ->
        when (actionId) {
            ActionType.RESET_COUNTER -> viewModel.resetCounter(doItAgainEntity)
            ActionType.EDIT -> (activity as MainActivity).replaceFragment(R.id.container, getInstanceEditFragment(doItAgainEntity), true)
            else -> Log.d(null, "Finish all options")
        }
    }, { doItAgainEntity, actionId ->
        when (actionId) {
            ActionType.DELETE -> (activity as MainActivity).addFragment(R.id.container, getInstanceDelFragment(doItAgainEntity),true)
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
            return@Observer
        })

        fab.setOnClickListener {
            (activity as MainActivity).replaceFragment(
                R.id.container,
                InsertNewDataFragment(),
                true
            )
        }
    }
}
