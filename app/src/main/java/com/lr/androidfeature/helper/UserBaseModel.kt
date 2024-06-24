package com.lr.androidfeature.helper

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class UserBaseModel : Serializable {
    @SerializedName("role_slug")
    var roleSlug: String = ""

    @SerializedName("role_name")
    var roleName: String = ""

    @SerializedName("full_name")
    var fullName: String = ""

    @SerializedName("firm_name")
    var firmName: String = ""

    @SerializedName("first_name")
    var firstName: String = ""

    @SerializedName("last_name")
    var lastName: String = ""

    @SerializedName("email")
    var email: String = ""

    @SerializedName("state_id")
    var stateId: Int = 0

    @SerializedName("state_name")
    var stateName: String = ""

    @SerializedName("city_id")
    var cityId: Int = 0

    @SerializedName("city_name")
    var cityName: String = ""

    @SerializedName("pincode")
    var pinCode: String = ""

    @SerializedName("contact_no_1")
    var contactNo1: String = ""

    @SerializedName("contact_no_2")
    var contactNo2: String = ""

    @SerializedName("drug_document")
    var drugDocument: String = ""

    @SerializedName("drug_licence_no")
    var drugLicenceNo: String = ""

    @SerializedName("gst_document")
    var gstDocument: String = ""

    @SerializedName("gst_no")
    var gstNo: String = ""

    @SerializedName("id_proof_document")
    var idProofDocument: String = ""

    @SerializedName("is_new_push")
    var isNewPush: Int = 0

    @SerializedName("total_points")
    var totalPoint: Int = 0
}