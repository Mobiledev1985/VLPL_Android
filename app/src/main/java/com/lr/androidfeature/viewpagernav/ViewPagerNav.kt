package com.lr.androidfeature.viewpagernav

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class ViewPagerNav {
    companion object {
        private var instance: ViewPagerNav? = null

        fun getInstance(): ViewPagerNav? {
            if (instance == null) {
                instance = ViewPagerNav()
            }
            return instance
        }
    }

    /**
     * set view pager
     * @param context -> Activity
     * @param fragmentManager -> fragment manager
     * @param arrayList -> arraylist of fragments
     * @param viewPager -> view pager object
     * @param tabLayout -> its optional if you want set view pager with tablayout then add object other wise null
     * @param pageLimit -> its optional if you want set offscreenPageLimit then add other wise null
     */
    fun setViewPager(
        context: Context,
        fragmentManager: FragmentManager,
        arrayList: ArrayList<ViewPagerModel>,
        viewPager: ViewPager,
        tabLayout: TabLayout?,
        pageLimit: Int?
    ) {
        val adapter = ViewPagerAdapter(context, fragmentManager, arrayList)
        if (pageLimit != null)
            viewPager.offscreenPageLimit = pageLimit
        viewPager.adapter = adapter
        tabLayout?.setupWithViewPager(viewPager)
    }

    /**
     * This method use for get fragment from view pager
     * @param viewPagerId -> ViewPager id
     * @param fragmentManager -> FragmentManager
     * @param index -> Fragment index which you want get
     */
    fun getFragmentViewPager(
        viewPagerId: Int,
        fragmentManager: FragmentManager,
        index: Int
    ): Fragment {
        return fragmentManager.findFragmentByTag(
            "android:switcher:" + viewPagerId + ":"
                    + index
        )!!
    }
}