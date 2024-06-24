package com.lr.androidfeature.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.lr.androidfeature.R
import com.lr.androidfeature.databinding.ActivityWelcomeBinding
import com.lr.androidfeature.utils.Utility

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewBinding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.clickListener = this
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btnLogin -> {
                val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
                val activityOptionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@WelcomeActivity,
                        viewBinding.ivlogo,
                        ViewCompat.getTransitionName(viewBinding.ivlogo)!!
                    )
                startActivity(intent, activityOptionsCompat.toBundle())
            }
            R.id.tvEmail -> {
                Utility.getInstance().openEmail(this@WelcomeActivity)
            }
            R.id.tvCall -> {
                Utility.getInstance().openDial(this@WelcomeActivity)
            }
        }
    }
}