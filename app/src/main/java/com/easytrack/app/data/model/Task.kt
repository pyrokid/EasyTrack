package com.easytrack.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val taskName: String,
    val taskImage: String,
    val isTaskEnabled: Boolean
) {
}