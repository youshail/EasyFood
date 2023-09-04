package com.youshail.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youshail.easyfood.data.remote.dto.Category
import com.youshail.easyfood.databinding.CategoryItemBinding

class CategoriesRecylerAdapter(): RecyclerView.Adapter<CategoriesRecylerAdapter.CategoryViemHolder>(){

    private var categoryList = ArrayList<Category>()

    fun setCategoryList(categoryList: List<Category>){
        this.categoryList = categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }
    class CategoryViemHolder(val binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViemHolder {
        return  CategoryViemHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
       return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViemHolder, position: Int) {
         Glide.with(holder.itemView)
             .load(categoryList[position].strCategoryThumb)
             .into(holder.binding.imageCategory)
        holder.binding.tvCategoryName.text = categoryList[position].strCategory
    }
}