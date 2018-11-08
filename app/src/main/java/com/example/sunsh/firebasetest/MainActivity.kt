package com.example.sunsh.firebasetest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.resist_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_page -> change()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        resist_btn.setOnClickListener {
            if (!rens_name_text.text.isBlank() and !wide_text.text.isBlank() and !tele_text.text.isBlank() and !price_text.text.isBlank())
                resist()
        }
    }

    fun resist() {
        // Access a Cloud Firestore instance from your Activity

        val db = FirebaseFirestore.getInstance()

        // Create a new user with a first and last names
        val rens = HashMap<String, Any>().apply {
            put("rens_name", rens_name_text.text.toString())
            put("wide_edge", wide_text.text.toString())
            put("tele_edge", tele_text.text.toString())
            put("price", price_text.text.toString())
        }

        // Add a new document with a generated ID
        db.collection("rens")
                .add(rens)
                .addOnSuccessListener { documentReference ->
                    Log.d("MainActivity", "DocumentSnapshot added with ID: " + documentReference.id)
                    Toast.makeText(this, "登録完了しました", Toast.LENGTH_LONG).show();
                }
                .addOnFailureListener { e -> Log.w("MainActivity", "Error adding document", e) }
    }

    fun change() {
        val intent = Intent(this, ListDisplay::class.java)
        startActivity(intent)
    }
}
