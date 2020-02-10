package ui

import adapter.ActivitiesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwappsandmore.doitagain.R
import kotlinx.android.synthetic.main.show_dbentries_fragment.*

class ShowDBEntriesFragment : Fragment() {

    private lateinit var viewModel: ShowDBEntriesViewModel
    private lateinit var activitiesAdapter: ActivitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.show_dbentries_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activitiesAdapter = ActivitiesAdapter()
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

        viewModel = ViewModelProviders.of(this).get(ShowDBEntriesViewModel::class.java)

        viewModel.allActivities.observe(viewLifecycleOwner, Observer {
            activities ->
            activities?.let { activitiesAdapter.setActivities(it) }
        })

        fab.setOnClickListener {
            val fragmentManager = (activity as MainActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
                .remove(this@ShowDBEntriesFragment)
            val fragment = InsertNewEngagementFragment()
            fragmentTransaction.add(R.id.fragment_newEngagement, fragment)
            fragmentTransaction.commit()
        }
    }

}
