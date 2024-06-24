package com.lr.androidfeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.lr.androidfeature.repository.BaseRepository
import com.lr.androidfeature.utils.Utility
import com.lr.androidfeature.viewmodel.ViewModelFactory

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel, R : BaseRepository> : Fragment() {

    private var binding: ViewBinding? = null
    private var viewModel: ViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater.invoke(inflater, container, false)
        viewModel = ViewModelProvider(this, ViewModelFactory(repository))[bindingViewModel]
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(savedInstanceState, binding as VB, viewModel as VM)
    }

    /**
     * @method Show No Internet Screen/Dialog when is internet/wifi is not connect
     * @param tryAgainClick = Call function, methods on Try Again button click
     */
    fun showNoInternetDialog( tryAgainClick: View.OnClickListener) {
        Utility.getInstance().showNoInternetDialog(requireContext()) {
            tryAgainClick.onClick(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        viewModel = null
    }

    protected abstract fun onViewCreated(instance: Bundle?, viewBinding: VB, viewModel: VM)

    protected abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected abstract val bindingViewModel: Class<VM>

    protected abstract val repository: R
}