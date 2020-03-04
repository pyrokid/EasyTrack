package com.easytrack.app.modules.main.management.view

interface IManageTask {
    fun addTask(id: Long, taskName: String, photo: String)
    fun changeTask(id: Long, taskName: String, photo: String)
    fun deleteTask(id: Long)
}