package com.easytrack.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "reports")
data class Report(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val taskId: Long,
    var periodStart: Long,
    val periodFinish: Long?,
    val isReportEnabled: Boolean,
    val taskName: String,
    val taskImage: String
) {
    fun calculateSpentTime(periodStart: Long, periodFinish: Long?): String {

        if (periodFinish != null) {

            val start = convertLongToDate(periodStart)
            val finish = convertLongToDate(periodFinish)

            val diff = finish.time - start.time
            var diffMin = diff / (60 * 1000)
            val diffHour = diffMin / 60
            diffMin %= 60

            return diffHour.toString() + "h. " + diffMin.toString() + "m. spent"
        } else
            return "Task is still recording"

    }


    fun show(periodStart: Long, periodFinish: Long): String {
        return periodStart.toString() + periodFinish.toString()
    }

    companion object {

        fun convertLongToDate(time: Long): Date {
            return Date(time)
        }

        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
            return format.format(date)
        }

        fun convertLongToStringTime(date: Long): String {
            val df = SimpleDateFormat("HH:mm")
            return df.format(date)
        }

        fun convertLongToStringDate(date: Long): String {
            val df = SimpleDateFormat("dd.MM.yyyy")
            return df.format(date)
        }

        fun convertDateToLong(date: Date): Long {
            return date.time
        }
    }
}