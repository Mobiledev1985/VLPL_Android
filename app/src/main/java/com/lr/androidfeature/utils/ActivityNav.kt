package com.lr.androidfeature.utils

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.annotation.AnimRes
import com.lr.androidfeature.BaseApplication

class ActivityNav {

    companion object {
        private var instance: ActivityNav? = null

        fun getInstance(): ActivityNav? {
            if (instance == null) {
                instance = ActivityNav()
            }
            return instance
        }
    }


    /**
     * set animation in application loader class for all transaction
     * @param enterAnimation
     * @param exitAnimation
     */
    fun setAnimation(@AnimRes enterAnimation: Int, @AnimRes exitAnimation: Int) {
        BaseApplication.enterAnimationActivity = enterAnimation
        BaseApplication.exitAnimationActivity = exitAnimation
    }


    /**
     * for finish activity
     * @param activity -> current activity
     */
    //kill any activity
    fun killActivity(activity: Activity?) {
        activity?.finish()
    }


    /**
     * call new activity and finish previous all activity
     * @param context -> current activity
     * @param className -> class name of which activity you want to start
     */
    fun callNewActivity(context: Context?, className: Class<*>) {
        val intent = Intent(context, className)
        val bundleAnimation = ActivityOptions.makeCustomAnimation(
            context!!.applicationContext,
            android.R.anim.fade_in, android.R.anim.fade_out
        ).toBundle()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }


    /**
     * call new activity
     * @param context -> current activity
     * @param className -> class name of which activity you want to start
     */
    fun callActivity(context: Context?, className: Class<*>) {
        val intent = Intent(context, className)
        val bundleAnimation = ActivityOptions.makeCustomAnimation(
            context!!.applicationContext,
            android.R.anim.fade_in, android.R.anim.fade_out
        ).toBundle()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }


    /**
     * call new activity with intent
     * @param context -> current activity
     * @param intent -> intent
     */
    fun callActivityIntent(context: Activity?, intent: Intent) {
        val bundleAnimation = ActivityOptions.makeCustomAnimation(
            context!!.applicationContext,
            android.R.anim.fade_in, android.R.anim.fade_out
        ).toBundle()
        context.startActivity(intent)
    }


    /**
     * call new activity with intent and finish previous all activity
     * @param context -> current activity
     * @param intent -> intent
     */
    fun callNewActivityIntent(context: Activity?, intent: Intent) {
        val bundleAnimation = ActivityOptions.makeCustomAnimation(
            context!!.applicationContext,
            android.R.anim.fade_in, android.R.anim.fade_out
        ).toBundle()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }
}