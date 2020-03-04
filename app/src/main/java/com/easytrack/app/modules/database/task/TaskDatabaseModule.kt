package com.easytrack.app.modules.database.task

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class TaskDatabaseModule {

    @Provides
    internal fun provideTaskDataBase(context: Context): TaskDatabase {
        return TaskDatabase.getDatabase(context)
    }

    @Provides
    internal fun provideTaskDao(db: TaskDatabase): TaskDao {
        return db.taskDao()
    }
}