package com.easytrack.app.utils.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RxSchedulersImpl : RxSchedulers {

    override fun getMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

    override fun getIOScheduler(): Scheduler = Schedulers.io()

    override fun getComputationScheduler(): Scheduler = Schedulers.computation()
}