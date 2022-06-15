package com.example.convertorsytem.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.convertorsytem.R
import com.example.convertorsytem.databinding.ActivityMainBinding
import com.example.convertorsytem.utils.UserBottomBackStackController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

class MainActivity:AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private lateinit var appBarConfiguration : AppBarConfiguration
    private var currentNavController : LiveData<NavController>? = null

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate( savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        if (savedInstanceState == null) {
            setUpBottomNavigationBar()
        }

        binding.apply {
            val materialShapeDrawable = appbarLayout.background as MaterialShapeDrawable
            materialShapeDrawable.shapeAppearanceModel =
                materialShapeDrawable.shapeAppearanceModel
                    .toBuilder()
                    .setBottomRightCorner(CornerFamily.ROUNDED , 30F)
                    .setBottomLeftCorner(CornerFamily.ROUNDED , 30F)
                    .build()
        }

    }

    override fun onRestoreInstanceState(savedInstanceState : Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpBottomNavigationBar()
    }


    private fun setUpBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(1).isEnabled = false

        val bottomAppBarBackground : MaterialShapeDrawable =
            bottomAppBar.background as MaterialShapeDrawable

        bottomAppBarBackground.shapeAppearanceModel =
            bottomAppBarBackground.shapeAppearanceModel
                .toBuilder()
                .setTopRightCornerSize(30F)
                .setTopLeftCornerSize(30F)
                .build()

        binding.fab.setOnClickListener {
            bottomNavigationView.menu.getItem(1).isEnabled = true
            bottomNavigationView.selectedItemId = R.id.currency_nav
        }

        val navGraphsId = listOf(
            R.navigation.home_nav_graph ,
            R.navigation.currency_nav_graph ,
            R.navigation.questions_nav_graph
        )

        val bottomNavController = UserBottomBackStackController()
        val controller = bottomNavController.setupWithNavController(
            bottomNavigationView ,
            navGraphIds = navGraphsId ,
            supportFragmentManager ,
            R.id.main_nav_host_fragment
        )

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homePage ,
                R.id.currencyPage ,
                R.id.questionsPage
            )
        )

        controller.observe(this , Observer { navController ->
            setupActionBarWithNavController(navController , appBarConfiguration)
        })

        currentNavController = controller
    }

    override fun onSupportNavigateUp() : Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

}