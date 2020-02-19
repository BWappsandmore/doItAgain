package at.bwappsandmore.doitagain.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import at.bwappsandmore.doitagain.R
import kotlinx.android.synthetic.main.insert_new_engagement_fragment.*
import at.bwappsandmore.doitagain.room.DoItAgainEntity

class InsertNewEngagementFragment : Fragment() {

    //private lateinit var viewModel: InsertNewActionViewModel
    private lateinit var viewModel: ShowDBEntriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.insert_new_engagement_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShowDBEntriesViewModel::class.java)

        backIb.setOnClickListener {
            closeThisAndOpenNewFragment()
        }

        okIb.setOnClickListener {
            promptActivityEt.text.isNotEmpty().apply {
                viewModel.insert(
                    DoItAgainEntity(
                        4,
                        promptActivityEt.text.toString(),
                        56
                    )
                )
                closeThisAndOpenNewFragment()
            }
        }
    }

    private fun closeThisAndOpenNewFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .remove(this)
            .commit()

        val fragmentManager = (activity as MainActivity).supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = ShowDBEntriesFragment()
        fragmentTransaction.add(R.id.fragment_showDBEntries, fragment)
        fragmentTransaction.commit()
    }
}
