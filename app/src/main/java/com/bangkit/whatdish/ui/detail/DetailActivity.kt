package com.bangkit.whatdish.ui.detail

import android.os.Bundle
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

        detailViewModel.setFoodID((food.imageTitle))
        detailViewModel.activity = (this@DetailActivity)

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

}