package uz.akbarali.exchangerates.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import uz.akbarali.exchangerates.models.Currency

@Dao
interface CurrencyDao {

    @Query("select * from currency")
    fun getAllCurrencyModel(): List<Currency>

    @Insert
    fun addCurrencyModel(currencyListModel: Currency)

    @Insert(onConflict = REPLACE)
    fun addCountries(countryModel: List<Currency?>?)
}