package com.youshail.easyfood.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.youshail.easyfood.R
import com.youshail.easyfood.adapters.FavoriteMealsRecyclerAdapter
import com.youshail.easyfood.databinding.FragmentFavoriteMealsBinding
import com.youshail.easyfood.ui.activity.MainActivity
import com.youshail.easyfood.ui.activity.MealActivity
import com.youshail.easyfood.ui.fragment.HomeFragment.Companion.MEAL_ID
import com.youshail.easyfood.ui.fragment.HomeFragment.Companion.MEAL_NAME
import com.youshail.easyfood.ui.fragment.HomeFragment.Companion.MEAL_THUMB
import com.youshail.easyfood.viewModel.HomeViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteMeals.newInstance] factory method to
 * create an instance of this fragment.
 */


class FavoriteMeals : Fragment() {
    private lateinit var binding: FragmentFavoriteMealsBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var favoriteMealsRecyclerAdapter: FavoriteMealsRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel =(activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteMealsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavoriteLiveData()
        onFavoriteMealClick()

        onItemMealMove()
    }

    private fun onItemMealMove() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val  position = viewHolder.adapterPosition
                homeViewModel.deleteMeal(favoriteMealsRecyclerAdapter.differ.currentList[position])
                val deletedMeal = favoriteMealsRecyclerAdapter.differ.currentList[position]
                Snackbar.make(
                    requireView(),
                    "Meal Deleted",
                    Snackbar.LENGTH_LONG
                ).setAction(
                    "Undo"
                ) {
                    homeViewModel.insertMeal(deletedMeal)
                }.show()
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recViewFavoriteMeals)
    }

    private fun onFavoriteMealClick() {
        favoriteMealsRecyclerAdapter.onItemClick = { meal->
            var intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.mealId)
            intent.putExtra(MEAL_NAME,meal.mealName)
            intent.putExtra(MEAL_THUMB,meal.mealThumb)
            startActivity(intent)
        }
    }

    private fun prepareRecyclerView() {
        favoriteMealsRecyclerAdapter = FavoriteMealsRecyclerAdapter()
        binding.recViewFavoriteMeals.apply {
            adapter = favoriteMealsRecyclerAdapter
            layoutManager = GridLayoutManager(context,2, LinearLayoutManager.VERTICAL,false)
        }
    }

    private fun observeFavoriteLiveData() {
        homeViewModel.observeFavoriteMailLiveData().observe(viewLifecycleOwner, Observer { meals ->
            favoriteMealsRecyclerAdapter.differ.submitList(meals)
        })
    }

}