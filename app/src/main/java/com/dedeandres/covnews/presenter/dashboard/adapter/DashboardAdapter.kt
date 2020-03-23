package com.dedeandres.covnews.presenter.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.blongho.country_data.World
import com.dedeandres.covnews.R
import com.dedeandres.covnews.presenter.dashboard.entity.GlobalDataResult
import com.dedeandres.covnews.util.ext.avoidDoubleClicks
import kotlinx.android.synthetic.main.item_country_data.view.*
import net.cachapa.expandablelayout.ExpandableLayout
import timber.log.Timber


class DashboardAdapter : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    private val items = ArrayList<GlobalDataResult>()
    private var selectedItem: Int = UNSELECTED

    private var recyclerView: RecyclerView? = null

    fun setRecyclerView(recyclerView: RecyclerView?) {
        this.recyclerView = recyclerView
    }

    fun bind(items: List<GlobalDataResult>) {
        this.items.clear()
        if (items.isNotEmpty()) {
            this.items.addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DashboardAdapter.DashboardViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_country_data, parent, false)
        return DashboardViewHolder(view)
    }

    inner class DashboardViewHolder(private val view: View): RecyclerView.ViewHolder(view), ExpandableLayout.OnExpansionUpdateListener {

        init {
            view.el_detail.setInterpolator(OvershootInterpolator())
            view.el_detail.setOnExpansionUpdateListener(this)
        }

        fun bind(globalDataResult: GlobalDataResult) {
            view.tv_country.text = globalDataResult.country
            val flag = World.getFlagOf(globalDataResult.country)
            view.iv_flag.setImageResource(flag)
            view.tv_total_death.text = globalDataResult.deaths
            view.tv_total_positive.text = globalDataResult.confirmed
            view.tv_total_recovered.text = globalDataResult.recovered
            view.avoidDoubleClicks()
            view.iv_expand.setOnClickListener {
                Timber.d("$globalDataResult Click")
                val holder =
                    recyclerView?.findViewHolderForAdapterPosition(selectedItem)
                if (holder != null) {
                    holder.itemView.iv_expand.setImageResource(R.drawable.ic_down_arrow)
                    holder.itemView.el_detail.collapse()
                }

                val position = adapterPosition
                selectedItem = if (position == selectedItem) {
                    UNSELECTED
                } else {
                    view.iv_expand?.setImageResource(R.drawable.ic_up_arrow)
                    view.el_detail?.expand()
                    position
                }
            }
        }

        override fun onExpansionUpdate(expansionFraction: Float, state: Int) {
            if (state == ExpandableLayout.State.EXPANDING) {
                recyclerView?.smoothScrollToPosition(adapterPosition)
            }
        }
    }

    companion object{
        const val UNSELECTED = -1
    }

}