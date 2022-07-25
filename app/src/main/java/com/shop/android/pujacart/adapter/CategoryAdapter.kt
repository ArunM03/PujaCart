package com.shop.android.pujacart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.shop.android.pujacart.data.category.CategoryResponseItem
import com.shop.android.pujacart.databinding.RvCategoryBinding


class CategoryAdapter(val glide : RequestManager)  : RecyclerView.Adapter<CategoryAdapter.ProductViewHolder>()  {

    class ProductViewHolder(val binding : RvCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((CategoryResponseItem) -> Unit)? = null

    fun setOnItemClickListener(position: (CategoryResponseItem) -> Unit) {
        onItemClickListener = position
    }
    

    private val diffCallback = object : DiffUtil.ItemCallback<CategoryResponseItem>() {

        override fun areContentsTheSame(oldItem: CategoryResponseItem, newItem: CategoryResponseItem): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: CategoryResponseItem, newItem: CategoryResponseItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }


    private val differ = AsyncListDiffer(this, diffCallback)

    var categoryList : List<CategoryResponseItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    var visibleItemPositions = mutableListOf<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = RvCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return  ProductViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return categoryList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val data = categoryList[position]
        holder.itemView.apply {

            with(holder) {

                glide.load(data.PImage).into(binding.ivProduct)

                binding.tvPrice.text = data.PType

            }

            setOnClickListener {

                onItemClickListener?.let {
                        click ->
                    click(data)
                }
            }
        }
    }





}