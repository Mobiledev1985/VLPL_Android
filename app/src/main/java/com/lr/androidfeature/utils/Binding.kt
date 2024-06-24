package com.lr.androidfeature.utils

import android.annotation.SuppressLint
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.View
import android.view.View.*
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.lr.androidfeature.R
import java.text.NumberFormat
import java.util.*

// for Price with "RS." placeHolder
@BindingAdapter("setPrice")
fun AppCompatTextView.setPrice(price: String?) {
    if (price != null) {
        text = context.getString(R.string.str_rs).plus(price)
    }
}

//// for Price with "RS." placeHolder
@BindingAdapter("setCutPrice")
fun AppCompatTextView.setCutPrice(price: String?) {
    if (price != null) {
        text = context.getString(R.string.str_rs).plus(price)
    }
    paintFlags = STRIKE_THRU_TEXT_FLAG
}

// for Price with "RS." placeHolder
@BindingAdapter("subTotalPrice", "points")
fun AppCompatTextView.setPriceMinusPoint(price: String?, points: String?) {
    if (price != null) {
        val f: NumberFormat = NumberFormat.getInstance()
        val myNumber = f.parse(price.toString())!!.toDouble()
//        text = context.getString(R.string.str_rs).plus("%.2f".format(myNumber.minus(points!!.toInt())))
        text = context.getString(R.string.str_rs).plus(price)
    }
}

// for Price with "MRP RS." placeHolder and Strike text
@BindingAdapter("setMRPPrice")
fun AppCompatTextView.setMRPPrice(price: Any?) {
    text = buildString {
        append("Rs. ")
        append(price)
    }
//    paintFlags = android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
}

@BindingAdapter("discountType", "discount")
fun AppCompatTextView.setDiscount(discountType: Int?, discount: String? = "") {
    text = if (discountType == 1) {
        "You will get Rs. $discount OFF on this product"
    } else {
        "You will get $discount% OFF on this product "
    }
}

// for Offer Percentage with "MRP RS." placeHolder and Strike text
@BindingAdapter("setOfferPercentage")
fun AppCompatTextView.setOfferPercentage(price: Any?) {
    text = buildString {
        append(price)
        append(" OFF")
    }
}

@BindingAdapter("setImage")
fun AppCompatImageView.setImage(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(this).load(url)
            .placeholder(R.drawable.ic_placeholder).into(this)
    } else {
        setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_placeholder,
                null
            )
        )
    }
}

@BindingAdapter("setHomeBannerImage")
fun AppCompatImageView.setHomeBannerImage(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(this).load(url).into(this)
    }
}

@BindingAdapter("setRadiusImage", "imageRadius")
fun AppCompatImageView.setCropImage(url: String?, radius: Int?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(this).asBitmap().load(url).centerCrop()
            .transform(RoundedCorners(radius!!))
            .placeholder(R.drawable.ic_placeholder).into(this)
    } else {
        setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_placeholder,
                null
            )
        )
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setRating")
fun AppCompatTextView.setRating(rating: String?) {
    this.text = "($rating Ratings)"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setReviews")
fun AppCompatTextView.setReviews(review: String?) {
    text = if (review.equals("1")) {
        "($review Review)"
    } else {
        "($review Reviews)"
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setRateFromTotalRate")
fun AppCompatTextView.setRateFromTotalRate(rating: Float?) {
    this.text = "${rating.toString()} / 5"
}

//for change image on click
@BindingAdapter("selectAddress")
fun AppCompatImageView.selectAddress(selected: Int) {
    if (selected == 1) {
        setImageDrawable(
            ResourcesCompat.getDrawable(resources, R.drawable.ic_radio_selected_blue, null)
        )
    } else {
        setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_radio_unselect, null))

    }
}

//for any View Visibility
@BindingAdapter("android:visibility")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) VISIBLE else GONE
}

//for any View Visibility
@BindingAdapter("setVisibilityForProducts")
fun View.setVisibilityForProducts(visible: Boolean) {
    visibility = if (visible) VISIBLE else INVISIBLE
}

//for Editable or not
@BindingAdapter("isEditEnable")
fun isEditEnable(view: View, enable: Boolean) {
    view.isEnabled = enable
}

@BindingAdapter("isCartQTYAvailable")
fun TextView.cartQTY(qty: Int): String {
    return if (qty > 0) "@string/str_added" else "@string/str_add_to_cart"
}

@BindingAdapter("setCartItemCount")
fun AppCompatTextView.showCartItemQTY(qtySize: Int) {
    text = if (qtySize > 1) {
        "$qtySize items in cart"
    } else {
        "$qtySize item in cart"
    }
}

@BindingAdapter("setStripts")
fun AppCompatTextView.setStripts(product_strips: String) {
    text = if (product_strips == "") {
        ""
    } else {
        " ( $product_strips ) "
    }
}

@BindingAdapter("offerLableVisibilityAccordingDiscountType")
fun AppCompatTextView.offerLableVisibilityAccordingDiscountType(discountType: Int) {
    visibility = if (discountType == 0) {
        INVISIBLE
    } else {
        VISIBLE
    }
}

@BindingAdapter("setOfferLabel", "discountValue", "productBillQTY", "productFreeQTY")
fun AppCompatTextView.setOfferLabel(
    discountType: Int?,
    discountValue: String?,
    productBillQTY: Int?,
    productFreeQTY: Int?
) {
    text = when (discountType) {
        1 -> {
            "Flat $discountValue₹ OFF "
        }
        2 -> {
            "$discountValue% OFF"
        }
        else -> {
            "Buy $productBillQTY Get $productFreeQTY Free"
        }
    }
}

//total free QTY and total QTY label hide in order detail screen
@BindingAdapter("setVisibilityInOrderDetail")
fun AppCompatTextView.setVisibilityInOrderDetail(qty: Int?) {
    visibility = if (qty == 0) {
        GONE
    } else {
        VISIBLE
    }
}

@BindingAdapter("setProductTypeNameLabel")
fun AppCompatTextView.setProductTypeNameLabel(productType: String) {
    text = "(Per $productType)"
}

@BindingAdapter("setProductStript")
fun AppCompatTextView.setProductStript(productType: String) {
    text = "$productType"
}

@BindingAdapter("setGSTLlable")
fun AppCompatTextView.setGSTLlable(productGST: Int) {
    text = " +GST ($productGST%)"
}

//// for Price with "RS." placeHolder
@BindingAdapter("setCutPriceWithoutDiscount", "discountType")
fun AppCompatTextView.setCutPriceWithoutDiscount(price: String?, discountType: Int?) {
    if (price != null) {
        val f: NumberFormat = NumberFormat.getInstance()
        if (discountType == 1 || discountType == 2) {
            text = context.getString(R.string.str_rs).plus(price)
        } else {
            visibility = GONE
        }
    }
    paintFlags = STRIKE_THRU_TEXT_FLAG
}