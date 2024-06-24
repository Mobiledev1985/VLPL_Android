package com.lr.androidfeature.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.lr.androidfeature.BaseApplication
import com.lr.androidfeature.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

//This class is a collection of common methods which are use in daily routine
class Utility {

    companion object {
        private var instance: Utility? = null

        fun getInstance(): Utility {
            if (instance == null) {
                instance = Utility()
            }
            return instance as Utility
        }
    }

    @SuppressLint("HardwareIds")
    //get device token for different devices
    fun getDeviceToken(context: Context?): String {
        return Settings.Secure.getString(context!!.contentResolver, Settings.Secure.ANDROID_ID)
    }

    //this method will return 32 characters authenticate value while api calling
    fun getMD5EncryptedString(inputString: String): String {
        var mdEnc: MessageDigest? = null
        try {
            mdEnc = MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        mdEnc!!.update(inputString.toByteArray(), 0, inputString.length)
        var md5 = BigInteger(1, mdEnc.digest()).toString(16)
        while (md5.length < 32) {
            md5 = "0$md5"
        }
        return md5
    }

    /*This method is open your keyboard on click of edittext
   * @param pass your context
   * @Param pass your edittext id
   */
    fun launchKeyboard(mContext: Activity?, mEditText: EditText) {
        mEditText.postDelayed({
            val keyboard =
                mContext!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(mEditText, 0)
        }, 100)
    }

    /*This method is hide your keyboard on outside touch of screen
    * @Param pass your parent view id of activity of fragment
    */
    fun outSideTouchHideKeyboard(view: View) {
        // Set up touch listener for non-text box views to hideAnimateDialog keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { p0, p1 ->
                when (p1?.action) {
                    MotionEvent.ACTION_DOWN -> {}
                    MotionEvent.ACTION_UP -> p0?.performClick()
                    else -> {}
                }
                true
            }
        }

        // If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                outSideTouchHideKeyboard(innerView)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun hideKeyBoardWhenTouchOutside(view: View) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, _ ->
                hideKeyboard(v)
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                hideKeyBoardWhenTouchOutside(innerView)
            }
        }
    }

    fun getTextRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    fun getRequestBody(fileKey: String, value: String): MultipartBody.Part {
        return MultipartBody.Part.createFormData(fileKey, value)
    }

    fun getMultipartBody(fileKey: String, file: File): MultipartBody.Part {
        val fileReqBody: RequestBody = file.asRequestBody("*/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(fileKey, file.name, fileReqBody)
    }

    fun getPermissionForSDK33(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }

    fun showNoInternetDialog(context: Context, tryAgainClick: View.OnClickListener) {
        val dialog = Dialog(context, R.style.AppTheme_Theme)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_no_internet, null)
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        view.findViewById<AppCompatButton>(R.id.btnTryAgain)
            .setOnClickListener {
                if (BaseApplication.getInstance().isConnectionAvailable()) {
                    dialog.dismiss()
                    tryAgainClick.onClick(it)
                }
            }
        dialog.show()
    }

    fun openDial(
        context: Context,
        number: String = context.resources.getString(R.string.str_contact_number)
    ) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        context.startActivity(intent)
    }

    fun openEmail(context: Context) {
        val emailIntent = Intent(
            Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", context.resources.getString(R.string.str_vlpl_connect_com), null
            )
        ).apply {
            putExtra(Intent.EXTRA_EMAIL, "address")
            putExtra(Intent.EXTRA_SUBJECT, "Subject")
            putExtra(Intent.EXTRA_TEXT, "Body")
        }
        context.startActivity(Intent.createChooser(emailIntent, "Send Email..."))
    }
}