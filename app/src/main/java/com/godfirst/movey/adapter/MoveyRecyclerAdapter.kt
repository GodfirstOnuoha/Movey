package com.godfirst.movey.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.godfirst.movey.R
import com.godfirst.movey.models.Movey

class MoveyRecyclerAdapter(private val moveyList: List<Movey>) :
    RecyclerView.Adapter<MoveyRecyclerAdapter.MoveyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoveyViewHolder {
        return MoveyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movey_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoveyViewHolder, position: Int) {
        val movey = moveyList[position]
        holder.bind(movey)
    }

    override fun getItemCount(): Int = moveyList.size

    class MoveyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val moveyImage: ImageView = itemView.findViewById(R.id.movey_image)
        private val titleText: TextView = itemView.findViewById(R.id.title_text)
        private val overviewText: TextView = itemView.findViewById(R.id.overview_text)
        private val dateText: TextView = itemView.findViewById(R.id.date_text)

        fun bind(movey: Movey) {
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500/${movey.poster_path}")
                .placeholder(R.drawable.tmdb)
                .into(moveyImage)
            titleText.text = movey.title
            overviewText.text = movey.overview
            dateText.text = movey.release_date
        }
    }
}