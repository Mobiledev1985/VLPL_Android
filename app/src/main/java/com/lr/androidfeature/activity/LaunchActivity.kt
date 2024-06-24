package com.lr.androidfeature.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.lr.androidfeature.R
import com.lr.androidfeature.helper.LoginHelper
import com.lr.androidfeature.utils.ActivityNav
class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        Handler(Looper.getMainLooper()).postDelayed({
            if (LoginHelper.getInstance()!!.isUserLoggedIn()) {
                //call home Activity/fragment like else part
            } else {
                ActivityNav.getInstance()?.callActivity(this, WelcomeActivity::class.java)
            }
            ActivityNav.getInstance()?.killActivity(this)
        }, 3000)
    }
}