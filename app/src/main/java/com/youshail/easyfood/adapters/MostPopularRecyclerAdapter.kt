package com.youshail.easyfood.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youshail.easyfood.data.remote.dto.MealCategory
import com.youshail.easyfood.databinding.PopularMealsBinding

 class MostPopularRecyclerAdapter() : RecyclerView.Adapter<MostPopularRecyclerAdapter.PopularMealViewHolder>() {
     private var mealsList = ArrayList<MealCategory>()
     lateinit var onItemClick :((MealCategory)-> Unit)
     var onLongItemClick : ((MealCategory)-> Unit)? = null

     @SuppressLint("NotifyDataSetChanged")
     fun setMeals(mealsList: ArrayList<MealCategory>){
         this.mealsList = mealsList
         notifyDataSetChanged()
     }
    class PopularMealViewHolder(val binding: PopularMealsBinding): RecyclerView.ViewHolder(binding.root)

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
         return PopularMealViewHolder(PopularMealsBinding.inflate(LayoutInflater.from(parent.context),parent,false))

     }

     override fun getItemCount(): Int {
         return mealsList.size
     }

     override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
         Glide.with(holder.itemView)
             .load(mealsList[position].strMealThumb)
             .into(holder.binding.imgPopularMeal)

         holder.itemView.setOnClickListener {
             onItemClick.invoke(mealsList[position])
         }

         holder.itemView.setOnLongClickListener {
             onLongItemClick?.invoke(mealsList[position])
             true
         }
     }
 }