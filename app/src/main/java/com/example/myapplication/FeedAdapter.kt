package com.example.myapplication

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_layout_image.view.*

class FeedAdapter(val context: Context, val feed: List<Post>) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {

        //link adapter to recyclerview cards
        return when (viewType){
            0 -> FeedViewHolderImage(LayoutInflater.from(context).inflate(R.layout.item_layout_image, parent, false))
            1 -> FeedViewHolderText(LayoutInflater.from(context).inflate(R.layout.item_layout_text, parent, false))
            else -> FeedViewHolderImage(LayoutInflater.from(context).inflate(R.layout.item_layout_image, parent, false))
        }

    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        //bind and combine data to the view
        val item = feed[position]
        holder.setData(item, position)
    }

    override fun getItemCount(): Int {
        //get number of items from Post items
        return feed.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (feed[position].imgUrl == null) {
            1
        } else {
            0
        }
    }

    abstract inner class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun setData(item: Post?, position: Int) {

        }
    }

    inner class FeedViewHolderImage(itemView: View) : FeedViewHolder(itemView) {
        override fun setData(item: Post?, position: Int) {
            if (item != null) {
                itemView.tvTitle.text = item.title
                itemView.tvText.text = item.text
            }

            if (item?.imgUrl != null) {
                Picasso.get()
                        .load(item.imgUrl)
                        .into(itemView.ivPic)
            }
        }
    }


    inner class FeedViewHolderText(itemView: View) : FeedViewHolder(itemView) {
        override fun setData(item: Post?, position: Int) {
            if (item != null) {
                itemView.tvTitle.text = item.title
                itemView.tvText.text = item.text
            }
        }
    }
}
