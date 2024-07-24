package com.hk.listeddashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hk.listeddashboard.R
import com.hk.listeddashboard.models.CardItem

class CardAdapter() :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private var cardList : List<CardItem> = arrayListOf()

    fun setList(data: List<CardItem>){
        cardList=data
        notifyDataSetChanged()
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewSubtitle: TextView = itemView.findViewById(R.id.textViewSubtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = cardList[position]
        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textViewTitle.text = currentItem.title
        holder.textViewSubtitle.text = currentItem.subtitle
    }

    override fun getItemCount(): Int = cardList.size
}
