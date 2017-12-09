package com.nicolasguillen.kointlin.libs.rx.operators

import com.google.gson.Gson
import com.nicolasguillen.kointlin.services.errors.ApiException
import com.nicolasguillen.kointlin.services.errors.ResponseException

class Operators {

    companion object {
        /**
         * When a response errors, send an [ApiException] or [ResponseException] to
         * [Subscriber.onError], otherwise send the response to [Subscriber.onNext].
         */
        fun <T> apiError(gson: Gson): ApiErrorOperator<T> {
            return ApiErrorOperator(gson)
        }
    }
}
