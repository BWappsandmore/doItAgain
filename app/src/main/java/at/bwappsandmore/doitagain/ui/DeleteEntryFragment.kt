package at.bwappsandmore.doitagain.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.base.BaseSharedFragment
import at.bwappsandmore.doitagain.databinding.DeleteEntryFragmentBinding
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import at.bwappsandmore.doitagain.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.display_data_fragment.*
import org.joda.time.DateTime
import org.joda.time.Days

class DeleteEntryFragment : BaseSharedFragment<DeleteEntryFragmentBinding, SharedViewModel>() {

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.viewModel = viewModel
        dataBinding.doItAgainEntity = doItAgainEntity
        dataBinding.fragment = this
    }

    fun onClickActionDelete(){
        viewModel.deleteDoItAgainActivity(doItAgainEntity)
        closeThisAndOpenNewFragment()
    }

    fun onClickActionShare(){
        val sendMessage = doItAgainEntity.name + "\n \n"+context?.resources?.getString(R.string.since)+" " + Days.daysBetween(
            doItAgainEntity.dateActivity.toLocalDate(),
            DateTime.now().toLocalDate()
        ).days.toString() + " "+context?.resources?.getString(R.string.days)+"."
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