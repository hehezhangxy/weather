package com.example.sunnyweather.logic

import androidx.lifecycle.liveData
import com.example.sunnyweather.logic.model.Place
import com.example.sunnyweather.logic.network.SunnyWeatherNetwork

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {
    fun <T> searchPlaces(query: String) = liveData<kotlin.Result<T>>(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<T>(e)
        }
        emit(result as Result<T>)
    }
//    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
//        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
//        if (placeResponse.status == "ok") {
//            val places = placeResponse.places
//            Result.success(places)
//        } else {
//            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
//        }
//    }
//    private fun <T> fire(context: CoroutineContext, block: suspend () -> kotlin.Result<T>) =
//        liveData<kotlin.Result<T>>(context) {
//            val result = try {
//                block()
//            } catch (e: Exception) {
//                Result.failure<T>(e)
//            }
//            emit(result)
//        }

}