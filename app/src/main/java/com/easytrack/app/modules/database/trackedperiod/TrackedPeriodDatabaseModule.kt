package com.easytrack.app.modules.database.trackedperiod

import android.content.Context
import dagger.Module
import dagger.Provides


@Module
class ReportDatabaseModule {

    @Provides
    internal fun provideReportDataBase(context: Context): ReportDatabase {
        return ReportDatabase.getDatabase(context)
    }

    @Provides
    internal fun provideReportDao(dbb: ReportDatabase): ReportDao {
        return dbb.trackedPeriodDao()
    }
}