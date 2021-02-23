package com.example.mobilecomputing.ui.login

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
        @ColumnInfo(name = "message") val message: String?
        //@ColumnInfo(name = "creation_time") val creation_time: Date

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
}

@Database(entities = arrayOf(ReminderSQL::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ReminderSQLDao(): ReminderSQLDao
}