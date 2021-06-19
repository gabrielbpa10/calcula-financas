package br.com.calculafinancas.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import br.com.calculafinancas.R
import br.com.calculafinancas.adapter.FragmentAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class PrincipalActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var pager2: ViewPager2
    private lateinit var adapter: FragmentAdapter
    private lateinit var botaoAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        tabLayout = findViewById(R.id.activity_principal_tabLayout)
        pager2 = findViewById(R.id.activity_principal_view_pager2)
        botaoAdd = findViewById(R.id.activity_principal_floatingActionButton)

        var fm = supportFragmentManager
        adapter = FragmentAdapter(fm,lifecycle)
        pager2.setAdapter(adapter)

        tabLayout.addTab(tabLayout.newTab().setText("Receitas"))
        tabLayout.addTab(tabLayout.newTab().setText("Despesas"))

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab) {
                pager2.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        pager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        botaoAdd.setOnClickListener{
            var intentFormulario = Intent(this,FormularioActivity::class.java)
            startActivity(intentFormulario)
        }

    }
}