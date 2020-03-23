package com.dedeandres.covnews.util.ext


import com.dedeandres.covnews.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single


/**
 * Use SchedulerProvider configuration for Observable
 */
fun Completable.with(schedulerProvider: SchedulerProvider): Completable =
    this.observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

/**
 * Use SchedulerProvider configuration for Single
 */
fun <T> Single<T>.with(schedulerProvider: SchedulerProvider): Single<T> =
    this.observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())