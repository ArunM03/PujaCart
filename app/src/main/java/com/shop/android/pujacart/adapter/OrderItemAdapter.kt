package com.shop.android.pujacart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.shop.android.pujacart.data.order.GetOrderItemDetailsResponseItem
import com.shop.android.pujacart.databinding.RvCategoryBinding
import com.shop.android.pujacart.databinding.RvOrdersBinding
import com.shop.android.pujacart.databinding.RvOrdershistoryBinding


class OrderItemAdapter()  : RecyclerView.Adapter<OrderItemAdapter.ProductViewHolder>()  {

    class ProductViewHolder(val binding : RvOrdersBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((GetOrderItemDetailsResponseItem) -> Unit)? = null

    fun setOnItemClickListener(position: (GetOrderItemDetailsResponseItem) -> Unit) {
        onItemClickListener = position
    }
    

    private val diffCallback = object : DiffUtil.ItemCallback<GetOrderItemDetailsResponseItem>() {

        override fun areContentsTheSame(oldItem: GetOrderItemDetailsResponseItem, newItem: GetOrderItemDetailsResponseItem): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: GetOrderItemDetailsResponseItem, newItem: GetOrderItemDetailsResponseItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }


    private val differ = AsyncListDiffer(this, diffCallback)

    var categoryList : List<GetOrderItemDetailsResponseItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    var visibleItemPositions = mutableListOf<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = RvOrdersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return  ProductViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return categoryList.size
    }


    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val data = categoryList[position]
        holder.itemView.apply {

            with(holder) {
                
                binding.tvProductnameData.text = data.ProductName
                binding.tvSizeData.text = data.ProductSize
                binding.tvQuantityData.text = data.ProductQuantity.toString()
                binding.tvUnitpriceData.text = "₹${data.ProductQuantity}"
                binding.tvSstData.text = data.GST.toString()
                binding.tvBrandData.text = data.Brand
                binding.tvCategoryData.text = data.ProductType


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