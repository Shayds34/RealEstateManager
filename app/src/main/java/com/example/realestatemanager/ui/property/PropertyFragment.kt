package com.example.realestatemanager.ui.property

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.realestatemanager.R
import com.example.realestatemanager.models.Property
import kotlinx.android.synthetic.main.fragment_good.*
import kotlinx.android.synthetic.main.photos_gallery_item.view.*

class PropertyFragment : Fragment() {

    companion object {
        fun newInstance(property: Property): PropertyFragment {
            val fragment = PropertyFragment()
            val args = Bundle()
            args.putParcelable("property", property)
            fragment.arguments = args
            return fragment
        }
    }

    private var currentProperty: Property? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_good, container, false)
        val gallery: LinearLayout = root.findViewById(R.id.photos_gallery)

        // If activity recreated (such as from screen rotation), restore
        // the previous property selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            currentProperty = savedInstanceState.get("property") as Property
        }

        // Inflate photos gallery
        for (i in 1..6){
            val view = inflater.inflate(R.layout.photos_gallery_item, gallery, false)
            view.text_gallery.text = "Photo $i"
            gallery.addView(view)
        }

        // Inflate the layout for this fragment.
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("PropertyFragment", "onViewCreated")
    }

    override fun onStart() {
        super.onStart()

        // During start, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the property info
        val bundle: Bundle? = arguments
        if (bundle != null){
            // Set property based on arguments passed in
            displayDetailsOfGood(bundle.get("property") as Property)
        }
    }

    fun displayDetailsOfGood(property : Property){
        Log.d("PropertyFragment", "Values: ${property.type}, ${property.description}")
        tv_description.text = property.description
        this.currentProperty = property

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save current currentProperty selection in case we need to recreate the fragment
        outState.putParcelable("currentProperty", currentProperty)
    }
}