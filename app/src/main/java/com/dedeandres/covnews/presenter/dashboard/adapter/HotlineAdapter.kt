package com.dedeandres.covnews.presenter.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dedeandres.covnews.R
import com.dedeandres.covnews.data.dashboard.entity.Hotline
import com.dedeandres.covnews.presenter.dashboard.entity.NewsResult
import com.dedeandres.covnews.util.ext.avoidDoubleClicks
import kotlinx.android.synthetic.main.item_hotline.view.*
import kotlinx.android.synthetic.main.item_news.view.*


class HotlineAdapter : RecyclerView.Adapter<HotlineAdapter.ViewHolder>() {

    private val items = ArrayList<Hotline>()
    private var onItemClickListener: OnItemClickListener? = null

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun bind(items: List<Hotline>) {
        this.items.clear()
        if (items.isNotEmpty()) {
            this.items.addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HotlineAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hotline, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(hotline: Hotline) {
            view.tv_name.text = hotline.name
            view.iv_logo.setImageResource(hotline.logo)
            view.tv_phone_number.text = hotline.phoneNumber
            itemView.setOnClickListener {
                it?.avoidDoubleClicks()
                onItemClickListener?.onItemClick(hotline)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(hotline: Hotline)
    }

}