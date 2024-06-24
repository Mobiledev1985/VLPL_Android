package com.lr.androidfeature.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.lr.androidfeature.R
import com.lr.androidfeature.helper.LoginHelper

fun TextView.setPriceLableAccordingUserType() {
    text = if (LoginHelper.getInstance()?.getRole().equals("Stockiest")) {
        context.getString(R.string.str_your_price)
    } else {
        context.getString(R.string.str_your_price)
    }
}

fun TextView.setPriceLableInCartAccordingUserType() {
    text = if (LoginHelper.getInstance()?.getRole().equals("Stockiest")) {
        context.getString(R.string.str_your_amount)
    } else {
        context.getString(R.string.str_your_amount)
    }
}

/**
 * @param isShowBottomView it's a Boolean value
 * @method Hide and Show Bottom Navigation View
 */
fun View.hideShowBottomNavigationView(isShowBottomView: Boolean) {
    this.visibility = if (isShowBottomView) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun AppCompatButton.setVisibilityForMR() {
    visibility = if (LoginHelper.getInstance()?.getRole().equals("MR")) {
        View.INVISIBLE
    } else {
        View.VISIBLE
    }
}

fun LinearLayout.setStockiestViewVisibility() {
    visibility = if (LoginHelper.getInstance()?.getRole().equals("Stockiest")) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

fun View?.backIconHideShow(isShowBackIcon : Boolean) {
    this?.visibility = if (isShowBackIcon) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View?.showView() {
    this?.visibility = View.VISIBLE
}

fun View?.hideView() {
    this?.visibility = View.GONE
}

fun View?.makeViewInvisible() {
    this?.visibility = View.INVISIBLE
}