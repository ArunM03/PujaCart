package com.shop.android.pujacart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.shop.android.pujacart.data.user.GetUserAddressResponseItem
import com.shop.android.pujacart.databinding.RvAddressBinding
import com.shop.android.pujacart.databinding.RvCartBinding
import com.shop.android.pujacart.databinding.RvCategoryBinding


class AddressAdapter()  : RecyclerView.Adapter<AddressAdapter.ProductViewHolder>()  {

    class ProductViewHolder(val binding : RvAddressBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((GetUserAddressResponseItem) -> Unit)? = null

    fun setOnItemClickListener(position: (GetUserAddressResponseItem) -> Unit) {
        onItemClickListener = position
    }

    private var onItemClickListenerForDelete: ((GetUserAddressResponseItem) -> Unit)? = null

    private var onItemClickListenerForQuantity: ((GetUserAddressResponseItem) -> Unit)? = null

    fun setOnItemClickListenerForDelete(position: (GetUserAddressResponseItem) -> Unit) {
        onItemClickListenerForDelete = position
    }

    fun setOnItemClickListenerForQuantity(position: (GetUserAddressResponseItem) -> Unit) {
        onItemClickListenerForQuantity = position
    }

    private val diffCallback = object : DiffUtil.ItemCallback<GetUserAddressResponseItem>() {

        override fun areContentsTheSame(oldItem: GetUserAddressResponseItem, newItem: GetUserAddressResponseItem): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: GetUserAddressResponseItem, newItem: GetUserAddressResponseItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }


    private val differ = AsyncListDiffer(this, diffCallback)

    var categoryList : List<GetUserAddressResponseItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    var visibleItemPositions = mutableListOf<Int>()

    var selectedAddress = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = RvAddressBinding.inflate(
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

                binding.rbSelectaddress.isChecked = selectedAddress == position
                
                binding.tvName.text  = data.DelAddressName

                binding.tvAddress.text = "${data.DelAddress}\n${data.DelDistrict}\n${data.DelState}\n${data.DelPostCode}\nPhone : ${data.DelContactNo}."

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