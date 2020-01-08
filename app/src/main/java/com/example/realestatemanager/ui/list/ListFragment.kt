package com.example.realestatemanager.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.realestatemanager.R
import com.example.realestatemanager.models.Property
import com.example.realestatemanager.utils.PropertiesAdapter
import com.example.realestatemanager.utils.RealEstateDBHelper
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    //#region {Initialization}
    private val myTag = "ListFragment"
    private lateinit var adapter: PropertiesAdapter
    private lateinit var data : ArrayList<Property>
    //endregion

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(myTag, "onCreateView")
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView(view)
        Log.d(myTag, "onViewCreated")
    }

    private fun configureRecyclerView(view: View) {
        Log.d(myTag, "configureRecyclerView")

        // Fetch our data.
        val db = RealEstateDBHelper(view.context, null)
        data = db.getListOfProperties()

        recycler_view.layoutManager = GridLayoutManager(context, 2)
        adapter = PropertiesAdapter(view.context, data)
        adapter.notifyDataSetChanged()
        recycler_view.adapter = adapter
//        recycler_view.addItemDecoration(DividerItemDecoration(recycler_view.context, DividerItemDecoration.VERTICAL))
    }

    override fun onResume() {
        super.onResume()
        updateAdapter()
    }

    override fun onStart() {
        super.onStart()
        updateAdapter()
    }

    private fun updateAdapter(){
        val db = RealEstateDBHelper(activity!!.applicationContext, null)
        data = db.getListOfProperties()
        adapter.updateList(data)
    }
}