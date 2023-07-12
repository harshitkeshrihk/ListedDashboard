package com.hk.listeddashboard.adapter

import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hk.listeddashboard.R
import com.hk.listeddashboard.databinding.LinkItemBinding
import com.hk.listeddashboard.models.TopLink
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class LinksAdapter(): RecyclerView.Adapter<LinksAdapter.VH>() {

    private var list: List<TopLink> = arrayListOf()

    fun setList(data: List<TopLink>){
        list=data
        notifyDataSetChanged()
    }

    var onItemClick: ((TopLink) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH  = VH(
        LinkItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        val link = list[position]
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(link)
        }
        holder.onBind(link,position)

    }

    override fun getItemCount(): Int {
        return list.size
    }
    class VH(val binding: LinkItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(link: TopLink, position: Int){
            binding.titleTv.text = link.title
            binding.dateTv.text = link.created_at
            binding.countTv.text = link.total_clicks.toString()
            binding.clicks.text = "Clicks"
            binding.linksTv.text = link.smart_link

            if (link.original_image!=null){
                Picasso.get()
                    .load(link.original_image)
                    .into(binding.appCompatImageView)
            }else{
                binding.appCompatImageView.setImageResource(R.drawable.img)
            }

        }

//        fun getDateFromCreatedAt(createdAt: String): String {
//            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
//            val outputFormat = SimpleDateFormat("dd MMM yyyy")
//
//            val date: Date = inputFormat.parse(createdAt)
//            return outputFormat.format(date)
//        }
    }


}