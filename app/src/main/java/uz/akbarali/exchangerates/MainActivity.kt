package uz.akbarali.exchangerates

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.tab_layout_main.view.*
import uz.akbarali.exchangerates.adapter.ViewPagerAdapter
import uz.akbarali.exchangerates.database.AppDatabase
import uz.akbarali.exchangerates.databinding.ActivityMainBinding
import uz.akbarali.exchangerates.models.Currency
import uz.akbarali.exchangerates.utils.MyViewModels


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    lateinit var appDatabase: AppDatabase
    lateinit var listCurrency: ArrayList<Currency>

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var myViewModel: MyViewModels

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BindingObject.activityBinding = ActivityMainBinding.inflate(layoutInflater)
        binding = BindingObject.activityBinding!!
        setContentView(binding.root)

        binding.appBarMain.menuImage.setOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.START)
        }

        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_share -> {
                    try {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "https://t.me/AkbaraliKhasanov")
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(shareIntent)
                    } catch (e: Exception) {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.menu_app_info -> {
                    val telegram = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://t.me/AkbaraliKhasanov")
                    )
                    startActivity(telegram)
                }
            }
            binding.drawerLayout.close()
            true
        }

        loadCurrency()

        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        binding.appBarMain.viewPagerBasic.adapter = viewPagerAdapter
        binding.appBarMain.tabLayoutMain.setupWithViewPager(binding.appBarMain.viewPagerBasic)
        setTabs()

        binding.appBarMain.tabLayoutMain.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                when (tab?.position) {
                    0 -> customView?.image_tab_item_main?.setImageResource(R.drawable.ic_home_selected)
                    1 -> {
                        customView?.image_tab_item_main?.setImageResource(R.drawable.ic_all_currency_selected)
                        binding.appBarMain.searchBasic.visibility = View.VISIBLE
                    }
                    2 -> customView?.image_tab_item_main?.setImageResource(R.drawable.ic_calculator_selected)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                when (tab?.position) {
                    0 -> customView?.image_tab_item_main?.setImageResource(R.drawable.ic_home_unselected)
                    1 -> {
                        customView?.image_tab_item_main?.setImageResource(R.drawable.ic_all_currency_unselected)
                        binding.appBarMain.searchBasic.visibility = View.GONE
                    }
                    2 -> customView?.image_tab_item_main?.setImageResource(R.drawable.ic_calculator_unselected)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun loadCurrency() {
        myViewModel = ViewModelProvider(this@MainActivity).get(MyViewModels::class.java)
        myViewModel.getUsers().observe(this@MainActivity, Observer {
            appDatabase = AppDatabase.getInstance(this)
            listCurrency = ArrayList()
            listCurrency.addAll(appDatabase.currencyDao().getAllCurrencyModel())

            if (listCurrency.isNotEmpty()) {
                if (listCurrency.last().date != it.last().date) {
                    for (currency in it) {
                        appDatabase.currencyDao().addCurrencyModel(currency)

                    }
                }
            } else {
                for (currency in it) {
                    appDatabase.currencyDao().addCurrencyModel(currency)

                }
            }
        })

    }

    private fun setTabs() {
        val tabCount = tab_layout_main.tabCount

        for (i in 0 until tabCount) {
            val tabView = LayoutInflater.from(this).inflate(R.layout.tab_layout_main, null, false)
            val tab = tab_layout_main.getTabAt(i)
            tab?.customView = tabView

            when (i) {
                0 -> tabView.image_tab_item_main.setImageResource(R.drawable.ic_home_selected)
                1 -> {
                    tabView.image_tab_item_main.setImageResource(R.drawable.ic_all_currency_unselected)
                    binding.appBarMain.searchBasic.visibility = View.GONE
                }
                2 -> tabView.image_tab_item_main.setImageResource(R.drawable.ic_calculator_unselected)
            }
        }
    }
}

object BindingObject {
    var activityBinding: ActivityMainBinding? = null
}