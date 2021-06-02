package com.bangkit.whatdish.ui.intro.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.whatdish.databinding.FragmentIntro3Binding

class Intro3Fragment: Fragment() {
    private lateinit var intro3Binding: FragmentIntro3Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        intro3Binding = FragmentIntro3Binding.inflate(layoutInflater, container, false)
        return intro3Binding.root
    }
}