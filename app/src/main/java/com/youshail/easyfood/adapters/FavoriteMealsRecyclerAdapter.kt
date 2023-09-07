package com.youshail.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youshail.easyfood.data.local.MealInfo
import com.youshail.easyfood.databinding.MealItemBinding

class FavoriteMealsRecyclerAdapter: RecyclerView.Adapter<FavoriteMealsRecyclerAdapter.FavoriteMealsViewHolder>(){

    var onItemClick : ((MealInfo) -> Unit)? = null
    inner class FavoriteMealsViewHolder(val binding: MealItemBinding): RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<MealInfo>(){
        override fun areItemsTheSame(oldItem: MealInfo, newItem: MealInfo): Boolean {
            return oldItem.mealId == newItem.mealId
        }

        override fun areContentsTheSame(oldItem: MealInfo, newItem: MealInfo): Boolean {
            return oldItem == newItem
        }

    }

     val differ = AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMealsViewHolder {
        return FavoriteMealsViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoriteMealsViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(differ.currentList[position].mealThumb)
            .into(holder.binding.ivCategoryItem)
        holder.binding.tvMealName.text = differ.currentList[position].mealName

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(differ.currentList[position])
        }
    }

}