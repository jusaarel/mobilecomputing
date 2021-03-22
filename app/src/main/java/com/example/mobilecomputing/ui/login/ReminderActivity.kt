package com.example.mobilecomputing.ui.login

import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.getActivity
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mobilecomputing.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class ReminderActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        val NotificationCheck = findViewById<CheckBox>(R.id.AddNotification)
        val reminderText = findViewById<EditText>(R.id.reminderName)
        val mPickTimeBtn = findViewById<Button>(R.id.pickTime)
        val textView     = findViewById<TextView>(R.id.timeView)
        val addBtn = findViewById<Button>(R.id.add)
        val pickIcon = findViewById<Button>(R.id.pickIcon)

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

            if(NotificationCheck.isChecked)
            {
                Log.d("TIME", time.toString())
                ReminderActivity.setReminderWithWorkManager(
                        applicationContext,
                        time,
                        reminderName
                )
            }

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


        val NotificationCheck = findViewById<CheckBox>(R.id.AddNotification)
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

            if(NotificationCheck.isChecked)
            {
                Log.d("TIME", time.toString())
                ReminderActivity.setReminderWithWorkManager(
                        applicationContext,
                        time,
                        reminderName
                )
            }

            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()

            val userDao = db.ReminderSQLDao()
            userDao.insertAll(reminder)
            Log.d("REMINDER", reminder.message);
        }
    }

    companion object {
        //val paymenthistoryList = mutableListOf<PaymentInfo>()

        fun showNofitication(context: Context, message: String) {

            val CHANNEL_ID = "BANKING_APP_NOTIFICATION_CHANNEL"
            var notificationId = Random.nextInt(10, 1000) + 5
            // notificationId += Random(notificationId).nextInt(1, 500)

            var notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.info)
                    .setContentTitle(context.getString(R.string.app_name))
                    .setContentText(message)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setGroup(CHANNEL_ID)

            val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Notification chancel needed since Android 8
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                        CHANNEL_ID,
                        context.getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = context.getString(R.string.app_name)
                }
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(notificationId, notificationBuilder.build())

        }


        fun setReminderWithWorkManager(
                context: Context,
                timeInMillis: Long,
                message: String
        ) {

            Log.d("REMINDER", "workmanager");
            val reminderParameters = Data.Builder()
                    .putString("message", message)
                    .build()

            // get minutes from now until reminder
            var minutesFromNow = 0L
            Log.d("REMINDER", minutesFromNow.toString());
            if (timeInMillis > System.currentTimeMillis())
                minutesFromNow = timeInMillis - System.currentTimeMillis()

            val reminderRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                    .setInputData(reminderParameters)
                    .setInitialDelay(minutesFromNow, TimeUnit.MILLISECONDS)
                    .build()

            WorkManager.getInstance(context).enqueue(reminderRequest)
        }

    }
}