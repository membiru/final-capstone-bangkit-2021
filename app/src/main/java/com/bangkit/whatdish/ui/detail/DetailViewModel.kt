package com.bangkit.whatdish.ui.detail

import android.app.Activity
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

    private var foodID = ""
    private var isNeverRunBefore = true
    lateinit var activity: Activity

    init {
        findFoodInfo()
    }

    private fun findFoodInfo() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFood(foodID)
        Log.d("API:", client.toString())
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
                        Handler().postDelayed({
                            if (isNeverRunBefore){
                                findFoodInfo()
                            }
                        }, 800)
                        break
                    }
                    isNeverRunBefore = true
                }
            }

            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                _isLoading.value = false
                show(t.message.toString())
                activity.finish()
            }
        })
    }

    fun setFoodID(x: String){
        this.foodID = x
    }

    private fun show(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }


}