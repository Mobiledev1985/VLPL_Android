package com.lr.androidfeature.utils

import androidx.annotation.AnimRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.lr.androidfeature.BaseApplication

class FragmentNav {

    companion object {
        private var instance: FragmentNav? = null

        fun getInstance(): FragmentNav? {
            if (instance == null) {
                instance = FragmentNav()
            }
            return instance
        }
    }

    private fun getTransaction(
        fragmentManager: FragmentManager,
        isAnimated: Boolean
    ): FragmentTransaction {
        val transaction = fragmentManager.beginTransaction()
        if (isAnimated)
            transaction.setCustomAnimations(
                BaseApplication.enterAnimationFragment, 0,
                0, BaseApplication.exitAnimationFragment
            )
        return transaction
    }

    /**
     * set animation in application loader class for all transaction
     * @param enterAnimation
     * @param exitAnimation
     */
    fun setAnimation(@AnimRes enterAnimation: Int, @AnimRes exitAnimation: Int) {
        BaseApplication.enterAnimationFragment = enterAnimation
        BaseApplication.exitAnimationFragment = exitAnimation
    }

    /**
     * This method use for add root fragment without back stack
     * @param containerId -> View id where you want fragment add
     * @param fragment -> your fragment
     * @param fragmentManager -> FragmentManager
     * @param isAnimated -> true/false
     */
    fun addFragment(
        containerId: Int,
        fragment: Fragment,
        fragmentManager: FragmentManager,
        isAnimated: Boolean
    ) {
        val transaction = getTransaction(fragmentManager, isAnimated)
        transaction.add(containerId, fragment, fragment.javaClass.name).commit()
    }

    /**
     * This method use for add fragment with back stack
     * @param containerId -> View id where you want fragment add
     * @param fragment -> your fragment
     * @param fragmentManager -> FragmentManager
     * * @param isAnimated -> true/false
     */
    fun addFragmentBack(
        containerId: Int,
        fragment: Fragment,
        fragmentManager: FragmentManager,
        isAnimated: Boolean
    ) {
        val transaction = getTransaction(fragmentManager, isAnimated)
        transaction.add(containerId, fragment).addToBackStack(fragment.javaClass.name).commit()
    }

    /**
     * This method use for replace fragment
     * @param containerId -> View id where you want fragment add
     * @param fragment -> your fragment
     * @param fragmentManager -> FragmentManager
     * @param isAnimated -> true/false
     */
    fun replaceFragment(
        containerId: Int,
        fragment: Fragment,
        fragmentManager: FragmentManager,
        isAnimated: Boolean
    ) {
        val transaction = getTransaction(fragmentManager, isAnimated)
        transaction.replace(containerId, fragment).commitAllowingStateLoss()
    }

    /**
     * This method use for add root fragment with remove all your previous fragments
     * @param containerId -> View id where you want fragment add
     * @param fragment -> your fragment
     * @param fragmentManager -> FragmentManager
     */
    fun addFragmentBackRoot(
        containerId: Int,
        fragment: Fragment,
        fragmentManager: FragmentManager
    ) {
        if (fragmentManager.backStackEntryCount > 0) {
            while (fragmentManager.popBackStackImmediate());
        }
        val transaction = fragmentManager.beginTransaction()
        transaction.add(containerId, fragment).commit()
    }

    /**
     * This method use for get current fragment
     * @param containerId -> View id where you want fragment add
     * @param fragmentManager -> FragmentManager
     */
    fun getCurrentFragment(containerId: Int, fragmentManager: FragmentManager): Fragment {
        val fragment = fragmentManager.findFragmentById(containerId)
        return fragment!!
    }

    /**
     * This method use for check current fragment is root fragment or not
     * @param fragmentManager -> FragmentManager
     * @return -> true/false
     */
    fun isRootFragment(fragmentManager: FragmentManager): Boolean {
        return fragmentManager.backStackEntryCount == 0
    }

    /**
     * This method use for remove fragment from fragment stack
     * @param fragmentManager -> FragmentManager
     */
    fun removeFragment(fragmentManager: FragmentManager) {
        fragmentManager.popBackStack()
    }
}