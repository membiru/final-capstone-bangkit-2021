package com.bangkit.whatdish.ui.detail

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.whatdish.R
import com.bangkit.whatdish.data.source.local.FoodEntity
import com.bangkit.whatdish.data.source.remote.ApiConfig
import com.bangkit.whatdish.data.source.remote.response.FoodResponse
import com.bangkit.whatdish.data.source.remote.response.Message
import kotlinx.coroutines.delay
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

    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int> = _message

    private var foodID = ""
    private var isNeverRunBefore = true

    init {
        findFoodInfo()
    }

    private fun findFoodInfo() {
        _isLoading.value = true
        _message.value = 0

        val client = ApiConfig.getApiService().getFood(foodID)
        client.enqueue(object : Callback<FoodResponse> {
            override fun onResponse(
                call: Call<FoodResponse>,
                response: Response<FoodResponse>
            ) {
                if (response.isSuccessful) {
                    isNeverRunBefore = false

                    _isLoading.value = false
                    _foodItem.value = response.body()?.message?.imagePath
                    _foodListInfo.value = response.body()?.message?.information
                } else {
                    for (i in 1..3){
                        if (isNeverRunBefore){
                            Handler().postDelayed({
                                if (isNeverRunBefore){
                                    findFoodInfo()
                                }
                            }, 500)
                        } else {
                            when(response.code()){
                                400 -> _message.value = 400
                                500 -> _message.value = 500
                            }
                            break
                        }

                    }
                }
            }

            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = 1
            }
        })
    }

    fun setFoodID(x: String){
        this.foodID = x
    }

}