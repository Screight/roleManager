package com.example.rolemanager
import retrofit2.http.GET
import retrofit2.Call

interface ApiQuotes {
    @GET("random.json")
    fun getRandomQuote() : Call<Quote>

}