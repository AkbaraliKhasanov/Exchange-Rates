package uz.akbarali.exchangerates.retrofit

import retrofit2.Call
import retrofit2.http.GET
import uz.akbarali.exchangerates.models.Currency

interface ApiService {
    @GET("json")
    fun getCurrencyData(): Call<List<Currency>>
}