package com.youshail.easyfood.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.youshail.easyfood.adapters.CategoryMealRecyclerAdapter
import com.youshail.easyfood.databinding.ActivityCategoryMealBinding
import com.youshail.easyfood.ui.fragment.HomeFragment
import com.youshail.easyfood.viewModel.MealByCategoryViewModel

class CategoryMealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealBinding
    private lateinit var categoryMealRecyclerAdapter: CategoryMealRecyclerAdapter
    private val mealByCategoryViewModel : MealByCategoryViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealByCategoryViewModel.getMealByCategory(intent.getStringExtra(HomeFragment.MEAL_CATEGORY)!!)
        observeMealCategoryLiveData()

        prepareCategorieMealRecyclerView()

    }

    private fun prepareCategorieMealRecyclerView() {
        categoryMealRecyclerAdapter = CategoryMealRecyclerAdapter()

        binding.recViewMeals.apply {
            adapter= categoryMealRecyclerAdapter
            layoutManager = GridLayoutManager(context,2,LinearLayoutManager.VERTICAL,false)
        }
    }

    private fun observeMealCategoryLiveData() {
        mealByCategoryViewModel.observeMealCategoryLiveData().observe(this, Observer { mealList ->
            categoryMealRecyclerAdapter.setMealCategoryList(mealList)
            binding.tvCategoryCount.text = categoryMealRecyclerAdapter.itemCount.toString()
        })
    }
}