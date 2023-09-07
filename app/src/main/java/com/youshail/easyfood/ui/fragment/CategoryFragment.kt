package com.youshail.easyfood.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.youshail.easyfood.adapters.CategoriesRecyclerAdapter
import com.youshail.easyfood.adapters.CategoryMealRecyclerAdapter
import com.youshail.easyfood.data.remote.dto.Category
import com.youshail.easyfood.databinding.FragmentCategoryBinding
import com.youshail.easyfood.ui.activity.CategoryMealActivity
import com.youshail.easyfood.ui.activity.MainActivity
import com.youshail.easyfood.ui.activity.MealActivity
import com.youshail.easyfood.ui.fragment.HomeFragment.Companion.MEAL_CATEGORY
import com.youshail.easyfood.viewModel.HomeViewModel


class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoriesRecyclerAdapter: CategoriesRecyclerAdapter
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        homeViewModel = (activity as MainActivity).homeViewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeCategoryLiveData()

        onCategoryItemClick()
    }

    private fun onCategoryItemClick() {
        categoriesRecyclerAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealActivity::class.java)
            intent.putExtra(MEAL_CATEGORY,category.strCategory)
            startActivity(intent)
        }
    }

    private fun observeCategoryLiveData() {
        homeViewModel.observeCategoryListLiveData().observe(viewLifecycleOwner , Observer { categories ->
            categoriesRecyclerAdapter.setCategoryList(categories)
        })
    }

    private fun prepareRecyclerView() {
        categoriesRecyclerAdapter = CategoriesRecyclerAdapter()
        binding.recViewMealsCategory.apply {
            adapter= categoriesRecyclerAdapter
            layoutManager = GridLayoutManager(context,3 , GridLayoutManager.VERTICAL, false)
        }
    }


}