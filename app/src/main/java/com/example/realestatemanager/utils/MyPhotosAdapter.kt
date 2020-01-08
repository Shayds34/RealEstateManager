package com.example.realestatemanager.utils

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.realestatemanager.R
import com.example.realestatemanager.controllers.EditActivity
import com.example.realestatemanager.models.Property
import kotlinx.android.synthetic.main.photos_gallery_item_edit.view.*

class MyPhotosAdapter(private val context: Context, private var property: Property) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var myTag = "MyPhotosAdapter"

    private lateinit var photosToBeDeleted : ArrayList<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        photosToBeDeleted = ArrayList()
        return PhotoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.photos_gallery_item_edit,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is PhotoViewHolder -> {
                holder.bind(property.photos[position])
                holder.setListeners()
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d(myTag, "getItemCount" + property.photos.size)
        return property.photos.size
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var photo: String

        fun bind(photo: String){
            this.photo = photo

            itemView.text_gallery.text = "Photo ${property.photos.indexOf(photo) + 1}"

            //region {Glide}
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            if (property.photos.size > 0){
                Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(photo)
                    .centerCrop()
                    .into(itemView.photo_gallery)
            }
            //endregion
        }

        fun setListeners() {
            itemView.button_delete.setOnClickListener {

                // Prepare ArrayList of photos to be deleted when Edit Button is triggered.
                photosToBeDeleted.add(photo)

                Log.d(myTag, "Photos to be deleted $photosToBeDeleted")
                val edit : EditActivity = context as EditActivity
                edit.updateProperty(photosToBeDeleted)

                property.photos.remove(photo)
                notifyDataSetChanged()
            }
        }
    }
}