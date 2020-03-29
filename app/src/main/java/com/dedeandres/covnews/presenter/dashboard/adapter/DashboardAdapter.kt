package com.dedeandres.covnews.presenter.dashboard.adapter

import android.net.Uri
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.blongho.country_data.World
import com.bumptech.glide.Glide
import com.dedeandres.covnews.R
import com.dedeandres.covnews.presenter.dashboard.entity.GlobalDataResult
import com.dedeandres.covnews.util.ext.avoidDoubleClicks
import com.dedeandres.covnews.util.ext.hide
import com.dedeandres.covnews.util.ext.show
import kotlinx.android.synthetic.main.item_country_data.view.*
import timber.log.Timber
import java.lang.StringBuilder


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

    inner class DashboardViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(globalDataResult: GlobalDataResult) {
            view.tv_country.text = globalDataResult.country
            Glide.with(view)
                .load(getFlagUrl(globalDataResult.country))
                .placeholder(R.drawable.ic_flag)
                .into(view.iv_flag)
            view.tv_total_death.text = globalDataResult.deaths
            view.tv_total_positive.text = globalDataResult.confirmed
            view.tv_total_recovered.text = globalDataResult.recovered
            view.avoidDoubleClicks()
            view.setOnClickListener {
                Timber.d("$globalDataResult Click")
                val holder =
                    recyclerView?.findViewHolderForAdapterPosition(selectedItem)
                if (holder != null) {
                    holder.itemView.iv_expand.setImageResource(R.drawable.ic_down_arrow)
                    TransitionManager.beginDelayedTransition(view.cv_country_data, AutoTransition())
                    holder.itemView.el_detail.hide()
                }

                val position = adapterPosition
                selectedItem = if (position == selectedItem) {
                    UNSELECTED
                } else {
                    view.iv_expand?.setImageResource(R.drawable.ic_up_arrow)
                    TransitionManager.beginDelayedTransition(view.cv_country_data, AutoTransition())
                    view.el_detail?.show()
                    position
                }
            }
        }

        private fun getFlagUrl(countryName: String): Uri {
            val countryCode = World.getCountryFrom(countryName)?.currency?.country
            Timber.d("countryCode: $countryCode")
            return Uri.parse(COUNTRY_FLAG_BASE_URL).buildUpon().appendPath(countryCode).appendPath(
                COUNTRY_FLAG_STYLE).appendPath(COUNTRY_FLAG_SIZE).build()
        }

    }

    companion object{
        const val COUNTRY_FLAG_BASE_URL = "https://www.countryflags.io/"
        const val COUNTRY_FLAG_STYLE = "flat"
        const val COUNTRY_FLAG_SIZE= "64.png"
        const val UNSELECTED = -1
    }

}