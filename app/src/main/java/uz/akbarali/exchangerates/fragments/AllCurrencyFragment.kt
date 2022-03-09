package uz.akbarali.exchangerates.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import uz.akbarali.exchangerates.BindingObject
import uz.akbarali.exchangerates.R
import uz.akbarali.exchangerates.adapter.AllCurrencyAdapter
import uz.akbarali.exchangerates.adapter.SpinnerAdapter
import uz.akbarali.exchangerates.database.AppDatabase
import uz.akbarali.exchangerates.databinding.FragmentAllValyutaBinding
import uz.akbarali.exchangerates.databinding.FragmentCalculatorBinding
import uz.akbarali.exchangerates.models.Currency
import uz.akbarali.exchangerates.utils.MyViewModels

import java.lang.Exception

class AllCurrencyFragment : Fragment() {
    lateinit var handler: Handler
    lateinit var appDatabase: AppDatabase
    lateinit var binding: FragmentAllValyutaBinding
    lateinit var allCurrencyAdapter: AllCurrencyAdapter
    lateinit var myViewModel: MyViewModels
    lateinit var list: ArrayList<Currency>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllValyutaBinding.inflate(layoutInflater)
        handler = Handler(Looper.getMainLooper())
        appDatabase = AppDatabase.getInstance(requireContext())
        BindingObject.activityBinding?.appBarMain?.searchBasic?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var l = ArrayList<Currency>()
                list.forEach {
                    if (it.title.toLowerCase().contains(newText?.toLowerCase()!!.toRegex())) {
                        l.add(it)
                    }
                }
                rvSetAdapter(l)
                return true
            }
        })

        myViewModel = ViewModelProvider(requireActivity()).get(MyViewModels::class.java)
        myViewModel.getUsers().observe(viewLifecycleOwner) { it ->
            list = ArrayList()
            list.addAll(it)
            list.forEach {
                if (it.nbu_cell_price == "")
                    it.nbu_cell_price = it.cb_price
                if (it.nbu_buy_price == "")
                    it.nbu_buy_price = it.cb_price
            }

            rvSetAdapter(list)


        }


        return binding.root
    }

    fun rvSetAdapter(list: List<Currency>) {
        allCurrencyAdapter =
            AllCurrencyAdapter(binding.root.context, list, object : AllCurrencyAdapter.RvClick,
                AdapterView.OnItemSelectedListener {
                override fun onCalculator(currency: Currency) {
                    var list2 = ArrayList<Currency>()
                    list2.addAll(list)
                    list2.add(Currency(list.last().date, "1", "UZS", "1", "1", "o'zbek so'mi"))
                    val index = list2.indexOf(currency)

                    val dialog = AlertDialog.Builder(context, R.style.NewDialog).create()
                    val fragmentCCalculatorBinding =
                        FragmentCalculatorBinding.inflate(layoutInflater)
                    dialog.setView(fragmentCCalculatorBinding.root)

                    fragmentCCalculatorBinding.vContainer1.visibility = View.VISIBLE
                    val spinnerAdapter = SpinnerAdapter(list2)
                    fragmentCCalculatorBinding.spinner1.adapter = spinnerAdapter
                    fragmentCCalculatorBinding.spinner2.adapter = spinnerAdapter

                    fragmentCCalculatorBinding.spinner1.setSelection(index)
                    fragmentCCalculatorBinding.spinner2.setSelection(list2.size - 1)
                    fragmentCCalculatorBinding.spinner1.onItemSelectedListener = this
                    fragmentCCalculatorBinding.spinner2.onItemSelectedListener = this

                    fragmentCCalculatorBinding.imageChangeV.setOnClickListener {
                        val sip = fragmentCCalculatorBinding.spinner1.selectedItemPosition
                        fragmentCCalculatorBinding.spinner1.setSelection(fragmentCCalculatorBinding.spinner2.selectedItemPosition)
                        fragmentCCalculatorBinding.spinner2.setSelection(sip)
                        calculationF(fragmentCCalculatorBinding, list2)
                    }

                    fragmentCCalculatorBinding.edt1.addTextChangedListener {
                        calculationF(fragmentCCalculatorBinding, list2)
                    }

                    dialog.show()
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            })
        binding.rvAllValyuta.adapter = allCurrencyAdapter
    }

    fun calculationF(
        fragmentCCalculatorBinding: FragmentCalculatorBinding,
        list2: ArrayList<Currency>
    ) {
        if (fragmentCCalculatorBinding.edt1.text.isNotEmpty()) {
            var getSum =
                list2[fragmentCCalculatorBinding.spinner1.selectedItemPosition].nbu_buy_price.toDouble() * fragmentCCalculatorBinding.edt1.text.toString()
                    .toDouble()
            var saleSum =
                list2[fragmentCCalculatorBinding.spinner1.selectedItemPosition].nbu_cell_price.toDouble() * fragmentCCalculatorBinding.edt1.text.toString()
                    .toDouble()

            var saleTv =
                (saleSum / list2[fragmentCCalculatorBinding.spinner2.selectedItemPosition].nbu_cell_price.toDouble())
            var getTv =
                (getSum / list2[fragmentCCalculatorBinding.spinner2.selectedItemPosition].nbu_buy_price.toDouble())

            if (saleTv.toString().contains('E') || getTv.toString().contains('E')) {
                fragmentCCalculatorBinding.tvGet.text = getTv.toInt().toString()
                fragmentCCalculatorBinding.tvSale.text = saleTv.toInt().toString()
            } else {
                try {
                    fragmentCCalculatorBinding.tvSale.text =
                        saleTv.toString().subSequence(0, saleTv.toString().indexOf(".") + 3)
                            .toString()
                    fragmentCCalculatorBinding.tvGet.text =
                        getTv.toString().subSequence(0, getTv.toString().indexOf(".") + 3)
                            .toString()
                } catch (e: Exception) {
                    fragmentCCalculatorBinding.tvSale.text = saleTv.toString()
                    fragmentCCalculatorBinding.tvGet.text = getTv.toString()
                }
            }
        }
    }
}