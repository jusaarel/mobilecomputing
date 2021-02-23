package com.example.mobilecomputing.ui.login

import MyListViewItem
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
import androidx.room.Room
import com.example.mobilecomputing.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val userDao = db.ReminderSQLDao()

        var Reminder = ReminderSQL(2,"laadidaa")



        var Reminders : List<ReminderSQL>


        Reminders = userDao.getAll()



        val intent = Intent(this@MainActivity, ReminderActivity::class.java)


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { ContextCompat.startActivity(this@MainActivity, intent, null) }

        val logout = findViewById<Button>(R.id.logout)
        val listview = findViewById<ListView>(R.id.listview)

        val listlist = ArrayList<String>()

        for (reminder in Reminders)
        {
            listlist.add(reminder.message ?: "default")
        }


        val adapter = MyListViewItem(this, listlist)
        //val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listlist)
        listview.adapter = adapter

        logout.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            ContextCompat.startActivity(this@MainActivity, intent, null)
        }
    }

    override fun onResume()
    {
        super.onResume()
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        val userDao = db.ReminderSQLDao()

        var Reminders : List<ReminderSQL>


        Reminders = userDao.getAll()

        val listview = findViewById<ListView>(R.id.listview)

        val listlist = ArrayList<String>()

        for (reminder in Reminders)
        {
            listlist.add(reminder.message ?: "default")
        }


        val adapter = MyListViewItem(this, listlist)
        //val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listlist)
        listview.adapter = adapter
    }
}