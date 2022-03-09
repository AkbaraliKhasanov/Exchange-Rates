package uz.akbarali.exchangerates.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Currency:Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var date: String = "null"
    var cb_price: String = "null"
    var code: String = "null"
    var nbu_buy_price: String = "null"
    var nbu_cell_price: String = "null"
    var title: String = "null"

    constructor(
        date: String,
        cb_price: String,
        code: String,
        nbu_buy_price: String,
        nbu_cell_price: String,
        title: String
    ) {
        this.date = date
        this.cb_price = cb_price
        this.code = code
        this.nbu_buy_price = nbu_buy_price
        this.nbu_cell_price = nbu_cell_price
        this.title = title
    }

    constructor()


    override fun toString(): String {
        return "Valyuta(id=$id, date=$date, cb_price=$cb_price, code=$code, nbu_buy_price=$nbu_buy_price, nbu_cell_price=$nbu_cell_price, title=$title)"
    }
}