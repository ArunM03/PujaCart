package com.shop.android.pujacart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.shop.android.pujacart.data.cart.GetGuestCartItemResponseItem
import com.shop.android.pujacart.databinding.RvCartBinding
import com.shop.android.pujacart.databinding.RvCategoryBinding


class CartAdapter(val glide : RequestManager)  : RecyclerView.Adapter<CartAdapter.ProductViewHolder>()  {

    class ProductViewHolder(val binding : RvCartBinding) : RecyclerView.ViewHolder(binding.root)

    private var onItemClickListener: ((GetGuestCartItemResponseItem) -> Unit)? = null

    fun setOnItemClickListener(position: (GetGuestCartItemResponseItem) -> Unit) {
        onItemClickListener = position
    }

    private var onItemClickListenerForDelete: ((GetGuestCartItemResponseItem) -> Unit)? = null

    private var onItemClickListenerForQuantity: ((GetGuestCartItemResponseItem) -> Unit)? = null

    fun setOnItemClickListenerForDelete(position: (GetGuestCartItemResponseItem) -> Unit) {
        onItemClickListenerForDelete = position
    }

    fun setOnItemClickListenerForQuantity(position: (GetGuestCartItemResponseItem) -> Unit) {
        onItemClickListenerForQuantity = position
    }

    private val diffCallback = object : DiffUtil.ItemCallback<GetGuestCartItemResponseItem>() {

        override fun areContentsTheSame(oldItem: GetGuestCartItemResponseItem, newItem: GetGuestCartItemResponseItem): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: GetGuestCartItemResponseItem, newItem: GetGuestCartItemResponseItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }


    private val differ = AsyncListDiffer(this, diffCallback)

    var categoryList : List<GetGuestCartItemResponseItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    var visibleItemPositions = mutableListOf<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = RvCartBinding.inflate(
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

                var quantity = data.Quantity

                glide.load(data.ProductImage).into(binding.ivProduct)

                binding.tvProductname.text = data.ProductName

                binding.tvPrice.text =  "₹${data.UnitPrice}"

                binding.tvTotalPrice.text =  "₹${data.TotalPrice}"

                binding.tvSize.text = "Size : ${data.PSize}"

                binding.edQuantity.text = data.Quantity.toString()

                binding.ibAdd.setOnClickListener {
                    quantity++
                    onItemClickListenerForQuantity?.let {
                            click ->
                        click(data.apply {
                            this.Quantity = quantity
                        })
                    }
                }

                binding.ibMinus.setOnClickListener {
                    if(quantity > 1){
                        quantity--
                        onItemClickListenerForQuantity?.let {
                                click ->
                            click(data.apply {
                                this.Quantity = quantity
                            })
                        }
                    }else{
                        Toast.makeText(context, "Can't able to reduce the quantity", Toast.LENGTH_SHORT).show()
                    }
                }


                binding.ibDelete.setOnClickListener {
                    onItemClickListenerForDelete?.let {
                            click ->
                        click(data)
                    }
                }

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