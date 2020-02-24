package at.bwappsandmore.doitagain.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.adapter.ActivitiesAdapter
import kotlinx.android.synthetic.main.display_data_fragment.*

class DisplayDataFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel

    private var activitiesAdapter = ActivitiesAdapter { doItAgainActivity, actionId ->
        when(actionId) {
            0 -> viewModel.resetCounter(doItAgainActivity)
            1 -> {
                val fragmentManager = (activity as MainActivity).supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                    .remove(this@DisplayDataFragment)
                val fragment = InsertNewDataFragment()
                val bundle = Bundle()
                bundle.putString("activity_name",doItAgainActivity.doItAgainActivity)
                fragment.arguments = bundle
                fragmentTransaction.add(R.id.fragment_newEngagement, fragment)
                fragmentTransaction.commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
            View = inflater.inflate(R.layout.display_data_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerview.apply {
            addItemDecoration(DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
            adapter = activitiesAdapter
        }

        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        viewModel.allActivities.observe(viewLifecycleOwner, Observer { activities ->
            activities?.let {
                activitiesAdapter.setActivities(it)
            }
        })

        fab.setOnClickListener {
            val fragmentManager = (activity as MainActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
                .remove(this@DisplayDataFragment)
            val fragment =
                InsertNewDataFragment()
            fragmentTransaction.add(R.id.fragment_newEngagement, fragment)
            fragmentTransaction.commit()
        }
    }
}
