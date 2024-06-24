package com.lr.androidfeature.helper

import com.google.gson.GsonBuilder
import com.lr.androidfeature.utils.SharedPreference
import org.json.JSONObject

class LoginHelper {
    companion object {

        private const val USER_OBJECT: String = "USER_DETAILS"
        private const val AUTH_TOKEN = "accessToken"

        private var instance: LoginHelper? = null
        fun getInstance(): LoginHelper? {
            if (instance == null) {
                instance = LoginHelper()
            }
            return instance
        }
    }

    /**
     * for save login data
     * @param value -> userModel object that extend UserBaseModel
     */
    fun saveLogin(value: Any) {
        val gson =
            GsonBuilder().enableComplexMapKeySerialization().serializeNulls().setPrettyPrinting()
                .create()
        val json = gson.toJson(value).replace(Regex("[\n\t]"), "")
        SharedPreference.setValue(USER_OBJECT, json)
    }

    /**
     * for save login data
     * @param value -> userModel object that extend UserBaseModel
     */
    fun saveLogin(value: JSONObject) {
        SharedPreference.setValue(USER_OBJECT, value.toString())
    }

    /**
     * for get Login data
     * @param value -> empty user model
     * after get data cast with your userModel class
     */
    fun getLoginData(value: Any): Any? {
        val gson =
            GsonBuilder().enableComplexMapKeySerialization().serializeNulls().setPrettyPrinting()
                .create()
        val json: String = SharedPreference.getValue(USER_OBJECT, "") as String
        return if (json.isNotEmpty()) {
            gson.fromJson(json, value.javaClass)
        } else
            value
    }

    /**
     * for check user login return true for login yes
     */
    fun saveAuthToken(token: String) {
        SharedPreference.setValue(AUTH_TOKEN, token)
    }

    fun isUserLoggedIn(): Boolean {
        return getAuthToken().isNotEmpty()
    }

    fun getAuthToken(): String {
        return SharedPreference.getValue(AUTH_TOKEN, "") as String
    }

    fun logout() {
        SharedPreference.deletePreference(USER_OBJECT)
        SharedPreference.deletePreference(AUTH_TOKEN)
    }

    private fun getUserObject(): UserBaseModel {
        var userBaseModel = UserBaseModel()
        userBaseModel = getLoginData(userBaseModel) as UserBaseModel
        return userBaseModel
    }

    fun getRole(): String {
        return getUserObject().roleName
    }

    fun getRoleSlug(): String {
        return getUserObject().roleSlug
    }

    fun getEmail(): String {
        return getUserObject().email
    }

    fun getFullName(): String {
        return getUserObject().fullName
    }

    fun getFirstName(): String {
        return getUserObject().firstName
    }

    fun getLastName(): String {
        return getUserObject().lastName
    }

    fun getFirmName(): String {
        return getUserObject().firmName
    }

    fun getPinCode(): String {
        return getUserObject().pinCode
    }

    fun getContactNo1(): String {
        return getUserObject().contactNo1
    }

    fun getContactNo2(): String {
        return getUserObject().contactNo2
    }

    fun getStateId(): Int {
        return getUserObject().stateId
    }

    fun getStateName(): String {
        return getUserObject().stateName
    }

    fun getCityId(): Int {
        return getUserObject().cityId
    }

    fun getCityName(): String {
        return getUserObject().cityName
    }

    fun getDrugDoc(): String {
        return getUserObject().drugDocument
    }

    fun getDrugNo(): String {
        return getUserObject().drugLicenceNo
    }

    fun getGstDoc(): String {
        return getUserObject().gstDocument
    }

    fun getGstNo(): String {
        return getUserObject().gstNo
    }

    fun getIdDoc(): String {
        return getUserObject().idProofDocument
    }

    fun getIsNewPush(): Int {
        return getUserObject().isNewPush
    }
}