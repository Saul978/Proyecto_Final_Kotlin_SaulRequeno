package com.example.main_activity.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.main_activity.Fragments.Fragment_Ajustes
import com.example.main_activity.Fragments.Fragment_pedidos


/**
 * Created by Avin on 22/09/2018.
 */
 class Adapter_Pages(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                Fragment_pedidos()
            }
            else -> {
                return Fragment_Ajustes()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }


    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Pedidos"
            else -> {
                return "Ajustes"
            }
        }
    }
}