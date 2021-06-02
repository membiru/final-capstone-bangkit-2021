package com.bangkit.whatdish.ui.intro.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.whatdish.databinding.FragmentIntro1Binding

class Intro1Fragment: Fragment() {
    private lateinit var intro1Binding: FragmentIntro1Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        intro1Binding = FragmentIntro1Binding.inflate(layoutInflater, container, false)
        return intro1Binding.root
    }
}