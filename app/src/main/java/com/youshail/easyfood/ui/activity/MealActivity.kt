package com.youshail.easyfood.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.youshail.easyfood.R
import com.youshail.easyfood.databinding.ActivityMealBinding
import com.youshail.easyfood.ui.fragment.HomeFragment
import com.youshail.easyfood.viewModel.MealDetailViewModel

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youtubeLink: String

    private val mealDetailViewModel : MealDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setMealInformationFromIntent()
        setInformationInView()

        loadingCase()
        mealDetailViewModel.getMealDetail(mealId)
        observeMealDetailLiveData()

        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }


    private fun observeMealDetailLiveData() {
        mealDetailViewModel.observeMailDetailLiveData().observe(this
        ) { meal ->
            onResponceCase()
            binding.tvCategoryInfo.text = "Category : ${meal.strCategory}"
            binding.tvAreaInfo.text = "Area : ${meal.strArea}"
            binding.tvInstructions.text = meal.strInstructions
            youtubeLink = meal.strYoutube

        }
    }

    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }
    private fun onResponceCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }

    private fun setInformationInView() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun setMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }
}