package com.example.main_activity.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.main_activity.Adapter.Adapter_Pages

import com.example.main_activity.R
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Fragment_user : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var  tabs: TabLayout
    companion object{
        fun newInstance(): Fragment_user=Fragment_user()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_user, container, false)
        viewPager = view.findViewById(R.id.view_Pager)

        tabs = view.findViewById(R.id.tab_layout)
        val fragmentAdapter = Adapter_Pages(childFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabs.setupWithViewPager(viewPager)
        return view
    }


}
