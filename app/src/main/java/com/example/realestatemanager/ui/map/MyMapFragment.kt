package com.example.realestatemanager.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.realestatemanager.R

class MyMapFragment : Fragment() {

    private lateinit var myMapViewModel: MyMapViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myMapViewModel =
            ViewModelProviders.of(this).get(MyMapViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_map, container, false)
        val textView: TextView = root.findViewById(R.id.text_map)

        myMapViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}