package com.example.realestatemanager.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.realestatemanager.GoodRecyclerAdapter
import com.example.realestatemanager.R
import com.example.realestatemanager.com.example.realestatemanager.DataSource
import com.example.realestatemanager.com.example.realestatemanager.models.Good
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {
    private val myTag = "ListFragment"

    private lateinit var goodRecyclerAdapter: GoodRecyclerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(myTag, "onCreateView")
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(myTag, "onViewCreated")

        configureRecyclerView()
    }

    private fun goodItemClicked(good: Good){
        Toast.makeText(context, "Clicked on ${good.place}", Toast.LENGTH_LONG).show()
    }

    private fun configureRecyclerView() {
        Log.d(myTag, "configureRecyclerVie")

        val data = DataSource.createDataSet()

        goods_list.apply {
            layoutManager = LinearLayoutManager(context)
            goodRecyclerAdapter = GoodRecyclerAdapter(data) { good: Good -> goodItemClicked(good)}
            adapter = GoodRecyclerAdapter(data) { good: Good -> goodItemClicked(good)}
        }
    }
}