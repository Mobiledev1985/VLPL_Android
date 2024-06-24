package com.lr.androidfeature.viewpagernav

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(
    mContext: Context,
    fragmentManager: FragmentManager,
    arrayList: ArrayList<ViewPagerModel>
) :
    FragmentPagerAdapter(fragmentManager) {

    private var arrayList: ArrayList<ViewPagerModel>? = arrayList
    var mContext: Context? = mContext

    override fun getPageTitle(position: Int): CharSequence? {
        return arrayList?.get(position)?.title
    }

    override fun getItem(position: Int): Fragment {
        return arrayList?.get(position)!!.fragment
    }

    override fun getCount(): Int {
        return arrayList!!.size
    }
}