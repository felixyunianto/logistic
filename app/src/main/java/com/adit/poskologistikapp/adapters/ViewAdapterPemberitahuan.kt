package com.adit.poskologistikapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.adit.poskologistikapp.fragments.BencanaPemberitahuanFragment
import com.adit.poskologistikapp.fragments.KebutuhanPemberitahuanFragment
import com.adit.poskologistikapp.fragments.KeluarPenerimaanFragment
import com.adit.poskologistikapp.fragments.PenerimaanFragment

class ViewAdapterPemberitahuan(supportFragmentManager: FragmentManager) :
    FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                BencanaPemberitahuanFragment()
            }else -> {
                KebutuhanPemberitahuanFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Bencana"
            else -> "Kebutuhan Logistik"
        }
    }
}