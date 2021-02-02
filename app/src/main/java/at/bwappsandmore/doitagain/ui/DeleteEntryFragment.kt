package at.bwappsandmore.doitagain.ui

import android.content.Intent
import android.os.Bundle
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.base.BaseFragment
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import at.bwappsandmore.doitagain.viewModel.SharedViewModel
import org.joda.time.DateTime
import org.joda.time.Days
import kotlinx.android.synthetic.main.delete_entry_fragment.*


class DeleteEntryFragment : BaseFragment<SharedViewModel>() {

    companion object {
        private const val doItAgainActivityKey = "doItAgainActivity"
    }

    override fun getLayoutResource(): Int = R.layout.delete_entry_fragment
    override fun getViewModelClass(): Class<SharedViewModel> = SharedViewModel::class.java

    private lateinit var doItAgainEntity: DoItAgainEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            doItAgainEntity = it.getParcelable(doItAgainActivityKey)!!
        }

        deleteIB.setOnClickListener {
            onClickActionDelete()
        }

        shareIB.setOnClickListener {
            onClickActionShare()
        }
    }

    private fun onClickActionDelete() {
        viewModel.deleteDoItAgainActivity(doItAgainEntity)
        closeThisAndOpenNewFragment()
    }

    private fun onClickActionShare() {
        val sendMessage =
            doItAgainEntity.name + "\n \n" + context?.resources?.getString(R.string.since) + " " + Days.daysBetween(
                doItAgainEntity.dateActivity.toLocalDate(),
                DateTime.now().toLocalDate()
            ).days.toString() + " " + context?.resources?.getString(R.string.days) + "."
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_SUBJECT, "DoItAgain")
            putExtra(Intent.EXTRA_TEXT, sendMessage)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "DoItAgain")
        startActivity(shareIntent)
    }
}