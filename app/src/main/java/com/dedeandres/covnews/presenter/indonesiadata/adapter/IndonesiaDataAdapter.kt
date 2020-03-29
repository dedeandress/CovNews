package com.dedeandres.covnews.presenter.indonesiadata.adapter

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dedeandres.covnews.R
import com.dedeandres.covnews.presenter.dashboard.entity.IndonesiaDataResult
import com.dedeandres.covnews.util.ext.avoidDoubleClicks
import com.dedeandres.covnews.util.ext.hide
import com.dedeandres.covnews.util.ext.show
import kotlinx.android.synthetic.main.item_indonesia_data.view.*


class IndonesiaDataAdapter : RecyclerView.Adapter<IndonesiaDataAdapter.ViewHolder>() {

    private val items = ArrayList<IndonesiaDataResult>()
    private var selectedItem: Int = UNSELECTED

    private var recyclerView: RecyclerView? = null

    fun setRecyclerView(recyclerView: RecyclerView?) {
        this.recyclerView = recyclerView
    }

    fun bind(items: List<IndonesiaDataResult>) {
        this.items.clear()
        if (items.isNotEmpty()) {
            this.items.addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: IndonesiaDataAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_indonesia_data, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        fun bind(indonesiaDataResult: IndonesiaDataResult) {
            view.tv_province_name.text = indonesiaDataResult.province
            view.tv_total_death_province.text = indonesiaDataResult.deaths
            view.tv_total_positive_province.text = indonesiaDataResult.positive
            view.tv_total_recovered_province.text = indonesiaDataResult.recovered
            view.avoidDoubleClicks()
            view.setOnClickListener {
                val holder =
                    recyclerView?.findViewHolderForAdapterPosition(selectedItem)
                if (holder != null) {
                    holder.itemView.iv_expand_province.setImageResource(R.drawable.ic_down_arrow)
                    TransitionManager.beginDelayedTransition(view.cv_province_data, AutoTransition())
                    holder.itemView.el_detail_province.hide()
                }

                val position = adapterPosition
                selectedItem = if (position == selectedItem) {
                    UNSELECTED
                } else {
                    view.iv_expand_province?.setImageResource(R.drawable.ic_up_arrow)
                    TransitionManager.beginDelayedTransition(view.cv_province_data, AutoTransition())
                    view.el_detail_province?.show()
                    position
                }
            }
        }

    }

    companion object{
        const val UNSELECTED = -1
    }

}