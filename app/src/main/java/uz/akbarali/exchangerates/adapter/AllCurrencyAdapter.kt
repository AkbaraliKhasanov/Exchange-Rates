package uz.akbarali.exchangerates.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.akbarali.exchangerates.R
import uz.akbarali.exchangerates.databinding.ItemRvAllMoneyBinding
import uz.akbarali.exchangerates.models.Currency


class AllCurrencyAdapter(val context: Context, val list: List<Currency>, var onCLick: RvClick) :
    RecyclerView.Adapter<AllCurrencyAdapter.Vh>() {

    inner class Vh(var binding: ItemRvAllMoneyBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(currency: Currency) {
            binding.tvNameItem.text = currency.title
            if (currency.nbu_buy_price != "") {
                binding.tvOlishNarxi.text = currency.nbu_buy_price + " UZS"
            } else {
                binding.tvOlishNarxi.text = currency.cb_price + " UZS"
            }
            if (currency.nbu_cell_price != "") {
                binding.tvSotishNarxi.text = currency.nbu_cell_price + " UZS"
            } else {
                binding.tvSotishNarxi.text = currency.cb_price + " UZS"
            }
            binding.imageCalculation.setOnClickListener {
                onCLick.onCalculator(currency)
            }
            val str = "https://nbu.uz/local/templates/nbu/images/flags/"
            val str1 = ".png"
            Picasso.get().load("${str}${list[position].code}$str1").into(binding.imageMoney)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvAllMoneyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
//        val animation: Animation = AnimationUtils.loadAnimation(
//            context,
//            R.anim.rv_item_anim
//        )
//        holder.binding.root.animation = animation
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface RvClick {
        fun onCalculator(currency: Currency)
    }
}