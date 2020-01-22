package ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bwappsandmore.doitagain.R
import kotlinx.android.synthetic.main.insert_new_engagement_fragment.*

class InsertNewEngagementFragment : Fragment() {

    private lateinit var viewModel: InsertNewActionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.insert_new_engagement_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(InsertNewActionViewModel::class.java)
        // TODO: Use the ViewModel

        backIb.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }
    }


}
