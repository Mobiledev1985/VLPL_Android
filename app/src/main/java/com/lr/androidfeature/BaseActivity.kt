package com.lr.androidfeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.lr.androidfeature.repository.BaseRepository
import com.lr.androidfeature.utils.Utility
import com.lr.androidfeature.viewmodel.ViewModelFactory

abstract class BaseActivity<VM : ViewModel, VB : ViewBinding, R : BaseRepository> :
    AppCompatActivity() {

    private var binding: ViewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding?.root)
        val viewModel = ViewModelProvider(this, ViewModelFactory(repository))[bindingViewModel]
        onCreate(savedInstanceState, viewModel, binding as VB)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    /**
     * @method Show No Internet Screen/Dialog when is internet/wifi is not connect
     * @param tryAgainClick = Call function, methods on Try Again button click
     */
    fun showNoInternetDialog( tryAgainClick: View.OnClickListener) {
        Utility.getInstance().showNoInternetDialog(this) {
            tryAgainClick.onClick(it)
        }
    }

    protected abstract fun onCreate(instance: Bundle?, viewModel: VM, viewBinding: VB)

    protected abstract val bindingInflater: (LayoutInflater) -> VB

    protected abstract val bindingViewModel: Class<VM>

    protected abstract val repository: R
}