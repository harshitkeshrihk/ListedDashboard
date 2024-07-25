package com.hk.listeddashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hk.listeddashboard.UI.Constants
import com.hk.listeddashboard.UI.CoursesFragment
import com.hk.listeddashboard.UI.LinksFragment
import com.hk.listeddashboard.UI.MediaFragment
import com.hk.listeddashboard.UI.ProfileFragment
import com.hk.listeddashboard.api.SessionManager
import com.hk.listeddashboard.databinding.ActivityMainBinding
import com.hk.listeddashboard.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var sessionManager: SessionManager

    private val linksFragment = LinksFragment()
    private val coursesFragment = CoursesFragment()
    private val mediaFragment = MediaFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager.saveAuthToken(Constants.TOKEN)

        replaceFragment(linksFragment)
        setUpButton()
        showToast()

    }

    private fun setUpButton() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    replaceFragment(LinksFragment())
                    true
                }
                R.id.menu_courses -> {
                    replaceFragment(coursesFragment)
                    true
                }
                R.id.menu_campaigns -> {
                    replaceFragment(mediaFragment)
                    true
                }
                R.id.menu_profile -> {
                    replaceFragment(profileFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun showToast() {
        binding.fab.setOnClickListener{
            Toast.makeText(this,"Work In Progress",Toast.LENGTH_SHORT).show()
        }
    }

    private fun replaceFragment(fragment: Fragment){
            if(fragment != null){
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.contentFrameLayout,fragment)
                transaction.commit()
            }
    }
}
