package com.youshail.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youshail.easyfood.data.remote.dto.MealCategory
import com.youshail.easyfood.databinding.MealItemBinding

class CategoryMealRecyclerAdapter : RecyclerView.Adapter<CategoryMealRecyclerAdapter.CategoryMealViewHolder>() {

    private var mealList = ArrayList<MealCategory>()


    fun setMealCategoryList(mealList: List<MealCategory>){
      this.mealList = mealList as ArrayList<MealCategory>
        notifyDataSetChanged()
    }
    inner class CategoryMealViewHolder(val binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewHolder {
        return CategoryMealViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: CategoryMealViewHolder, position: Int) {
        Glide.with(holder.itemView).load(
            mealList[position].strMealThumb
        ).into(holder.binding.ivCategoryItem)
        holder.binding.tvMealName.text = mealList[position].strMeal
    }


}