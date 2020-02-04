package com.example.realestatemanager.utils

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.realestatemanager.R
import com.example.realestatemanager.models.Property
import kotlinx.android.synthetic.main.good_list_item.view.*

class PropertiesAdapter(private val context: Context, private var items: List<Property>) : RecyclerView.Adapter<ViewHolder>() {
    private var myTag = "GoodRecyclerAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.good_list_item_2, parent, false)

        val orientation = context.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val params = (view.layoutParams)
            params.width = (parent.measuredWidth)
            params.height = params.width
            view.layoutParams = params
        } else {
            val params = (view.layoutParams)
            params.width = (parent.measuredWidth / 2)
            params.height = params.width
            view.layoutParams = params
        }

//        val params = (view.layoutParams)
//        params.width = (parent.measuredWidth / 2)
//        params.height = params.width
//        view.layoutParams = params

        return GoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(myTag, "onBindViewHolder")
        when(holder){
            is GoodViewHolder -> {
                holder.bind(items[position])
                holder.setListeners()
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d(myTag, "getItemCount" + items.size)
        return items.size
    }

    inner class GoodViewHolder (itemView: View) : ViewHolder(itemView){
        lateinit var property: Property

        // TODO To have user's preferences regarding € or $, for example.
        private val preferences = itemView.context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        private var currency = preferences.getBoolean("currency", true)

        private val goodType = itemView.good_type
        private val goodPlace = itemView.good_place
        private val goodPrice = itemView.good_price
        private val goodImage = itemView.good_image

        fun bind(property: Property){
            this.property = property

            goodType.text = property.type
            goodPlace.text = property.neighborhood.plus(", ").plus(property.city)

            //region {Glide}
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            if (property.photos.size > 0){
                Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(property.photos[0])
                    .centerCrop()
                    .into(goodImage)
            }
            //endregion
        }

        fun setListeners() {
            itemView.setOnClickListener {
                val communicator = context as Communicator
                communicator.displayDetailsOfGood(property)
            }
        }
    }

    fun updateList(items: List<Property>) {
        this.items = items
        notifyDataSetChanged()
    }
}