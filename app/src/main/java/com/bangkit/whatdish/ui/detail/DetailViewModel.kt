package com.bangkit.whatdish.ui.detail

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.whatdish.R
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

    lateinit var activity: Activity

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
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    when(response.code()){
                        400 -> show(activity.getString(R.string.err_400))
                        500 -> show(activity.getString(R.string.err_500))
                        else -> show (response.message())
                    }
                    activity.finish()
                }
            }
            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                _isLoading.value = false
                show(t.message.toString())
                activity.finish()
            }
        })
    }

    private fun show(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}