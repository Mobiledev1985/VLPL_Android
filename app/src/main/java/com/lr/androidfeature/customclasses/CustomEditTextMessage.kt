package com.lr.androidfeature.customclasses

import android.content.Context
import android.content.res.Resources.getSystem
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.lr.androidfeature.R
import java.util.*
import java.util.regex.Pattern


class CustomEditTextMessage : ConstraintLayout {

    lateinit var et: EditText
    lateinit var tvError: TextView
    lateinit var img: ImageView
    private lateinit var tvLabel: TextView
    private var validateType: String? = null
    private var emptyValidateText: String? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        inIt(context, attrs, -1)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        inIt(context, attrs, defStyleAttr)
    }

    private fun inIt(context: Context, attrs: AttributeSet, defStyleAttr: Int) {
        val itemView: View =
            LayoutInflater.from(context)
                .inflate(R.layout.custom_edit_text_message_layout, this, true)
        et = itemView.findViewById(R.id.et)
        img = itemView.findViewById(R.id.img)
        tvError = itemView.findViewById(R.id.tvError)
        tvLabel = itemView.findViewById(R.id.tvLabel)

        attrs.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomEditText, 0, 0)
            val hint = typedArray.getString(R.styleable.CustomEditText_android_hint)
            val value = typedArray.getString(R.styleable.CustomEditText_valueText)
            val enable = typedArray.getBoolean(R.styleable.CustomEditText_android_enabled, true)
            if (typedArray.getString(R.styleable.CustomEditText_validateType) != null)
                validateType = typedArray.getString(R.styleable.CustomEditText_validateType)
            if (typedArray.getString(R.styleable.CustomEditText_emptyValidateText) != null)
                emptyValidateText = "Please ${hint?.lowercase(Locale.ROOT)}"
            //typedArray.getString(R.styleable.CustomEditText_emptyValidateText)
            val imageVisible = typedArray.getBoolean(R.styleable.CustomEditText_imageVisible, false)
            val isAllowSpace = typedArray.getBoolean(R.styleable.CustomEditText_isAllowSpace, true)
            val dynamicHeight = typedArray.getInteger(R.styleable.CustomEditText_dynamicHeight, 45)
            val maxLines = typedArray.getInteger(R.styleable.CustomEditText_android_maxLines, 1)
            val maxLength =
                typedArray.getInteger(R.styleable.CustomEditText_android_maxLength, 50)
            val drawableStart =
                typedArray.getResourceId(R.styleable.CustomEditText_android_drawableStart, 0)
            val inputType =
                typedArray.getInteger(
                    R.styleable.CustomEditText_android_inputType,
                    EditorInfo.TYPE_CLASS_TEXT
                )
            val imeOption = typedArray.getInteger(R.styleable.CustomEditText_android_imeOptions, 0)
            et.hint = hint
            tvLabel.text = value
            et.isEnabled = enable
            et.inputType = inputType
            et.imeOptions = imeOption
            et.maxLines = maxLines
            et.layoutParams.height = dynamicHeight.px
            img.visibility = if (imageVisible) View.VISIBLE else View.GONE
            et.setCompoundDrawablesWithIntrinsicBounds(drawableStart, 0, 0, 0)
            val filterArray = arrayOfNulls<InputFilter>(1)
            filterArray[0] = InputFilter.LengthFilter(maxLength)
            et.filters = filterArray

            et.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (p0.toString().trim().isEmpty() && p0.toString().contains(" ")) {
                        et.setText("")
                    }
                }

                override fun afterTextChanged(p: Editable?) {
                    tvError.visibility = View.GONE
                    if (!isAllowSpace) {
                        val result: String = p.toString().replace(" ", "")
                        if (p.toString() != result) {
                            et.setText(result)
                            et.setSelection(result.length) // alert the user
                        }
                    }
                }
            })

            et.setOnFocusChangeListener { _, b ->
                if (!b) {
                    checkValidation(et.text.toString().trim())
                    if (tvError.text.isNullOrEmpty())
                        tvError.visibility = View.GONE
                    else
                        tvError.visibility = View.VISIBLE
                }
            }
            if (validateType == context.resources.getString(R.string.validate_type_password)) {
                val typeface = ResourcesCompat.getFont(context, R.font.inter_semi_bold)
                et.typeface = typeface
            }
            typedArray.recycle()
        }
    }

    // covert DP to PX of integer value for edit text height
    private val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()

    fun isValidate(): Boolean {
        val isValid = checkValidation(et.text.toString().trim())
        if (isValid)
            tvError.visibility = View.GONE
        else
            tvError.visibility = View.VISIBLE
        return isValid
    }

    fun setBackgroundNonEditableColor(isEditable: Boolean) {
        if (isEditable) {
            et.setBackgroundResource(R.drawable.bg_solid_white_radius_5)
        } else {
            et.setBackgroundResource(R.drawable.bg_solid_gray_radius_5_edittext)
        }
    }

    fun passwordEyeVisible() {
        et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                img.visibility = View.GONE
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                img.visibility = View.VISIBLE
                if (p0.toString().isEmpty())
                    img.visibility = View.GONE
            }
        })
    }

    fun passwordEyeClick() {
        img.setOnClickListener {
            val resId: Int
            val method: TransformationMethod
            if (et.transformationMethod.equals(PasswordTransformationMethod.getInstance())) {
                method = HideReturnsTransformationMethod.getInstance()
                resId = R.drawable.ic_visible_on
            } else {
                method = PasswordTransformationMethod.getInstance()
                resId = R.drawable.ic_visibility_off
            }
            et.transformationMethod = method
            img.setImageResource(resId)
            et.setSelection(et.length())
        }
    }

    fun setEnable(enabled: Boolean) {
        et.isEnabled = enabled
    }

    private fun checkValidation(text: String): Boolean {
        return when (validateType) {
            context.resources.getString(R.string.validate_type_mobile) -> mobileNoValidate(text)
            context.resources.getString(R.string.validate_type_email) -> emailValidate(text)
            context.resources.getString(R.string.validate_type_empty) -> emptyValidation(text)
            context.resources.getString(R.string.validate_type_gst) -> gstValidate(text)
            context.resources.getString(R.string.validate_type_username) -> mobileEmailValidate(text)
            context.resources.getString(R.string.validate_type_password) -> passwordValidate(text)
            context.resources.getString(R.string.validate_type_password_strong) -> passwordPattern(
                text
            )
            context.resources.getString(R.string.validate_type_pin_code) -> pinCodeValidate(text)
            else -> emptyValidation(text)
        }
    }

    private fun mobileNoValidate(text: String): Boolean {
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = InputFilter.LengthFilter(10)
        et.filters = filterArray
        return if (text.isEmpty()) {
            tvError.text = emptyValidateText
            false
        } else {
            if (text.length >= 10) {
                tvError.text = ""
                true
            } else {
                tvError.text = context.resources.getString(R.string.validation_mobile_length)
                false
            }
        }
    }

    private fun emailValidate(text: String): Boolean {
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = InputFilter.LengthFilter(255)
        et.filters = filterArray
        return if (text.isEmpty()) {
            tvError.text = emptyValidateText
            false
        } else {
            if (isEmailValid(text)) {
                tvError.text = ""
                true
            } else {
                tvError.text = context.resources.getString(R.string.validation_email_pattern)
                false
            }
        }
    }

    private fun gstValidate(text: String): Boolean {
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = InputFilter.LengthFilter(15)
        et.filters = filterArray
        return if (text.isEmpty()) {
            tvError.text = emptyValidateText
            false
        } else {
            if (isGSTValid(text)) {
                tvError.text = ""
                true
            } else {
                tvError.text = context.resources.getString(R.string.validation_gst_pattern)
                false
            }
        }
    }

    private fun emptyValidation(text: String): Boolean {
        return if (text.isEmpty()) {
            tvError.text = emptyValidateText
            false
        } else {
            tvError.text = ""
            true
        }
    }

    private fun mobileEmailValidate(text1: String): Boolean {
        var text = text1
        return if (emptyValidation(text)) {
            if (text[0] == '+') {
                text = ""
                text = text.replace("+", "")
            }
            val pattern = Pattern.compile("[0-9]+")
            val matcher = pattern.matcher(text)
            if (!matcher.matches()) {
                emailValidate(text)
            } else
                mobileNoValidate(text)
        } else
            false
    }

    private fun passwordPattern(text: String): Boolean {
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = InputFilter.LengthFilter(15)
        return if (text.isEmpty()) {
            tvError.text = emptyValidateText
            false
        } else {
            if (isPasswordValid(text)) {
                tvError.text = ""
                true
            } else {
                when {
                    text.length < 6 -> {
                        tvError.text =
                            context.resources.getString(R.string.validation_password_length)
                        false
                    }
                    !text.contains(Regex("[A-Z]")) -> {
                        tvError.text = "Password must contain one capital letter"
                        false
                    }
                    !text.contains(Regex("[a-z]")) -> {
                        tvError.text = "Password must contain one small letter"
                        false
                    }
                    !text.contains(Regex("[@#\$%^&*+_`=/?!~₹-]")) -> {
                        tvError.text =
                            "Password must contain one special symbol ( @#\$%^&*+_`=/?!~₹- )"
                        false
                    }
                    !text.contains(Regex("[0-9]")) -> {
                        tvError.text = "Password must contain one digit"
                        false
                    }
                    else -> {
                        tvError.text = "White space not allow"
                        false
                    }
                }
            }
        }
    }

    //    ^[a-zA-Z]{4,}(?: [a-zA-Z]+){0,2}$
    private fun isEmailValid(email: String): Boolean {
        val emailPattern =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern = Pattern.compile(emailPattern)
        return pattern.matcher(email).matches()
    }

    private fun isGSTValid(gst: String): Boolean {
        val gstPattern =
            "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}\$"
//            "^([0][1-9]|[1-2][0-9]|[3][0-7])([a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9a-zA-Z]{1}[zZ]{1}[0-9a-zA-Z]{1})+\$"
        val pattern = Pattern.compile(gstPattern)
        return pattern.matcher(gst).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&*+_`=/?!~₹-])(?=\\S+\$).{6,}$"
        val pattern = Pattern.compile(passwordPattern)
        return pattern.matcher(password).matches()
    }

    private fun passwordValidate(text: String): Boolean {
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = InputFilter.LengthFilter(15)
        return if (text.isEmpty()) {
            tvError.text = emptyValidateText
            false
        } else {
            if (text.length >= 6) {
                tvError.text = ""
                true
            } else {
                tvError.text = context.resources.getString(R.string.validation_password_length)
                false
            }
        }
    }

    private fun pinCodeValidate(text: String): Boolean {
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = InputFilter.LengthFilter(6)
        return if (text.isEmpty()) {
            tvError.text = emptyValidateText
            false
        } else {
            if (text.length >= 6) {
                tvError.text = ""
                true
            } else {
                tvError.text = context.resources.getString(R.string.validation_pin_code_length)
                false
            }
        }
    }
}