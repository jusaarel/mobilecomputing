package com.example.mobilecomputing

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.mobilecomputing.ui.login.AppDatabase
import java.text.SimpleDateFormat
import java.util.*

class EditReminder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_reminder)

        val confirm = findViewById<Button>(R.id.editConfirmButton)
        val NameTextView = findViewById<TextView>(R.id.editReminderName)
        val editTimeButton = findViewById<Button>(R.id.editTimeButton)
        val TimeTextView = findViewById<TextView>(R.id.TimeTextView)

        val nameOriginal = intent.getStringExtra("Name")

        var db = AppDatabase.getInstance(applicationContext)

        var dao = db.ReminderSQLDao()
        var reminder = dao.findByName(nameOriginal)

        val date = Date(reminder.reminder_time)
        val format = SimpleDateFormat("HH:mm | dd.MM.yyyy")

        TimeTextView.text = format.format(date)



        var time =  Calendar.getInstance().timeInMillis
        editTimeButton.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                TimeTextView.text = SimpleDateFormat("HH:mm | dd.MM.yyyy").format(cal.time)
                time = cal.timeInMillis
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        confirm.setOnClickListener {
            Log.d("ASD", "pressed confirm")
            AppDatabase.update(nameOriginal, NameTextView.text.toString(), time)
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()

        val confirm = findViewById<Button>(R.id.editConfirmButton)
        val NameTextView = findViewById<TextView>(R.id.editReminderName)
        val editTimeButton = findViewById<Button>(R.id.editTimeButton)
        val TimeTextView = findViewById<TextView>(R.id.TimeTextView)

        val nameOriginal = intent.getStringExtra("Name")

        NameTextView.text = nameOriginal
        var db = AppDatabase.getInstance(applicationContext)

        var dao = db.ReminderSQLDao()
        var reminder = dao.findByName(nameOriginal)

        val date = Date(reminder.reminder_time)
        val format = SimpleDateFormat("HH:mm | dd.MM.yyyy")

        TimeTextView.text = format.format(date)


        var day : Int = 0
        var year : Int = 0
        var month : Int = 0
        var minute : Int = 0
        var hour : Int = 0

        var myDay = 0
        var myHour = 0
        var myYear = 0
        var myMinute = 0
        var myMonth = 0

        var time =  Calendar.getInstance().timeInMillis
        editTimeButton.setOnClickListener {
            val cal = Calendar.getInstance()

            day = cal.get(Calendar.DAY_OF_MONTH)
            year = cal.get(Calendar.YEAR)
            month = cal.get(Calendar.MONTH)


            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.YEAR, myYear)
                cal.set(Calendar.MONTH, myMonth)
                cal.set(Calendar.DAY_OF_MONTH, myDay)
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                TimeTextView.text = SimpleDateFormat("HH:mm | dd.MM.yyyy").format(cal.time)
                time = cal.timeInMillis
            }

            val daySetListener = DatePickerDialog.OnDateSetListener { datePicker, year: Int, month: Int, dayOfMonth: Int ->
                myDay = dayOfMonth
                myYear = year
                myMonth = month

                val calendar: Calendar = Calendar.getInstance()
                hour = calendar.get(Calendar.HOUR)
                minute = calendar.get(Calendar.MINUTE)
                val timePickerDialog = TimePickerDialog(this@EditReminder, timeSetListener, hour, minute,
                    DateFormat.is24HourFormat(this))
                timePickerDialog.show()
            }

            val datePickerDialog = DatePickerDialog(this@EditReminder, daySetListener, year, month, day)
            datePickerDialog.show()


            //TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        confirm.setOnClickListener {
            AppDatabase.update(nameOriginal, NameTextView.text.toString(), time)
        }

    }


}