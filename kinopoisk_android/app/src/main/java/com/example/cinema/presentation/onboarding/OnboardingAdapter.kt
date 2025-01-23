package com.example.cinema.presentation.onboarding

import android.annotation.SuppressLint

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.example.cinema.R

import com.example.cinema.databinding.FragmentOnboardingBinding

class OnboardingAdapter(list:MutableList<Fragment>, fm: FragmentManager, lifecycle: Lifecycle):
    FragmentStateAdapter(fm,lifecycle) {
    private val fragmentList = list;
    override fun getItemCount(): Int {

        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}