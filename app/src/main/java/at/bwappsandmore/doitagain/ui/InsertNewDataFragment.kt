package at.bwappsandmore.doitagain.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import at.bwappsandmore.doitagain.R
import kotlinx.android.synthetic.main.insert_new_data_fragment.*
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import org.joda.time.DateTime

class InsertNewDataFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel

    private var dateActivity = DateTime.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.insert_new_data_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        backIb.setOnClickListener {
            closeThisAndOpenNewFragment()
        }

        okIb.setOnClickListener {
            promptActivityEt.text.isNotEmpty().apply {
                viewModel.insert(
                    DoItAgainEntity(
                        0,
                        promptActivityEt.text.toString(),
                        viewModel.calculateDays(dateActivity), dateActivity
                    )
                )
                closeThisAndOpenNewFragment()
            }
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            dateActivity = DateTime(year, month + 1, dayOfMonth, 0, 1)
        }
    }

    override fun onResume() {
        super.onResume()
        if (arguments?.getString("activity_name") != null) {
            promptActivityEt.setText(arguments?.getString("activity_name"))
            calendarView.setDate(arguments!!.getLong("activity_date"), true, true)
        }
    }

    private fun closeThisAndOpenNewFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .remove(this)
            .commit()

        val fragmentManager = (activity as MainActivity).supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = DisplayDataFragment()
        fragmentTransaction.add(R.id.fragment_showDBEntries, fragment)
        fragmentTransaction.commit()
    }
}
