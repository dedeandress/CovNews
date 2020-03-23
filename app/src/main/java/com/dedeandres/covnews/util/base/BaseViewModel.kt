package com.dedeandres.covnews.util.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel: ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    protected fun Disposable.collect() {
        compositeDisposable.add(this)
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

}