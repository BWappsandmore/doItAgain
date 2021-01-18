package at.bwappsandmore.doitagain.ui

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkManager
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.adapter.ActivitiesAdapter
import at.bwappsandmore.doitagain.base.BaseSharedFragment
import at.bwappsandmore.doitagain.databinding.DisplayDataFragmentBinding
import at.bwappsandmore.doitagain.enums.ActionType
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import at.bwappsandmore.doitagain.util.SwipeController
import at.bwappsandmore.doitagain.util.SwipeControllerActions
import at.bwappsandmore.doitagain.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.display_data_fragment.*

class DisplayDataFragment : BaseSharedFragment<DisplayDataFragmentBinding, SharedViewModel>() {

    override fun getLayoutResource(): Int = R.layout.display_data_fragment
    override fun getViewModelClass(): Class<SharedViewModel> = SharedViewModel::class.java

    private lateinit var workManager: WorkManager

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

        fun getInstanceSetReminderFragment(doItAgainEntity: DoItAgainEntity): SetReminderFragment {
            val fragment = SetReminderFragment()
            val bundle = Bundle()
            bundle.putParcelable(doItAgainActivityKey, doItAgainEntity)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var activitiesAdapter = ActivitiesAdapter({ doItAgainEntity, actionId ->
        when (actionId) {
            ActionType.EDIT -> (activity as MainActivity).replaceFragment(
                R.id.container,
                getInstanceEditFragment(doItAgainEntity),
                true
            )
            else -> {
            }
        }
    }, { doItAgainEntity, actionId ->
        when (actionId) {
            ActionType.DELETE -> (activity as MainActivity).addFragment(
                R.id.container,
                getInstanceDelFragment(doItAgainEntity),
                true
            )
            else -> {
            }
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
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = activitiesAdapter
        }

        val swipeController = SwipeController(requireContext(), object : SwipeControllerActions() {
            override fun onRightClicked(position: Int) {
                (activitiesAdapter.activities[position]).hasReminderSet =
                    !(activitiesAdapter.activities[position]).hasReminderSet
                viewModel.setReminder(
                    (activitiesAdapter.activities[position]).hasReminderSet,
                    (activitiesAdapter.activities[position]).id
                )
                if ((activitiesAdapter.activities[position]).hasReminderSet)
                    (activity as MainActivity).addFragment(
                        R.id.container,
                        getInstanceSetReminderFragment(activitiesAdapter.activities[position]),
                        true
                    )
                else {
                    workManager = WorkManager.getInstance(context!!)
                    workManager.cancelAllWorkByTag((activitiesAdapter.activities[position]).name)
                }
            }

            override fun onLeftClicked(position: Int) {
                viewModel.getMaxValue(activitiesAdapter.activities[position].id)
                    .observeOnce(viewLifecycleOwner, {
                        viewModel.setMaxValue( it, viewModel.calculateDays(activitiesAdapter.activities[position].dateActivity), activitiesAdapter.activities[position].id )
                        viewModel.resetCounter(activitiesAdapter.activities[position])
                        Toast.makeText(context, context?.getString(R.string.longest_idle)+" $it "+context?.getString(R.string.day),Toast.LENGTH_SHORT).show()
                    })
            }
        })

        val itemTouchHelper = ItemTouchHelper(swipeController)
        itemTouchHelper.attachToRecyclerView(recyclerview)

        recyclerview.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                swipeController.onDraw(c)
            }
        })

        viewModel.displayStoredActivities().observe(viewLifecycleOwner, Observer {
            activitiesAdapter.setActivities(it)
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


