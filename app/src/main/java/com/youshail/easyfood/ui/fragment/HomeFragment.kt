package com.youshail.easyfood.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.youshail.easyfood.adapters.CategoriesRecylerAdapter
import com.youshail.easyfood.adapters.MostPopularRecyclerAdapter
import com.youshail.easyfood.data.remote.dto.Category
import com.youshail.easyfood.data.remote.dto.Meal
import com.youshail.easyfood.data.remote.dto.MealCategory
import com.youshail.easyfood.databinding.FragmentHomeBinding
import com.youshail.easyfood.ui.activity.MealActivity
import com.youshail.easyfood.viewModel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var randomMeal: Meal
    private lateinit var popularMealsAdapter: MostPopularRecyclerAdapter
    private lateinit var categoriesRecylerAdapter: CategoriesRecylerAdapter

    companion object{
        const val MEAL_ID ="com.youshail.easyfood.ui.fragment.idMeal"
        const val MEAL_NAME ="com.youshail.easyfood.ui.fragment.nameMeal"
        const val MEAL_THUMB ="com.youshail.easyfood.ui.fragment.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        popularMealsAdapter = MostPopularRecyclerAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeViewModel.getPopularMeals("Seafood")
        preparePopularMealsRecyclerView()
        observePopularMealsLiveData()
        onPopularMealClick()

        prepareCategoriesRecyclerView()
        homeViewModel.getCategories()
        observeCategoriesLiveData()


    }

    private fun prepareCategoriesRecyclerView() {
        categoriesRecylerAdapter = CategoriesRecylerAdapter()
        binding.recyclerView.apply {
            adapter = categoriesRecylerAdapter
            layoutManager = GridLayoutManager(context,3, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun observeCategoriesLiveData() {
        homeViewModel.observeCategoryListLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categories.forEach { category: Category ->
                categoriesRecylerAdapter.setCategoryList(categories)
            }
        })
    }

    private fun onPopularMealClick() {
        popularMealsAdapter.onItemClick = { meal ->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularMealsRecyclerView() {
        binding.recViewMealsPopular.apply {
            adapter = popularMealsAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun observePopularMealsLiveData() {
        homeViewModel.observePopularMealsLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            popularMealsAdapter.setMeals(mealsList = mealList as ArrayList<MealCategory>)
        }

    }

    private fun onRandomMealClick() {
        binding.randomMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeViewModel.observeRandomLiveData().observe(
            viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal?.strMealThumb)
                .into(binding.imgRandomMeal)
            this.randomMeal = meal
        }
    }
}