package com.example.cinema.presentation.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cinema.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {
    private var _binding : FragmentOnboardingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listFragment = mutableListOf(
            FirstFragment(),
            SecondFragment(),
            ThirdFragment()
        )
        val onBoardingAdapter = OnboardingAdapter(listFragment, requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager2.adapter = onBoardingAdapter

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}