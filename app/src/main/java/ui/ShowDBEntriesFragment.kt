package ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.bwappsandmore.doitagain.R
import kotlinx.android.synthetic.main.show_dbentries_fragment.*

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
        viewModel = ViewModelProviders.of(this).get(ShowDBEntriesViewModel::class.java)
        // TODO: Use the ViewModel

        fab.setOnClickListener {
            val fragmentManager = (activity as MainActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val fragment = InsertNewEngagementFragment()
            fragmentTransaction.replace(R.id.fragment_newEngagement, fragment)
            fragmentTransaction.commit()
        }
    }

}
