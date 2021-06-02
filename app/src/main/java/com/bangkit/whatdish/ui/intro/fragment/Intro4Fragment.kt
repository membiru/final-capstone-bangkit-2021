package com.bangkit.whatdish.ui.intro.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.whatdish.databinding.FragmentIntro4Binding

class Intro4Fragment: Fragment() {
    private lateinit var intro4Binding: FragmentIntro4Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        intro4Binding = FragmentIntro4Binding.inflate(layoutInflater, container, false)
        return intro4Binding.root
    }
}