package com.bangkit.whatdish.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.bangkit.whatdish.databinding.ActivitySplashScreenBinding
import com.bangkit.whatdish.ui.intro.IntroActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(splashScreenBinding.root)
        Handler().postDelayed({
            startActivity(Intent(this@SplashScreenActivity, IntroActivity::class.java))
            finish()
        }, 2000)
    }
}