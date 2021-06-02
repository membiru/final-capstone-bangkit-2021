package com.bangkit.whatdish.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.whatdish.data.source.remote.ApiConfig
import com.bangkit.whatdish.data.source.remote.response.FoodResponse
import com.bangkit.whatdish.data.source.remote.response.Message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _foodItem = MutableLiveData <String>()
    val foodName : LiveData<String> = _foodItem

    private val _foodListInfo = MutableLiveData <List<String>>()
    val foodListInfo : LiveData<List<String>> = _foodListInfo

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "DetailViewModel"
        private const val FOOD_ID = "demo-image.png" //masih dummy
    }

    init {
        findFoodInfo()
    }

    fun findFoodInfo() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFood(FOOD_ID)
        client.enqueue(object : Callback<FoodResponse> {
            override fun onResponse(
                call: Call<FoodResponse>,
                response: Response<FoodResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _foodItem.value = response.body()?.message?.imagePath
                    _foodListInfo.value = response.body()?.message?.information
                    Log.e(TAG, "onSuccess: ${response.body()}")
                    Log.e("1", _foodItem.value.toString())
                    Log.e("2", _foodListInfo.value.toString())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}