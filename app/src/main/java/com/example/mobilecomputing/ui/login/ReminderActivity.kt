package com.example.mobilecomputing.ui.login

import android.app.DatePickerDialog
import android.app.PendingIntent.getActivity
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
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
        val pickIcon = findViewById<Button>(R.id.pickIcon);

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

        pickIcon.setOnClickListener {

            val intent = Intent(this@ReminderActivity, IconSelect::class.java)
            ContextCompat.startActivity(this@ReminderActivity, intent, null)
        }

        var time =  Calendar.getInstance().timeInMillis
        mPickTimeBtn.setOnClickListener {
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
                textView.text = SimpleDateFormat("HH:mm | dd.MM.yyyy").format(cal.time)
                time = cal.timeInMillis
            }

            val daySetListener = DatePickerDialog.OnDateSetListener { datePicker, year: Int, month: Int, dayOfMonth: Int ->
                myDay = dayOfMonth
                myYear = year
                myMonth = month

                val calendar: Calendar = Calendar.getInstance()
                hour = calendar.get(Calendar.HOUR)
                minute = calendar.get(Calendar.MINUTE)
                val timePickerDialog = TimePickerDialog(this@ReminderActivity, timeSetListener, hour, minute,
                    DateFormat.is24HourFormat(this))
                timePickerDialog.show()
            }

            val datePickerDialog = DatePickerDialog(this@ReminderActivity, daySetListener, year, month, day)
            datePickerDialog.show()


            //TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        var reminderName : String;
        reminderName = "laadidaa"

        reminderText.afterTextChanged {
            reminderName = reminderText.text.toString()
        }
        addBtn.setOnClickListener {

            var reminder = ReminderSQL(0, reminderName, reminder_time = time, location_x = null, location_y = null, reminder_seen = null, iconId = null)

            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()

            val userDao = db.ReminderSQLDao()
            userDao.insertAll(reminder)
            Log.d("REMINDER", reminder.message);
        }


    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_reminder)
        val imageView: ImageView = findViewById(R.id.imageViewIcon)
        val bundle: Bundle? = intent.extras

        var resId : Int? = null
        if(bundle != null)
        {
            resId = bundle.getInt("resId")
            Log.d("RESOURCE ID", resId.toString())
            imageView.setImageResource(resId)
        }



        val reminderText = findViewById<EditText>(R.id.reminderName)
        val mPickTimeBtn = findViewById<Button>(R.id.pickTime)
        val textView     = findViewById<TextView>(R.id.timeView)
        val addBtn = findViewById<Button>(R.id.add);
        val pickIcon = findViewById<Button>(R.id.pickIcon);

        pickIcon.setOnClickListener {

            val intent = Intent(this@ReminderActivity, IconSelect::class.java)
            ContextCompat.startActivity(this@ReminderActivity, intent, null)
        }

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
        mPickTimeBtn.setOnClickListener {
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
                textView.text = SimpleDateFormat("HH:mm | dd.MM.yyyy").format(cal.time)
                time = cal.timeInMillis
            }

            val daySetListener = DatePickerDialog.OnDateSetListener { datePicker, year: Int, month: Int, dayOfMonth: Int ->
                myDay = dayOfMonth
                myYear = year
                myMonth = month

                val calendar: Calendar = Calendar.getInstance()
                hour = calendar.get(Calendar.HOUR)
                minute = calendar.get(Calendar.MINUTE)
                val timePickerDialog = TimePickerDialog(this@ReminderActivity, timeSetListener, hour, minute,
                    DateFormat.is24HourFormat(this))
                timePickerDialog.show()
            }

            val datePickerDialog = DatePickerDialog(this@ReminderActivity, daySetListener, year, month, day)
            datePickerDialog.show()


            //TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        var reminderName : String;
        reminderName = "laadidaa"

        reminderText.afterTextChanged {
            reminderName = reminderText.text.toString()
        }
        addBtn.setOnClickListener {

            var reminder = ReminderSQL(0, reminderName, reminder_time = time, location_x = null, location_y = null, reminder_seen = null, iconId = resId)

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