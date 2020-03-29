package com.dedeandres.covnews.util

import androidx.lifecycle.MutableLiveData

data class Resource<out T> constructor(
    val state: ResourceState,
    val data: T? = null,
    val throwable: Throwable? = null
)

sealed class ResourceState {
    object LOADING : ResourceState()
    object SUCCESS : ResourceState()
    object ERROR : ResourceState()
}

fun <T> MutableLiveData<Resource<T>>.setSuccess(data: T): MutableLiveData<Resource<T>> {
    postValue(
        Resource(
            ResourceState.SUCCESS,
            data
        )
    )
    return this
}

fun <T> MutableLiveData<Resource<T>>.setLoading() =
    postValue(

        Resource(ResourceState.LOADING)

    )

fun <T> MutableLiveData<Resource<T>>.setSuccess() =
    postValue(
        Resource(
            ResourceState.SUCCESS,
            null
        )
    )

fun <T> MutableLiveData<Resource<T>>.setError(throwable: Throwable? = null) =
    postValue(
        Resource(
            ResourceState.ERROR,
            value?.data,
            throwable
        )
    )