package at.bwappsandmore.doitagain.ui


import at.bwappsandmore.doitagain.R
import at.bwappsandmore.doitagain.base.BaseSharedFragment
import at.bwappsandmore.doitagain.databinding.DeleteEntryFragmentBinding
import at.bwappsandmore.doitagain.viewModel.SharedViewModel

class DeleteEntryFragment : BaseSharedFragment<DeleteEntryFragmentBinding, SharedViewModel>() {
    override fun getLayoutResource(): Int = R.layout.delete_entry_fragment
    override fun getViewModelClass(): Class<SharedViewModel> = SharedViewModel::class.java
}