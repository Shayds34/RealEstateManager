package com.example.realestatemanager.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.realestatemanager.R
import com.example.realestatemanager.utils.DataSource
import com.example.realestatemanager.utils.GoodRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {
    private val myTag = "ListFragment"

    private lateinit var goodRecyclerAdapter: GoodRecyclerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(myTag, "onCreateView")
        val root = inflater.inflate(R.layout.fragment_list, container, false)
        configureRecyclerView(root)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(myTag, "onViewCreated")
    }

    private fun configureRecyclerView(root: View) {
        Log.d(myTag, "configureRecyclerView")

        // Fetch our data.
        val data = DataSource.createDataSet()

        root.recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            goodRecyclerAdapter =
                GoodRecyclerAdapter(
                    context,
                    data
                )
            adapter =
                GoodRecyclerAdapter(
                    context,
                    data
                )
        }
    }
}