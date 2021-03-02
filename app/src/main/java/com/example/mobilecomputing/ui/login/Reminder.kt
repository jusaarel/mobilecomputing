package com.example.mobilecomputing.ui.login

import android.content.Context
import android.util.Log
import androidx.room.*
import java.util.*

class Reminder(val name : String, val time : Date) {


    public var message = name

    public var location_x : Float = 0f
    public var location_y : Float = 0f

    public var reminder_time = time;
    public var reminder_seen = Calendar.getInstance().time
    public val creation_time = Calendar.getInstance().time

    public var creator_id = 1234
}

@Entity
data class ReminderSQL(
    @PrimaryKey (autoGenerate = true)
        val id : Int = 0,
    @ColumnInfo(name = "message") var message: String?,
    @ColumnInfo(name = "creation_time") val creation_time: Long = Calendar.getInstance().timeInMillis,
    @ColumnInfo(name = "creator_id") val creator_id: Int = 1,
    @ColumnInfo(name = "reminder_seen") val reminder_seen: Long?,
    @ColumnInfo(name = "reminder_time") var reminder_time: Long,
    @ColumnInfo(name = "location_x") val location_x: Float?,
    @ColumnInfo(name = "location_y") val location_y: Float?,
    @ColumnInfo(name = "icon") val iconId: Int?




        )


@Dao
interface ReminderSQLDao {
    @Query("SELECT * FROM ReminderSQL")
    fun getAll(): List<ReminderSQL>

    @Query("SELECT * FROM ReminderSQL WHERE message LIKE :message")
    fun findByName(message: String): ReminderSQL

    @Insert
    fun insertAll(vararg users: ReminderSQL)

    @Delete
    fun delete(user: ReminderSQL)

    @Update
    fun update(item: ReminderSQL)
}

@Database(entities = arrayOf(ReminderSQL::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ReminderSQLDao(): ReminderSQLDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context, AppDatabase::class.java,
                        "database-name")
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE as AppDatabase
        }
        fun delete(item : String)
        {
            val dao = INSTANCE?.ReminderSQLDao()
            val find = dao?.findByName(item ?: "default")
            if(find != null)
            {
                dao?.delete(find)
            }
        }

        fun update(item: String, newName : String, time: Long)
        {
            val dao = INSTANCE?.ReminderSQLDao()
            var find = dao?.findByName(item ?: "default")

            if(find != null)
            {
                Log.d("asd", "editing entry")
                find.message = newName
                find.reminder_time = time
                dao?.update(find)
            }
        }
    }
}
