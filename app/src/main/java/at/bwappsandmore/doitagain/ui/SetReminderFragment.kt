package at.bwappsandmore.doitagain.ui

import android.os.Bundle
import android.view.View
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.base.BaseSharedFragment
import at.bwappsandmore.doitagain.databinding.SetReminderFragmentBinding
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import at.bwappsandmore.doitagain.util.NotificationUtils
import at.bwappsandmore.doitagain.viewModel.SharedViewModel
import kotlinx.android.synthetic.main.set_reminder_fragment.*

class SetReminderFragment : BaseSharedFragment<SetReminderFragmentBinding, SharedViewModel>() {

    companion object {
        private const val doItAgainActivityKey = "doItAgainActivity"
    }

    override fun getLayoutResource(): Int = R.layout.set_reminder_fragment
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
        dataBinding.fragment = this

        backIb.setOnClickListener {
            closeThisAndOpenNewFragment()
        }
    }

    fun onAcceptAction() {
        NotificationUtils.setNotification(activity as MainActivity)
        closeThisAndOpenNewFragment()
    }

    fun onBackAction(){
        doItAgainEntity.hasReminderSet = !doItAgainEntity.hasReminderSet
        viewModel.setReminder(doItAgainEntity.hasReminderSet, doItAgainEntity.id)
        closeThisAndOpenNewFragment()
    }
}