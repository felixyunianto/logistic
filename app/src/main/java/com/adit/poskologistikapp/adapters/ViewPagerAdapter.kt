package com.adit.poskologistikapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.adit.poskologistikapp.fragments.KeluarPenerimaanFragment
import com.adit.poskologistikapp.fragments.PenerimaanFragment

class ViewPagerAdapter(supportFragmentManager: FragmentManager) :
    FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
{
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                KeluarPenerimaanFragment()
            }else -> {
                PenerimaanFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Konfirmasi"
            else -> "Penerimaan"
        }
    }
}