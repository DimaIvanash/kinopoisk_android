package com.example.cinema.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cinema.R
import com.example.cinema.presentation.HomePages.homePage.HomePageFragment
import com.example.cinema.presentation.profile.ProfileFragment
import com.example.cinema.presentation.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


import dagger.hilt.android.AndroidEntryPoint


@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity(),BottomNavigationView.OnNavigationItemSelectedListener {
    @Suppress("DEPRECATION")
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener ( this )
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homePageFragment -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, HomePageFragment())
                    .commit()
            }
            R.id.searchFragment -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, SearchFragment())
                    .commit()
            }
            R.id.profileFragment -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, ProfileFragment())
                    .commit()
            }
        }
        return true
    }
}


