package com.example.convertorsytem.utils

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class UserBottomBackStackController {

    private val stackOrder = Stack<Int>()

    private val graphIdToTagMap = HashMap<Int , String>()

    private val selectedNavController = MutableLiveData<NavController>()

    private var currentNavHostFragmentTag = ""

    fun setupWithNavController(
        bottomNavigationView : BottomNavigationView ,
        navGraphIds : List<Int> ,
        fragmentManager : FragmentManager ,
        containerId : Int
    ) : LiveData<NavController> {

        /**
         * Create nav host fragments and add them to fragment manager initially.
         */
        createNavHostFragments(fragmentManager , containerId , navGraphIds)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            if (fragmentManager.isStateSaved) {
                false
            } else {
                val selectedNavHostFragmentTag = graphIdToTagMap[menuItem.itemId]
                if (currentNavHostFragmentTag != selectedNavHostFragmentTag) {

                    if (stackOrder.contains(menuItem.itemId).not()) {
                        stackOrder.push(menuItem.itemId)
                    } else {
                        stackOrder.remove(menuItem.itemId)
                        stackOrder.push(menuItem.itemId)
                    }

                    fragmentManager.popBackStack(
                        currentNavHostFragmentTag ,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )

                    detachNavHostFragment(
                        fragmentManager ,
                        fragmentManager.findFragmentByTag(currentNavHostFragmentTag) as NavHostFragment
                    )

                    val newNavHostFragment =
                        fragmentManager.findFragmentByTag(selectedNavHostFragmentTag) as NavHostFragment?
                    fragmentManager.beginTransaction()
                        .attach(newNavHostFragment!!)
                        .setPrimaryNavigationFragment(newNavHostFragment)
                        .addToBackStack(selectedNavHostFragmentTag)
                        .setReorderingAllowed(true)
                        .commit()

                    currentNavHostFragmentTag = selectedNavHostFragmentTag!!
                    selectedNavController.value = newNavHostFragment.navController
                    true
                } else {
                    true
                }
            }
        }

        bottomNavigationView.setupItemReselected(graphIdToTagMap , fragmentManager)

        fragmentManager.addOnBackStackChangedListener {
            if (fragmentManager.backStackEntryCount == 0) {
                stackOrder.pop()
                if (stackOrder.isEmpty().not()) {
                    val fragmentGraphIdStack = stackOrder.peek()
                    val fragmentTagInStack = graphIdToTagMap[fragmentGraphIdStack]
                    val newNavHostFragment =
                        fragmentManager.findFragmentByTag(fragmentTagInStack) as NavHostFragment?
                    fragmentManager.beginTransaction()
                        .attach(newNavHostFragment!!)
                        .setPrimaryNavigationFragment(newNavHostFragment)
                        .apply {
                            if (stackOrder.size > 1) {
                                addToBackStack(fragmentTagInStack)
                            }
                        }
                        .setReorderingAllowed(true)
                        .commit()

                    currentNavHostFragmentTag = fragmentTagInStack!!
                    bottomNavigationView.selectedItemId = fragmentGraphIdStack
                    selectedNavController.value = newNavHostFragment.navController
                }
            }
        }

        return selectedNavController
    }

    private fun BottomNavigationView.setupItemReselected(
        graphIdToTagMap : HashMap<Int , String> ,
        fragmentManager : FragmentManager
    ) {
        setOnNavigationItemReselectedListener { item ->
            val newlySelectedItemTag = graphIdToTagMap[item.itemId]
            val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                    as NavHostFragment
            val navController = selectedFragment.navController
            // Pop the back stack to the start destination of the current navController graph
            navController.popBackStack(
                navController.graph.startDestinationId , false
            )
        }
    }

    private fun createNavHostFragments(
        fragmentManager : FragmentManager ,
        containerId : Int ,
        navGraphIds : List<Int>
    ) {
        navGraphIds.forEachIndexed { index , navGraphId ->
            val fragmentTag = getFragmentTag(index)

            val navHostFragment = obtainNavHostFragment(
                fragmentManager ,
                fragmentTag ,
                navGraphId ,
                containerId
            )

            val graphId = navHostFragment.navController.graph.id
            graphIdToTagMap[graphId] = fragmentTag

            if (index == 0) {
                selectedNavController.value = navHostFragment.navController
                attachNavHostFragment(fragmentManager , navHostFragment)
                currentNavHostFragmentTag = fragmentTag
                stackOrder.push(graphId)
            } else {
                detachNavHostFragment(fragmentManager , navHostFragment)
            }
        }
    }

    private fun detachNavHostFragment(
        fragmentManager : FragmentManager ,
        navHostFragment : NavHostFragment
    ) {
        fragmentManager.beginTransaction()
            .detach(navHostFragment)
            .commitNow()
    }

    private fun attachNavHostFragment(
        fragmentManager : FragmentManager ,
        navHostFragment : NavHostFragment
    ) {
        fragmentManager.beginTransaction()
            .attach(navHostFragment)
            .setPrimaryNavigationFragment(navHostFragment)
            .commitNow()
    }

    private fun obtainNavHostFragment(
        fragmentManager : FragmentManager ,
        fragmentTag : String ,
        navGraphId : Int ,
        containerId : Int
    ) : NavHostFragment {
        // If the Nav Host fragment exists, return it
        val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
        existingFragment?.let { return it }

        // Otherwise, create it and return it.
        val navHostFragment = NavHostFragment.create(navGraphId)
        fragmentManager.beginTransaction()
            .add(containerId , navHostFragment , fragmentTag)
            .commitNow()
        return navHostFragment
    }

    private fun getFragmentTag(index : Int) = "bottomNavigation#$index"
}