package com.lr.androidfeature.viewmodel

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.color
import androidx.core.text.scale
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lr.androidfeature.BaseApplication
import com.lr.androidfeature.R
import com.lr.androidfeature.api.RequestParams
import com.lr.androidfeature.api.Resource
import com.lr.androidfeature.databinding.ActivityLoginBinding
import com.lr.androidfeature.repository.AppRepository
import com.lr.androidfeature.utils.SharedPreference
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AppRepository) : ViewModel() {

    var viewBinding: ActivityLoginBinding? = null
    private val loginResult: MutableLiveData<Resource<String>> = MutableLiveData()

    fun chooseUserTypeRadioBtn(type: Int) {
        val selectedResource = R.drawable.ic_radio_selected
        val unSelectedResource = R.drawable.ic_radio_unselect
        if (type == 2) {  //type(2) = "mr"type and type(1) = "all"type
            viewBinding!!.tvRbAll.setCompoundDrawablesWithIntrinsicBounds(
                unSelectedResource,
                0,
                0,
                0
            )
            viewBinding!!.tvRbMr.setCompoundDrawablesWithIntrinsicBounds(selectedResource, 0, 0, 0)
        } else {
            viewBinding!!.tvRbAll.setCompoundDrawablesWithIntrinsicBounds(selectedResource, 0, 0, 0)
            viewBinding!!.tvRbMr.setCompoundDrawablesWithIntrinsicBounds(
                unSelectedResource,
                0,
                0,
                0
            )
        }
    }

    fun setSpannableString(context: Context) {
        val createAccountText = SpannableStringBuilder()
            .append(context.resources.getString(R.string.str_not_have_account))
            .bold {
                scale(1.1f) {
                    color(ContextCompat.getColor(context, R.color.colorTheme)) {
                        append(" " + context.resources.getString(R.string.str_create_an_account))
                    }
                }
            }
        viewBinding!!.tvCrateAnAccount.text = createAccountText

        val getInTouchText = SpannableStringBuilder()
            .append(context.resources.getString(R.string.str_need_help))
            .bold {
                scale(1.1f) {
                    color(ContextCompat.getColor(context, R.color.colorTheme)) {
                        append(" " + context.resources.getString(R.string.str_get_in_touch))
                    }
                }
            }
        viewBinding!!.tvGetInTouch.text = getInTouchText
    }

    fun isValidate(): Boolean {
        var isValid = true
        if (!viewBinding!!.etEmail.isValidate())
            isValid = false
        if (!viewBinding!!.etPassword.isValidate())
            isValid = false
        return isValid
    }

    fun callLoginApi() {
        if (isValidate()) {
            viewModelScope.launch {
                if (BaseApplication.getInstance().isConnectionAvailable()) {
                    loginResult.postValue(Resource.Loading())
                    val requestParams = HashMap<String, Any>()
                    requestParams[RequestParams.EMAIL] = viewBinding!!.etEmail.et.text.toString()
                    requestParams[RequestParams.PASSWORD] =
                        viewBinding!!.etPassword.et.text.toString()
                    requestParams[RequestParams.FCM_TOKEN] =
                        SharedPreference.getValue(SharedPreference.FCM_TOKEN, "")
                    loginResult.postValue(authRepository.login(requestParams))
                } else {
                    loginResult.postValue(Resource.ConnectionError())
                }
            }
        }
    }

    val getLoginResponse: MutableLiveData<Resource<String>> = loginResult
}