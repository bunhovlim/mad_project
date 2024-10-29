package kh.edu.rupp.ite.mad_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kh.edu.rupp.ite.mad_project.fragment.OrderFragment
import kh.edu.rupp.ite.mad_project.fragment.FavoriteFragment
import kh.edu.rupp.ite.mad_project.fragment.HomeFragment
import kh.edu.rupp.ite.mad_project.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set HomeFragment as default
        loadFragment(HomeFragment())

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_home -> fragment = HomeFragment()
                R.id.nav_favorite -> fragment = FavoriteFragment()
                R.id.nav_order -> fragment = OrderFragment()
                R.id.nav_profile -> fragment = ProfileFragment()
            }
            loadFragment(fragment)
            true
        }
    }

    private fun loadFragment(fragment: Fragment?) {
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }
}
