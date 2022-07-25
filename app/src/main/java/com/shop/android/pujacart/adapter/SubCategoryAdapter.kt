package com.shop.android.pujacart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.shop.android.pujacart.data.category.SubCategoryResponseItem
import com.shop.android.pujacart.databinding.RvCategoryBinding


class SubCategoryAdapter(val glide : RequestManager)  : RecyclerView.Adapter<SubCategoryAdapter.ProductViewHolder>()  {

    class ProductViewHolder(val binding : RvCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((SubCategoryResponseItem) -> Unit)? = null

    fun setOnItemClickListener(position: (SubCategoryResponseItem) -> Unit) {
        onItemClickListener = position
    }
    

    private val diffCallback = object : DiffUtil.ItemCallback<SubCategoryResponseItem>() {

        override fun areContentsTheSame(oldItem: SubCategoryResponseItem, newItem: SubCategoryResponseItem): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: SubCategoryResponseItem, newItem: SubCategoryResponseItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }


    private val differ = AsyncListDiffer(this, diffCallback)

    var categoryList : List<SubCategoryResponseItem>
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

                glide.load(data.PSubCategoryImage).into(binding.ivProduct)

                binding.tvPrice.text = data.PSubCategoryName

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