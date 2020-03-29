package com.dedeandres.covnews.presenter.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dedeandres.covnews.R
import com.dedeandres.covnews.presenter.dashboard.entity.NewsResult
import com.dedeandres.covnews.util.ext.avoidDoubleClicks
import kotlinx.android.synthetic.main.item_news.view.*


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val items = ArrayList<NewsResult>()
    private var onItemClickListener: OnItemClickListener? = null

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun bind(items: List<NewsResult>) {
        this.items.clear()
        if (items.isNotEmpty()) {
            this.items.addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(newsResult: NewsResult) {
            view.tv_title.text = newsResult.title
            view.tv_source.text = newsResult.sources
            Glide.with(view)
                .load(newsResult.urlToImage)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(view.iv_news_image)
            itemView.setOnClickListener {
                it?.avoidDoubleClicks()
                onItemClickListener?.onItemClick(newsResult)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(newsResult: NewsResult)
    }

}