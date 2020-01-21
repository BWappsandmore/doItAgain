package ui


import adapter.ActivitiesAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.show_dbentries_fragment.*
import com.bwappsandmore.doitagain.R


class ShowDBEntriesFragment : Fragment() {

    companion object {
        fun newInstance() = ShowDBEntriesFragment()
    }

    private lateinit var viewModel: ShowDBEntriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.show_dbentries_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerView = (activity as MainActivity).findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ActivitiesAdapter(activity as MainActivity)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity as MainActivity)

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
