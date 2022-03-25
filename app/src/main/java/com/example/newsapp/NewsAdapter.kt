package com.example.newsapp

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.zip.Inflater

class NewsAdapter( private val listener:NewsItemClicked):RecyclerView.Adapter<NewsViewHolder> (){

    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.items_news,parent,false)
        val viewHolder=NewsViewHolder(view)
        view.setOnClickListener {
            listener.onItemClicked(items [viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItems =items[position]
        holder.titleView.text= currentItems.title
        holder.author.text=currentItems.author
        Glide.with(holder.itemView.context).load(currentItems.imageUrl).into(holder.image)
    }

    fun updateNews(updatedNews: ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val titleView: TextView =itemView.findViewById(R.id.title)
    val image: ImageView=itemView.findViewById(R.id.image)
    val author: TextView=itemView.findViewById(R.id.author)

}
interface NewsItemClicked{
    fun onItemClicked(item:News)
}