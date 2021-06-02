package com.bangkit.whatdish.ui.intro

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.whatdish.ui.main.MainActivity
import com.bangkit.whatdish.R
import com.bangkit.whatdish.databinding.ActivityIntroBinding
import com.bangkit.whatdish.ui.intro.fragment.Intro1Fragment
import com.bangkit.whatdish.ui.intro.fragment.Intro2Fragment
import com.bangkit.whatdish.ui.intro.fragment.Intro3Fragment
import com.bangkit.whatdish.ui.intro.fragment.Intro4Fragment


class IntroActivity : AppCompatActivity() {
    private val fragmentList = ArrayList<Fragment>()
    private lateinit var introBinding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // making the status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        // to check if its openened before or not
        if (restorePrefData()) {
            val mainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }


        introBinding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(introBinding.root)

        val adapter = IntroSliderAdapter(this)
        introBinding.introSlider.adapter = adapter
        fragmentList.addAll(listOf(
            Intro1Fragment(), Intro2Fragment(), Intro3Fragment(), Intro4Fragment()
        ))
        adapter.setFragmentList(fragmentList)
        introBinding.indicatorLayout.setIndicatorCount(adapter.itemCount)
        introBinding.indicatorLayout.selectCurrentPosition(0)
        introPageListeners()
    }

    private fun introPageListeners() {
        introBinding.introSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                introBinding.indicatorLayout.selectCurrentPosition(position)
                if (position < fragmentList.lastIndex) {
                    introBinding.tvSkip.visibility = View.VISIBLE
                    introBinding.tvNext.text = getString(R.string.next)
                } else {
                    introBinding.tvSkip.visibility = View.GONE
                    introBinding.tvNext.text = getString(R.string.get_started)
                }
            }
        })
        introBinding.tvSkip.setOnClickListener {
            introBinding.introSlider.currentItem = fragmentList.lastIndex
        }
        introBinding.tvNext.setOnClickListener {
            val position = introBinding.introSlider.currentItem
            if (position < fragmentList.lastIndex) {
                introBinding.introSlider.currentItem = position + 1
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                savePrefsData()
                finish()
            }
        }
    }

    private fun restorePrefData(): Boolean {
        val pref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        return pref.getBoolean("isIntroOpnend", false)
    }

    private fun savePrefsData() {
        val pref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isIntroOpnend", true)
        editor.commit()
    }
}