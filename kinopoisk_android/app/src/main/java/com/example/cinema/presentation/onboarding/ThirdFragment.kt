package com.example.cinema.presentation.onboarding
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentOnboardingThreeBinding
import com.example.cinema.presentation.onboarding.SplashFragment.Companion.FINISHED
import com.example.cinema.presentation.onboarding.SplashFragment.Companion.ONBOARDING

class ThirdFragment : Fragment() {
    private var _binding : FragmentOnboardingThreeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingThreeBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.container.setOnClickListener {
            addPref()
            findNavController().navigate(R.id.action_thirdFragment_to_homePageFragment)
        }
    }
    private fun addPref()
    {
        val sharedPreferences = requireContext().getSharedPreferences(ONBOARDING, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {  putBoolean(FINISHED,true)}.apply()
    }

}