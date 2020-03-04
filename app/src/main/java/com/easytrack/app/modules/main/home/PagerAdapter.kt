package com.easytrack.app.modules.main.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.easytrack.app.modules.main.management.view.ManagementFragment
import com.easytrack.app.modules.main.reports.view.ReportsFragment
import com.easytrack.app.modules.main.settings.view.SettingsFragment
import com.easytrack.app.modules.main.tasks.view.TaskListFragment

class PagerAdapter(fm: FragmentManager, internal val totalTabs: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TaskListFragment()
            1 -> ReportsFragment()
            2 -> ManagementFragment()
            3 -> SettingsFragment()

            else -> TaskListFragment()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}