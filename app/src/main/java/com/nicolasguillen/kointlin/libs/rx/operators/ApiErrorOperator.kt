package com.nicolasguillen.kointlin.libs.rx.operators

import com.google.gson.Gson
import com.nicolasguillen.kointlin.services.errors.ApiException
import com.nicolasguillen.kointlin.services.errors.ResponseException
import com.nicolasguillen.kointlin.services.reponses.ErrorEnvelope
import io.reactivex.FlowableOperator
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import retrofit2.Response
import java.io.IOException

/**
 * Takes a [Response], if it's successful send it to [Subscriber.onNext], otherwise
 * attempt to parse the error.

 * Errors that conform to the API's error format are converted into an [ApiException] exception and sent to
 * [Subscriber.onError], otherwise a more generic [ResponseException] is sent to [Subscriber.onError].

 * @param <T> The response type.
</T> */
class ApiErrorOperator<T> internal constructor(private val gson: Gson) : FlowableOperator<T, Response<T>> {

    override fun apply(observer: Subscriber<in T>): Subscriber<in Response<T>> {
        return object : Subscriber<Response<T>> {

            override fun onSubscribe(s: Subscription?) {
                observer.onSubscribe(s)
            }

            override fun onComplete() {
                observer.onComplete()
            }

            override fun onError(e: Throwable) {
                observer.onError(e)
            }

            override fun onNext(response: Response<T>) {

                if (!response.isSuccessful) {
                    try {
                        val envelope = gson.fromJson(response.errorBody()?.string(), ErrorEnvelope::class.java)
                        observer.onError(ApiException(envelope))
                    } catch (e: IOException) {
                        observer.onError(ResponseException())
                    }

                }

                observer.onNext(response.body()!!)
            }
        }
    }

}
