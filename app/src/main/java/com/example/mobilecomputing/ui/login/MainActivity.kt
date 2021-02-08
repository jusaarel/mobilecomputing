package com.example.mobilecomputing.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mobilecomputing.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val logout = findViewById<Button>(R.id.logout)
        val listview = findViewById<ListView>(R.id.listview)

        val listlist = ArrayList<String>()

        listlist.add("Reminder 1")
        listlist.add("Reminder 2")
        listlist.add("Reminder 3")
        listlist.add("Reminder 4")
        listlist.add("Reminder 5")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listlist)
        listview.adapter = adapter

        logout.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            ContextCompat.startActivity(this@MainActivity, intent, null)
        }
    }
}