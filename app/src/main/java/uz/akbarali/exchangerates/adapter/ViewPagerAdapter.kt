package uz.akbarali.exchangerates.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uz.akbarali.exchangerates.fragments.AllCurrencyFragment
import uz.akbarali.exchangerates.fragments.CalculatorFragment
import uz.akbarali.exchangerates.fragments.MainFragment

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0-> MainFragment()
            1-> AllCurrencyFragment()
            2-> CalculatorFragment()
            else -> MainFragment()
        }
    }

//    override fun getPageTitle(position: Int): CharSequence? {
//        return categoryList[position].title
//    }
}