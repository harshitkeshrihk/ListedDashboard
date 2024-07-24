package com.hk.listeddashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.activity.viewModels
import com.hk.listeddashboard.UI.CoursesFragment
import com.hk.listeddashboard.UI.LinksFragment
import com.hk.listeddashboard.UI.MediaFragment
import com.hk.listeddashboard.UI.ProfileFragment
import com.hk.listeddashboard.api.SessionManager
import com.hk.listeddashboard.databinding.ActivityMainBinding
import com.hk.listeddashboard.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var sessionManager: SessionManager

    private val linksFragment = LinksFragment()
    private val coursesFragment = CoursesFragment()
    private val mediaFragment = MediaFragment()
    private val profileFragment = ProfileFragment()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        sessionManager = SessionManager()
        sessionManager.saveAuthToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI")

        replaceFragment(linksFragment)
        setUpButton()


//        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
//            val fragment = fragments[menuItem.itemId]
//            if (fragment != null) {
//                replaceFragment(fragment)
//                true
//            } else {
//                false
//            }
//        }

//        binding.bottomNavigationView.selectedItemId = R.id.menu_home

//        val navController = findNavController(R.id.fragmentContainerView)
//        binding.bottomNavigationView.setupWithNavController(navController)

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
                    // Handle Notifications menu item click
                    true
                }
                R.id.menu_profile -> {
                    replaceFragment(profileFragment)
                    // Handle Profile menu item click
                    true
                }
                else -> false
            }
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
