package com.zac4j.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zac4j.sample.R.id
import com.zac4j.sample.R.layout

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

    setupNavigation()
  }

  private fun setupNavigation() {
    val bottomNavigator = findViewById<BottomNavigationView>(id.bottom_navigator)
    val navHostFragment =
      supportFragmentManager.findFragmentById(id.nav_host_container) as NavHostFragment
    NavigationUI.setupWithNavController(
        bottomNavigator, navHostFragment.navController
    )
  }
}