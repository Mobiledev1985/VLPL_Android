package com.lr.androidfeature.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.lr.androidfeature.BaseActivity
import com.lr.androidfeature.R
import com.lr.androidfeature.api.Resource
import com.lr.androidfeature.api.RetrofitBuilder
import com.lr.androidfeature.customclasses.CustomDialog
import com.lr.androidfeature.databinding.ActivityLoginBinding
import com.lr.androidfeature.helper.LoginHelper
import com.lr.androidfeature.repository.AppRepository
import com.lr.androidfeature.utils.ActivityNav
import com.lr.androidfeature.utils.Utility
import com.lr.androidfeature.utils.errorSnack
import com.lr.androidfeature.viewmodel.LoginViewModel

class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding, AppRepository>(),
    View.OnClickListener {

    private lateinit var viewModel: LoginViewModel
    private lateinit var viewBinding: ActivityLoginBinding

    override fun onCreate(
        instance: Bundle?,
        viewModel: LoginViewModel,
        viewBinding: ActivityLoginBinding
    ) {
        this.viewModel = viewModel
        this.viewBinding = viewBinding
        viewModel.viewBinding = viewBinding
        Utility.getInstance().hideKeyBoardWhenTouchOutside(viewBinding.rootLayout)
        viewBinding.clickListener = this
        viewBinding.vm = viewModel
        viewModel.setSpannableString(this)
        manageLoginResponse()
        onEyePasswordClick()
    }

    override val bindingInflater: (LayoutInflater) -> ActivityLoginBinding
        get() = ActivityLoginBinding::inflate

    override val bindingViewModel: Class<LoginViewModel>
        get() = LoginViewModel::class.java

    override val repository: AppRepository
        get() {
            return AppRepository(RetrofitBuilder.apiService)
        }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.ivBack -> onBackPressedDispatcher.onBackPressed()
            R.id.tvCrateAnAccount -> {
                ActivityNav.getInstance()!!.killActivity(this as Activity?)
            }

            R.id.btnLoginButton -> {
                viewModel.callLoginApi()
            }

            R.id.tvEmail -> {
                val emailIntent = Intent(
                    Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", resources.getString(R.string.str_vlpl_connect_com), null
                    )
                )
                emailIntent.putExtra(Intent.EXTRA_EMAIL, "address")
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
                startActivity(Intent.createChooser(emailIntent, "Send Email..."))
            }

            R.id.tvCall -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:" + resources.getString(R.string.str_contact_number))
                startActivity(intent)
            }
        }
    }

    private fun onEyePasswordClick() {
        viewBinding.etPassword.passwordEyeVisible()
        viewBinding.etPassword.passwordEyeClick()
    }

    private fun manageLoginResponse() {
        viewModel.getLoginResponse.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    CustomDialog.getInstance().hide()
                    response.data?.let { data ->
                        LoginHelper.getInstance()?.saveAuthToken(data)
                        //after login Call other Activity
                    }
                }

                is Resource.Error -> {
                    CustomDialog.getInstance().hide()
                    viewBinding.rootLayout.errorSnack(response.message!!, Snackbar.LENGTH_LONG)
                }

                is Resource.Loading -> {
                    CustomDialog.getInstance().showDialog(this)
                }

                is Resource.ConnectionError -> {
                    showNoInternetDialog { viewModel.callLoginApi() }
                }
            }
        }
    }
}
