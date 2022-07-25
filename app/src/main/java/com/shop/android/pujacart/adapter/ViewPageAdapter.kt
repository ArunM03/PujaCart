package com.shop.android.pujacart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.shop.android.pujacart.data.banner.MainBannerResponseItem
import com.shop.android.pujacart.databinding.VpImageBinding


class ViewPageAdapter(val glide : RequestManager) : RecyclerView.Adapter<ViewPageAdapter.PlaylistViewHolder>() {
    
    inner class PlaylistViewHolder(val binding : VpImageBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<MainBannerResponseItem>(){

        override fun areItemsTheSame(
            oldItem: MainBannerResponseItem,
            newItem: MainBannerResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: MainBannerResponseItem,
            newItem: MainBannerResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
        
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    var playlist: List<MainBannerResponseItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private var onItemClickListener: ((MainBannerResponseItem) -> Unit)? = null
    private var onItemClickListener2: ((MainBannerResponseItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (MainBannerResponseItem) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnItemClickListener2(listener: (MainBannerResponseItem) -> Unit) {
        onItemClickListener2 = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPageAdapter.PlaylistViewHolder {
        val binding = VpImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlaylistViewHolder(binding)
    }



    override fun getItemCount(): Int {
        return playlist.size
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val data = playlist[position]
        holder.itemView.apply {

                with(holder) {

                    glide.load(data.BannerImage).into(binding.ivImage)

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