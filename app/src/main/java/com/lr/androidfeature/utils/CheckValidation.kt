package com.lr.androidfeature.utils

import android.app.Activity
import android.text.TextUtils
import android.widget.RadioButton
import android.widget.RadioGroup
import java.util.regex.Pattern


/*this class is use to check velidations of android widgets
*
* */
object CheckValidation {

    /*This method is check that field is empty or not
    * @param - your input String
    */
    fun isEmpty(inputString: String): Boolean {
        return TextUtils.isEmpty(inputString.trim())
    }

    /*This method is check the patern of string
    * @param - your input String
    * @Param -pass pattern type which you required
    *  */
    fun matchPattern(inputString: String, pattern: String): Boolean {
        return !(TextUtils.isEmpty(inputString.trim()) || pattern.isEmpty()) && Pattern.matches(
            pattern,
            inputString.trim()
        )

    }

    /*This method is use to check the length of string
    * @Param - your input string
    * @Param - how much length which you required for example mobile number i will pass 10
    * */
    fun isValidLength(inputString: String, length: Int): Boolean {
        return inputString.trim().length != length
    }

    /*This method is check any radio button is selected or not in your radioGroup
    * @Param - your radiogroup id
    * */
    fun isCheckRadioButton(radioGroup: RadioGroup): Boolean {
        return radioGroup.checkedRadioButtonId == -1
    }

    /*This method is return which radio button is selected
    * @Param activity context
    * @Param your radio group is
    * */
    fun getRadioButtonId(context: Activity, radioGroup: RadioGroup): RadioButton {
        val selectedId = radioGroup.checkedRadioButtonId
        return context.findViewById(selectedId)
    }
}