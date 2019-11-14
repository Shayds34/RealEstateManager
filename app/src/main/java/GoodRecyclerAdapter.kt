package com.example.realestatemanager

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.realestatemanager.com.example.realestatemanager.models.Good
import kotlinx.android.synthetic.main.good_list_item.view.*
import java.text.DecimalFormat
import java.text.NumberFormat

class GoodRecyclerAdapter(private var items: List<Good>, val clickListener: (Good) -> Unit) : RecyclerView.Adapter<ViewHolder>() {
    private var myTag = "GoodRecyclerAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return GoodViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.good_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(myTag, "onBindViewHolder")
        when(holder){
            is GoodViewHolder -> {
                holder.bind(items[position], clickListener)
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d(myTag, "getItemCount" + items.size)
        return items.size
    }

    class GoodViewHolder constructor(
        itemView: View
    ) : ViewHolder(itemView){

        // TODO To have user's preferences regarding € or $, for example.
        val preferences = itemView.context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        var currency = preferences.getBoolean("currency", true)

        private val goodType = itemView.good_type
        private val goodPlace = itemView.good_place
        private val goodPrice = itemView.good_price
//        val goodImage = itemView.good_image

        fun bind(good: Good, clickListener: (Good) -> Unit){
            goodType.text = good.type
            goodPlace.text = good.place

            if (currency){
                val formatter: NumberFormat = DecimalFormat("#,###")
                val currentPrice = formatter.format(good.price)
                goodPrice.text = currentPrice + " €"
            }


            // TODO Glide
//            val requestOptions = RequestOptions()
//                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.drawable.ic_launcher_background)
//
//            Glide.with(itemView.context)
//                .applyDefaultRequestOptions(requestOptions)
//                .load(good.photos)
//                .into(goodImage)

            itemView.setOnClickListener { clickListener(good) }
        }
    }

}