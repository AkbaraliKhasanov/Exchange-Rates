package uz.akbarali.exchangerates.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_vp_card.view.*
import uz.akbarali.exchangerates.R
import uz.akbarali.exchangerates.models.Currency


class CardAdapter(val list: List<Currency>) : PagerAdapter() {
    override fun getCount(): Int = list.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val layoutInflater =
            LayoutInflater.from(container.context).inflate(R.layout.item_vp_card, container, false)

        layoutInflater.tv_date.text = list[position].date
        if (list[position].nbu_cell_price.isEmpty()) {
            layoutInflater.tv_olish_narxi.text = "Mavjud emas"
        } else {
            layoutInflater.tv_olish_narxi.text = list[position].nbu_cell_price + " UZS"
        }
        if (list[position].nbu_buy_price.isEmpty()) {
            layoutInflater.tv_olish_narxi.text = "Mavjud emas"
        } else {
            layoutInflater.tv_sotish_narxi.text = list[position].nbu_buy_price + " UZS"
        }


        val str1 = "https://nbu.uz/local/templates/nbu/images/flags/"
        val s = ".png"

        Picasso.get().load("${str1}${list[position].code}$s").into(layoutInflater.image_dollor)

        container.addView(layoutInflater)

        return layoutInflater
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }
}