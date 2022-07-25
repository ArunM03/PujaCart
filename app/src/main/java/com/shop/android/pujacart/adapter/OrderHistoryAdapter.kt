package com.shop.android.pujacart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.shop.android.pujacart.data.order.GetOrderDetailsResponseItem
import com.shop.android.pujacart.databinding.RvCategoryBinding
import com.shop.android.pujacart.databinding.RvOrdershistoryBinding


class OrderHistoryAdapter()  : RecyclerView.Adapter<OrderHistoryAdapter.ProductViewHolder>()  {

    class ProductViewHolder(val binding : RvOrdershistoryBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((GetOrderDetailsResponseItem) -> Unit)? = null

    fun setOnItemClickListener(position: (GetOrderDetailsResponseItem) -> Unit) {
        onItemClickListener = position
    }
    

    private val diffCallback = object : DiffUtil.ItemCallback<GetOrderDetailsResponseItem>() {

        override fun areContentsTheSame(oldItem: GetOrderDetailsResponseItem, newItem: GetOrderDetailsResponseItem): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: GetOrderDetailsResponseItem, newItem: GetOrderDetailsResponseItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }


    private val differ = AsyncListDiffer(this, diffCallback)

    var categoryList : List<GetOrderDetailsResponseItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    var visibleItemPositions = mutableListOf<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = RvOrdershistoryBinding.inflate(
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

                binding.tvOrdernoData.text = data.OrderNo.toString()
                binding.tvDate.text = "Date : ${data.OrderCreatedDate}"
                binding.tvItemsData.text = data.NoofItems.toString()
                binding.tvPayableamountData.text = "₹${data.PayableAmount}"
                binding.tvTotalpriceData.text = "₹${data.TotalPrice}"
                binding.tvPaymentData.text = data.PaymentDone
                binding.tvOrderstatusData.text = data.OrderStatus

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