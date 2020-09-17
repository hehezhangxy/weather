package com.example.sunnyweather.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import retrofit2.http.Query
import javax.xml.transform.Result
import kotlin.coroutines.CoroutineContext

class Repository {
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            kotlin.Result.success(places)
        } else {
            kotlin.Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }
    private fun <T> fire(context: CoroutineContext, block: suspend () -> kotlin.Result<T>) =
        liveData<kotlin.Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                kotlin.Result.failure<T>(e)
            }
            emit(result)
        }
}