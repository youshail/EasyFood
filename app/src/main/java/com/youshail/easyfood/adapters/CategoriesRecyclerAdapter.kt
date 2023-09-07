package com.youshail.easyfood.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youshail.easyfood.data.remote.dto.Category
import com.youshail.easyfood.databinding.CategoryItemBinding

class CategoriesRecyclerAdapter: RecyclerView.Adapter<CategoriesRecyclerAdapter.CategoryViewHolder>(){

    private var categoryList = ArrayList<Category>()
    var onItemClick : ((Category) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setCategoryList(categoryList: List<Category>){
        this.categoryList = categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }
    class CategoryViewHolder(val binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return  CategoryViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
       return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
         Glide.with(holder.itemView)
             .load(categoryList[position].strCategoryThumb)
             .into(holder.binding.imageCategory)
        holder.binding.tvCategoryName.text = categoryList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(categoryList[position])
        }
    }
}