package at.bwappsandmore.doitagain.ui

import android.os.Bundle
import android.view.View
import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.base.BaseSharedFragment
import at.bwappsandmore.doitagain.databinding.DeleteEntryFragmentBinding
import at.bwappsandmore.doitagain.room.DoItAgainEntity
import at.bwappsandmore.doitagain.viewModel.SharedViewModel

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

    fun onClickAction(){
        viewModel.deleteDoItAgainActivity(doItAgainEntity)
        closeThisAndOpenNewFragment()
    }
}