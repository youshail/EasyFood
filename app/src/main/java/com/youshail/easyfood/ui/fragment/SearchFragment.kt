package com.youshail.easyfood.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.youshail.easyfood.adapters.MealsRecyclerAdapter
import com.youshail.easyfood.databinding.FragmentSearchBinding
import com.youshail.easyfood.ui.activity.MainActivity
import com.youshail.easyfood.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mealsRecyclerAdapter: MealsRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = (activity as MainActivity).homeViewModel

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareSearchRecyclerView()
        onEditeTextChange()
        onSearchBtnClick()
        observeSearchMealsLiveData()
    }

    private fun onSearchBtnClick() {
        binding.ivSearch.setOnClickListener {
            val name = binding.etSearchBox.text.toString()
            name.let {
                homeViewModel.getSearchMeals(name = name)
            }
        }
    }

    private fun onEditeTextChange() {

        var searchJob: Job? = null
        binding.etSearchBox.addTextChangedListener { name->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                homeViewModel.getSearchMeals(name.toString())
            }
        }

    }

    private fun observeSearchMealsLiveData() {
        homeViewModel.observeSearchedMealLiveData().observe(viewLifecycleOwner, Observer { meals ->
            mealsRecyclerAdapter.differ.submitList(meals)
        })
    }

    private fun prepareSearchRecyclerView() {
        mealsRecyclerAdapter = MealsRecyclerAdapter()
        binding.recViewMealsSearched.apply {
            adapter = mealsRecyclerAdapter
            layoutManager = GridLayoutManager(context,2, LinearLayoutManager.VERTICAL,false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

}