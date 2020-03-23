package com.dedeandres.covnews.util

import io.reactivex.Single

abstract class SingleUseCase<RESULT> {

    abstract fun buildUseCaseSingle(data: Map<String, Any?> = emptyMap()): Single<RESULT>

    fun execute(data: Map<String, Any?> = emptyMap()): Single<RESULT> {
        return buildUseCaseSingle(data)
    }

}