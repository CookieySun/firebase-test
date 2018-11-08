package com.example.sunsh.firebasetest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_list_display.*


class ListDisplay : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Access a Cloud Firestore instance from your Activity
        val db = FirebaseFirestore.getInstance()


        val names = mutableListOf<String>()
        val price = mutableListOf<String>()

        db.collection("rens")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            names.add(document.getString("rens_name")!!)
                            price.add(document.getString("price")!!)
                        }
                        val recyclerView = recycler_list
                        val adapter = ViewAdapter(createDataList(names, price), object : ViewAdapter.ListListener {
                            override fun onClickRow(tappedView: View, rowModel: RowModel) {
                            }
                        })

                        recyclerView.setHasFixedSize(true)
                        recyclerView.layoutManager = GridLayoutManager(this, 2)
                        recyclerView.adapter = adapter
                    } else {
                        Log.w("loglog", "Error getting documents.", task.exception)
                    }
                }
        setContentView(R.layout.activity_list_display)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.resist_page -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createDataList(names: MutableList<String>, price: MutableList<String>): List<RowModel> {
        val dataList = mutableListOf<RowModel>()

        for ((n, i) in names.withIndex()) {
            val data: RowModel = RowModel().also {
                it.title = i
                it.detail = "￥" + price[n]
            }
            dataList.add(data)
        }
        return dataList
    }
}
