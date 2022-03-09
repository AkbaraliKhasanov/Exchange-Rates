package uz.akbarali.exchangerates.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_spinner_c.view.*
import uz.akbarali.exchangerates.R
import uz.akbarali.exchangerates.models.Currency

class SpinnerAdapter(var list: List<Currency>) : BaseAdapter() {
    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView: View = convertView
            ?: LayoutInflater.from(parent?.context).inflate(R.layout.item_spinner_c, parent, false)

        itemView.tv_item_spinner.text = list[position].code

        val str = "https://nbu.uz/local/templates/nbu/images/flags/"
        val str1 = ".png"
        if (position == list.size - 1) {
            itemView.image_spinner_item.setImageResource(R.drawable.flag_uzb)
        } else {
            Picasso.get().load("${str}${list[position].code}$str1")
                .into(itemView.image_spinner_item)
        }


        return itemView
    }
}