package com.lr.androidfeature.customclasses

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.lr.androidfeature.R

class CustomVerticalMenuItem : LinearLayout {

    private lateinit var tv: AppCompatTextView
    private lateinit var ivMenuIcon: AppCompatImageView
    private lateinit var ivNextIcon: AppCompatImageView
    private lateinit var cv: CardView

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
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.custom_vertical_menu_item, this, true)
        tv = view.findViewById(R.id.tv)
        ivMenuIcon = view.findViewById(R.id.ivMenuIcon)
        ivNextIcon = view.findViewById(R.id.ivNextIcon)
        cv = view.findViewById(R.id.cv)

        attrs.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomMenuItem, 0, 0)
            val title = typedArray.getString(R.styleable.CustomMenuItem_menuTitle)
            val menuIcon = typedArray.getResourceId(R.styleable.CustomMenuItem_menuIcon, 0)
            val cardBackgroundColor =
                typedArray.getColor(
                    R.styleable.CustomMenuItem_cardBackgroundColor,
                    ContextCompat.getColor(context, R.color.colorWhite)
                )
            val isShowNextIcon =
                typedArray.getBoolean(R.styleable.CustomMenuItem_isShowNextIcon, true)

            tv.text = title        //for set Text
            ivMenuIcon.setImageResource(menuIcon)  //for Set Image/Icon
            cv.setCardBackgroundColor(cardBackgroundColor) //For set Card bg color
            cv.cardElevation = 0f
            //for Visible and Invisible Icon
            if (!isShowNextIcon) {
                ivNextIcon.visibility = View.GONE
            } else {
                ivNextIcon.visibility = View.VISIBLE
            }
            typedArray.recycle()
        }
    }
}