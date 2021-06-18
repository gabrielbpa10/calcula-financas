package br.com.calculafinancas.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.calculafinancas.fragment.PrimeiroFragment
import br.com.calculafinancas.fragment.SegundoFragment

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        when(position){
            1 -> {
                return SegundoFragment()
            }
        }

        return PrimeiroFragment()
    }
}