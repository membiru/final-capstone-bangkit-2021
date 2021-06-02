package com.bangkit.whatdish.ui.intro.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.whatdish.databinding.FragmentIntro2Binding

class Intro2Fragment: Fragment() {
    private lateinit var intro2Binding: FragmentIntro2Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        intro2Binding = FragmentIntro2Binding.inflate(layoutInflater, container, false)
        return intro2Binding.root
    }
}