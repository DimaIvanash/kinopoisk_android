package com.example.cinema.presentation.onboarding

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    companion object{
        const val ONBOARDING = "onboarding"
        const val FINISHED = "finished"
    }
    private var _binding : FragmentSplashBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            if (getOnBoardStatus()){
                findNavController().navigate(R.id.action_splashFragment_to_homePageFragment)
            }else{
                findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)
            }
        }, 500L)
    }
    private fun getOnBoardStatus() : Boolean{
        val sharedPreference = requireActivity().getSharedPreferences(ONBOARDING, Context.MODE_PRIVATE)
        return sharedPreference.getBoolean(FINISHED, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}