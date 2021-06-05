package com.bangkit.whatdish.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.whatdish.data.source.local.FoodEntity
import com.bangkit.whatdish.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var detailbinding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    companion object {
        const val EXTRA_DATA = "extra_data"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailbinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailbinding.root)

        val food = intent.getParcelableExtra<FoodEntity>(EXTRA_DATA) as FoodEntity

        initViews(food)
        initViewModel(food)

    }

    private fun initViews(mfood: FoodEntity){
        detailbinding.tvFoodContent.text = mfood.imageTitle
        Picasso.get()
            .load(mfood.imageUri)
            .fit()
            .into(detailbinding.ivFood)

        detailbinding.tvBack.setOnClickListener {
            finish()
        }
    }

    private fun initViewModel(mfood: FoodEntity){
        detailViewModel.setFoodID((mfood.imageTitle))

        detailViewModel.foodName.observe(this, { foodItem ->
            detailbinding.tvFoodContent.text = foodItem
        })

        detailViewModel.foodListInfo.observe(this, { listInfo ->
            val builder: StringBuilder = StringBuilder()
            for (details in listInfo) {
                builder.append(details + "\n")
            }
            detailbinding.tvListInformFood.text = builder.toString()
        })

        detailViewModel.isLoading.observe(this, {state ->
            if(state){
                detailbinding.progressBar.visibility = View.VISIBLE
                detailbinding.scrollContent.visibility = View.GONE
            }else {
                detailbinding.progressBar.visibility = View.GONE
                detailbinding.scrollContent.visibility = View.VISIBLE
            }
        })

        detailViewModel.message.observe(this, {state ->
            showMessage(state)
        })
    }

    private fun showMessage(code : Int){
        when(code){
            400 -> {
                Log.d("Detail error: ", "400")
                finish()
            }
            500 -> {
                Log.d("Detail error: ", "500")
                finish()
            }
            1 -> {
                Log.d("Detail error: ", "-1")
                finish()
            }
        }
    }

}