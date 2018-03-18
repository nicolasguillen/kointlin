package com.nicolasguillen.kointlin.libs.rx.operators

import com.google.gson.Gson
import com.nicolasguillen.kointlin.services.errors.ApiException
import com.nicolasguillen.kointlin.services.errors.ResponseException
import com.nicolasguillen.kointlin.services.reponses.ErrorEnvelope
import io.reactivex.SingleObserver
import io.reactivex.SingleOperator
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscriber
import retrofit2.Response
import java.io.IOException

/**
 * Takes a [Response], if it's successful send it to [SingleObserver.onSuccess], otherwise
 * attempt to parse the error.

 * Errors that conform to the API's error format are converted into an [ApiException] exception and sent to
 * [SingleObserver.onError], otherwise a more generic [ResponseException] is sent to [Subscriber.onError].

 * @param <T> The response type.
</T> */
class ApiErrorOperator<T> internal constructor(private val gson: Gson) : SingleOperator<T, Response<T>> {

    override fun apply(observer: SingleObserver<in T>): SingleObserver<in Response<T>> {
        return object : SingleObserver<Response<T>> {

            override fun onSubscribe(d: Disposable) {
                observer.onSubscribe(d)
            }

            override fun onError(e: Throwable) {
                observer.onError(e)
            }

            override fun onSuccess(response: Response<T>) {
                if (!response.isSuccessful) {
                    try {
                        val envelope = gson.fromJson(response.errorBody()?.string(), ErrorEnvelope::class.java)
                        observer.onError(ApiException(envelope))
                    } catch (e: IOException) {
                        observer.onError(ResponseException())
                    }

                }

                observer.onSuccess(response.body()!!)
            }
        }
    }

}
