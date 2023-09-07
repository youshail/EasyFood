package com.youshail.easyfood.ui.fragment.bottomSheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.youshail.easyfood.databinding.FragmentMealBottomSheetBinding
import com.youshail.easyfood.ui.activity.MainActivity
import com.youshail.easyfood.ui.activity.MealActivity
import com.youshail.easyfood.ui.fragment.HomeFragment.Companion.MEAL_NAME
import com.youshail.easyfood.ui.fragment.HomeFragment.Companion.MEAL_ID
import com.youshail.easyfood.ui.fragment.HomeFragment.Companion.MEAL_THUMB
import com.youshail.easyfood.viewModel.HomeViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ID_MEAL = "mealId"



/**
 * A simple [Fragment] subclass.
 * Use the [MealBottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealBottomSheetFragment : BottomSheetDialogFragment() {

    private var mealId: String? = null
    private lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(ID_MEAL)

        }

        homeViewModel = (activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let {
            homeViewModel.getMealById(it)
        }

        observeBottomSheetMeal()

        onBottomSheetClick()
    }

    private fun onBottomSheetClick() {
        binding.bottomSheetId.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.apply {
                putExtra(MEAL_ID,mealId)
                putExtra(MEAL_NAME,mealName)
                putExtra(MEAL_THUMB,mealThumb)
            }
            startActivity(intent)
        }
    }


    private var mealName: String? = null
    private var mealThumb: String? = null
    private fun observeBottomSheetMeal() {
        homeViewModel.observeBottomSheetMealLiveData()
            .observe(viewLifecycleOwner, Observer { meal ->
                Glide.with(this)
                    .load(meal.strMealThumb)
                    .into(binding.imgMealBottomSheet)
                binding.tvBottomSheetArea.text = meal.strArea
                binding.tvBottomSheetCategory.text = meal.strCategory
                binding.tvBottomSheetName.text = meal.strCategory

                mealName = meal.strMeal
                mealThumb = meal.strMealThumb
            })
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ID_MEAL, param1)

                }
            }
    }
}