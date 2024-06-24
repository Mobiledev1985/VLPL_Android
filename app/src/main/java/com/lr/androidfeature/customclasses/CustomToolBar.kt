package com.lr.androidfeature.customclasses

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.lr.androidfeature.R

class CustomToolBar : LinearLayout {

    lateinit var tvTitle: AppCompatTextView
    lateinit var tvEndIcon: AppCompatTextView
    lateinit var ivBackIcon: AppCompatImageView
    private var backClickListener: OnClickListener? = null

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_UP) {
            backClickListener?.onClick(this)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_UP &&
            (event.keyCode == KeyEvent.KEYCODE_DPAD_CENTER || event.keyCode == KeyEvent.KEYCODE_ENTER)
        ) {
            backClickListener?.onClick(this)
        }
        return super.dispatchKeyEvent(event)
    }

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
        val view: View = LayoutInflater.from(context).inflate(R.layout.custom_toolbar, this, true)
        tvTitle = view.findViewById(R.id.tvTitle)
        tvEndIcon = view.findViewById(R.id.tvEditICon)
        ivBackIcon = view.findViewById(R.id.ivBackArrow)

        attrs.let {
            val typeArray = context.obtainStyledAttributes(it, R.styleable.CustomToolBar, 0, 0)
            val title = typeArray.getString(R.styleable.CustomToolBar_title)
            val isShowBackArrowIcon =
                typeArray.getBoolean(R.styleable.CustomToolBar_isShowBackIcon, true)
            val isShowEditIcon =
                typeArray.getBoolean(R.styleable.CustomToolBar_isShowEndIcon, false)

            //set text in textview(title)
            tvTitle.text = title
            tvTitle.setLines(1)

            //for visible and invisible Back icon/Button in UI
            ivBackIcon.visibility = if (!isShowBackArrowIcon) {
                View.GONE
            } else {
                View.VISIBLE
            }

            //for visible and invisible Edit icon in UI
            tvEndIcon.visibility = if (isShowEditIcon) {
                View.VISIBLE
            } else {
                View.GONE
            }
            typeArray.recycle()
        }
    }
}