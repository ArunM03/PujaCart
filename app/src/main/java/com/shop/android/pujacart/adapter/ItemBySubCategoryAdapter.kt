package com.shop.android.pujacart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.shop.android.pujacart.data.item.GetItemBySubCatResponseItem
import com.shop.android.pujacart.databinding.RvCategoryBinding
import com.shop.android.pujacart.databinding.RvProductBinding


class ItemBySubCategoryAdapter(val glide : RequestManager)  : RecyclerView.Adapter<ItemBySubCategoryAdapter.ProductViewHolder>()  {

    class ProductViewHolder(val binding : RvProductBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((GetItemBySubCatResponseItem) -> Unit)? = null

    fun setOnItemClickListener(position: (GetItemBySubCatResponseItem) -> Unit) {
        onItemClickListener = position
    }
    

    private val diffCallback = object : DiffUtil.ItemCallback<GetItemBySubCatResponseItem>() {

        override fun areContentsTheSame(oldItem: GetItemBySubCatResponseItem, newItem: GetItemBySubCatResponseItem): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: GetItemBySubCatResponseItem, newItem: GetItemBySubCatResponseItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }


    private val differ = AsyncListDiffer(this, diffCallback)

    var categoryList : List<GetItemBySubCatResponseItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = RvProductBinding.inflate(
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

                binding.tvPrice.text = "₹${data.PPriceTag}"

                binding.tvProductname.text = data.ProductName

                binding.tvProductquantity.text = "Size : ${data.PSizeType}"

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