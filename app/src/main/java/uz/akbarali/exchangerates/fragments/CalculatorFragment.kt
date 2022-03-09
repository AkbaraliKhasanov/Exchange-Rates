package uz.akbarali.exchangerates.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_calculator.*
import uz.akbarali.exchangerates.adapter.SpinnerAdapter
import uz.akbarali.exchangerates.databinding.FragmentCalculatorBinding
import uz.akbarali.exchangerates.models.Currency
import uz.akbarali.exchangerates.utils.MyViewModels
import java.lang.Exception

class CalculatorFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentCalculatorBinding
    lateinit var myViewModel: MyViewModels
    lateinit var list: ArrayList<Currency>
    lateinit var spinnerAdapter: SpinnerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalculatorBinding.inflate(layoutInflater)
        list = ArrayList<Currency>()
        myViewModel = ViewModelProvider(requireActivity()).get(MyViewModels::class.java)
        myViewModel.getUsers().observe(viewLifecycleOwner) { it ->

            list.addAll(it)
            list.forEach {
                if (it.nbu_cell_price == "")
                    it.nbu_cell_price = it.cb_price
                if (it.nbu_buy_price == "")
                    it.nbu_buy_price = it.cb_price
            }

            list.add(Currency(list.last().date, "1", "UZS", "1", "1", "O'zbek so'mi"))
            spinnerAdapter = SpinnerAdapter(list)
            binding.spinner1.adapter = spinnerAdapter
            binding.spinner2.adapter = spinnerAdapter
            spinner_1.setSelection(list.size - 1)
            spinner_2.setSelection(list.size - 1)

        }

        binding.imageChangeV.setOnClickListener {
            val sip = binding.spinner1.selectedItemPosition
            binding.spinner1.setSelection(binding.spinner2.selectedItemPosition)
            binding.spinner2.setSelection(sip)
            calculation()
        }

        binding.edt1.addTextChangedListener {
            calculation()
        }

        return binding.root
    }

    private fun calculation() {
        if (binding.edt1.text.isNotEmpty()) {
            val getSum =
                list[binding.spinner1.selectedItemPosition].nbu_buy_price.toDouble() * binding.edt1.text.toString()
                    .toDouble()
            val saleSum =
                list[binding.spinner1.selectedItemPosition].nbu_cell_price.toDouble() * binding.edt1.text.toString()
                    .toDouble()
            val tv =
                (saleSum / list[binding.spinner2.selectedItemPosition].nbu_cell_price.toDouble())
            val tv1 =
                (getSum / list[binding.spinner2.selectedItemPosition].nbu_buy_price.toDouble())

            if (tv.toString().contains('E') || tv1.toString().contains('E')) {
                binding.tvGet.text = tv1.toInt().toString()
                binding.tvSale.text = tv.toInt().toString()
            } else {
                try {
                    binding.tvSale.text =
                        tv.toString().subSequence(0, tv.toString().indexOf(".") + 3)
                            .toString()
                    binding.tvGet.text =
                        tv1.toString().subSequence(0, tv1.toString().indexOf(".") + 3)
                            .toString()
                } catch (e: Exception) {
                    binding.tvSale.text = tv.toString()
                    binding.tvGet.text = tv1.toString()
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        binding.spinner1.onItemSelectedListener = this
        binding.spinner2.onItemSelectedListener = this


    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p: Int, p3: Long) {
        binding.tvSale.text =
            list[p].nbu_cell_price
        binding.tvGet.text =
            list[p].nbu_buy_price
        calculation()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}