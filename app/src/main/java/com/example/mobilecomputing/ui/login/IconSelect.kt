package com.example.mobilecomputing.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.example.mobilecomputing.R

class IconSelect : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icon_select)

        val icon1 = findViewById<ImageButton>(R.id.imageButton)
        val icon2 = findViewById<ImageButton>(R.id.imageButton4)

        icon1.setOnClickListener {

            val intent = Intent(this@IconSelect, ReminderActivity::class.java)
            intent.putExtra("resId", R.drawable.info)
            ContextCompat.startActivity(this@IconSelect, intent, null)
        }

        icon2.setOnClickListener {

            val intent = Intent(this@IconSelect, ReminderActivity::class.java)
            intent.putExtra("resId", R.drawable.warning)
            ContextCompat.startActivity(this@IconSelect, intent, null)
        }

    }
}