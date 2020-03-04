package com.easytrack.app.utils.rx

import io.reactivex.*

interface RxSchedulers {

    fun getMainThreadScheduler(): Scheduler

    fun getIOScheduler(): Scheduler

    fun getComputationScheduler(): Scheduler

    fun <T> getIOToMainTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer { objectObservable ->
            objectObservable.subscribeOn(getIOScheduler())
                .observeOn(getMainThreadScheduler())
        }
    }

    fun <T> getIOToMainTransformerSingle(): SingleTransformer<T, T> {
        return SingleTransformer { objectObservable ->
            objectObservable.subscribeOn(getIOScheduler())
                .observeOn(getMainThreadScheduler())
        }
    }

    fun <T> getIOToMainTransformerFlowable(): FlowableTransformer<T, T> {
        return FlowableTransformer { objectObservable ->
            objectObservable.subscribeOn(getIOScheduler())
                .observeOn(getMainThreadScheduler())
        }
    }

    fun getIOToMainTransformerCompletable(): CompletableTransformer {
        return CompletableTransformer { objectObservable ->
            objectObservable.subscribeOn(getIOScheduler())
                .observeOn(getMainThreadScheduler())
        }
    }

    fun <T> getComputationToMainTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer { objectObservable ->
            objectObservable.subscribeOn(getComputationScheduler())
                .observeOn(getMainThreadScheduler())
        }
    }

    fun <T> getComputationToMainTransformerSingle(): SingleTransformer<T, T> {
        return SingleTransformer { objectObservable ->
            objectObservable.subscribeOn(getComputationScheduler())
                .observeOn(getMainThreadScheduler())
        }
    }
}