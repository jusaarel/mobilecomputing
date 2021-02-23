package com.example.mobilecomputing.ui.login

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.room.Room
import com.example.mobilecomputing.R
import java.text.SimpleDateFormat
import java.util.*

class ReminderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)


        val reminderText = findViewById<EditText>(R.id.reminderName)
        val mPickTimeBtn = findViewById<Button>(R.id.pickTime)
        val textView     = findViewById<TextView>(R.id.timeView)
        val addBtn = findViewById<Button>(R.id.add);

        var time =  Calendar.getInstance().time
        mPickTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textView.text = SimpleDateFormat("HH:mm").format(cal.time)
                time = cal.time
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        var reminderName : String;
        reminderName = "laadidaa"

        reminderText.afterTextChanged {
            reminderName = reminderText.text.toString()
        }
        addBtn.setOnClickListener {

            var reminder = ReminderSQL(0, reminderName)

            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()

            val userDao = db.ReminderSQLDao()
            userDao.insertAll(reminder)
            Log.d("REMINDER", reminder.message);
        }


    }
}