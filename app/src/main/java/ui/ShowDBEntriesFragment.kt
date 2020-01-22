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

    private lateinit var viewModel: ShowDBEntriesViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.show_dbentries_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ActivitiesAdapter(context!!)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(adapter.mContext)
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
