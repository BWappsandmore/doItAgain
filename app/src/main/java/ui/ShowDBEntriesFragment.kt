package ui


import adapter.ActivitiesAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.show_dbentries_fragment.*
import com.bwappsandmore.doitagain.R


class ShowDBEntriesFragment : Fragment() {

    private lateinit var viewModel: ShowDBEntriesViewModel
    private lateinit var layout: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ActivitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout = inflater.inflate(R.layout.show_dbentries_fragment, container, false)
        adapter = ActivitiesAdapter(context!!)
        recyclerView = layout.findViewById(R.id.recyclerview)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context!!,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerView.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL ,false)
        recyclerView.adapter = adapter
        return layout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ShowDBEntriesViewModel::class.java)

        fab.setOnClickListener {
            val fragmentManager = (activity as MainActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val fragment = InsertNewEngagementFragment()
            fragmentTransaction.replace(R.id.fragment_newEngagement, fragment)
            fragmentTransaction.commit()
        }
    }

}
