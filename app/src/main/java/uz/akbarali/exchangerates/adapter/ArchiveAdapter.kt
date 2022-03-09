package uz.akbarali.exchangerates.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.akbarali.exchangerates.databinding.ItemRvArchiveBinding
import uz.akbarali.exchangerates.models.Currency

class ArchiveAdapter(var context: Context, val list: List<Currency>) : RecyclerView.Adapter<ArchiveAdapter.Vh>() {

    inner class Vh(var itemRv: ItemRvArchiveBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(currency: Currency) {
            itemRv.tvDate.text = currency.date.subSequence(0, currency.date.indexOf(" "))
            itemRv.tvTime.text = currency.date.subSequence(currency.date.indexOf(" "), currency.date.length)

            if (currency.nbu_buy_price!="" || currency.nbu_cell_price!="") {
                itemRv.tvSot.text = currency.nbu_cell_price
                itemRv.tvOl.text = currency.nbu_buy_price
            }else{
                itemRv.tvOl.text = currency.cb_price
                itemRv.tvSot.text = currency.cb_price
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvArchiveBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}