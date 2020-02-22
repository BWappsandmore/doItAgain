package at.bwappsandmore.doitagain.ui

import at.bwappsandmore.doitagain.adapter.ActivitiesAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import kotlinx.android.synthetic.main.recyclerview_item.*
import kotlinx.android.synthetic.main.show_dbentries_fragment.*

class DisplayDataFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel

    private lateinit var activitiesAdapter: ActivitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.show_dbentries_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activitiesAdapter =
            ActivitiesAdapter()
        recyclerview.apply {
            addItemDecoration(
                DividerItemDecoration(
                    context!!,
                    LinearLayoutManager.VERTICAL
                )
            )
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
            adapter = activitiesAdapter
        }

        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        viewModel.allActivities.observe(viewLifecycleOwner, Observer { activities ->
            activities?.let {
                activitiesAdapter.setActivities(it)
                //viewModel.resetCounter(it[0])
            }
        })

        activitiesAdapter.setOnItemClickListener {
            viewModel.findActivityById(it)
        }
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
