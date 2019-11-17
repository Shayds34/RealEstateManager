package com.example.realestatemanager.utils

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.realestatemanager.R
import com.example.realestatemanager.models.Property
import kotlinx.android.synthetic.main.good_list_item.view.*
import java.text.DecimalFormat
import java.text.NumberFormat

class GoodRecyclerAdapter(private val context: Context, private var items: List<Property>) : RecyclerView.Adapter<ViewHolder>() {
    private var myTag = "GoodRecyclerAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return GoodViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.good_list_item,
                parent,
                false
            )
        )
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

        private var pos: Int = 0
        lateinit var property: Property

        // TODO To have user's preferences regarding € or $, for example.
        private val preferences = itemView.context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        private var currency = preferences.getBoolean("currency", true)

        private val goodType = itemView.good_type
        private val goodPlace = itemView.good_place
        private val goodPrice = itemView.good_price
//        val goodImage = itemView.good_image

        fun bind(property: Property){

            this.property = property

            goodType.text = property.type
            goodPlace.text = property.place

            if (currency){
                val formatter: NumberFormat = DecimalFormat("#,###")
                val currentPrice = formatter.format(property.price)
                goodPrice.text = itemView.context.resources.getString(R.string.currencyDollars).plus(currentPrice)
            }

            //region {Glide}
            // TODO Glide
//            val requestOptions = RequestOptions()
//                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.drawable.ic_launcher_background)
//
//            Glide.with(itemView.context)
//                .applyDefaultRequestOptions(requestOptions)
//                .load(currentProperty.photos)
//                .into(goodImage)
            //endregion
        }

        fun setListeners() {
            itemView.setOnClickListener {
                val communicator = context as Communicator
                communicator.displayDetailsOfGood(property)
                Log.d("Tag", "Values type ${property.type} , ${property.description}")
            }
        }
    }
}