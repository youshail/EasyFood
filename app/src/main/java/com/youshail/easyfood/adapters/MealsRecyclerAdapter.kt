package com.youshail.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youshail.easyfood.data.remote.dto.Meal
import com.youshail.easyfood.databinding.MealItemBinding

class MealsRecyclerAdapter: RecyclerView.Adapter<MealsRecyclerAdapter.MealsViewHolder>(){

    var onItemClick : ((Meal) -> Unit)? = null
    inner class MealsViewHolder(val binding: MealItemBinding): RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        return MealsViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(differ.currentList[position].strMealThumb)
            .into(holder.binding.ivCategoryItem)
        holder.binding.tvMealName.text = differ.currentList[position].strMeal

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(differ.currentList[position])
        }
    }

}