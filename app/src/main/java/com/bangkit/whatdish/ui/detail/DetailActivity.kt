package com.bangkit.whatdish.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.bangkit.whatdish.R
import com.bangkit.whatdish.data.source.local.FoodEntity
import com.bangkit.whatdish.databinding.ActivityDetailBinding
import com.bangkit.whatdish.ui.main.MainActivity
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

        detailViewModel.foodName.observe(this, { foodItem ->
            detailbinding.tvFoodTitle.text = foodItem
        })

        detailViewModel.foodListInfo.observe(this, { listInfo ->
            detailbinding.lvInfo.adapter = ArrayAdapter(this, R.layout.item_list_inform, listInfo)
        })

        detailViewModel.isLoading.observe(this, {state ->
            if(state){
                detailbinding.progressBar.visibility = View.VISIBLE
                detailbinding.informLayout.visibility = View.GONE
            }else {
                detailbinding.progressBar.visibility = View.GONE
                detailbinding.informLayout.visibility = View.VISIBLE
            }
        })



    }

    private fun initViews(mfood: FoodEntity){
//        detailbinding.tvFoodTitle.text = mfood.imageTitle
        Picasso.get().load(mfood.imageUri).into(detailbinding.ivFood)
        detailbinding.tvBack.setOnClickListener {
            finish()
        }
    }

}