package it.fantacalcio.sample.core.network.retrofit

import android.content.Context
import it.fantacalcio.sample.core.constants.Constants
import it.fantacalcio.sample.core.utils.EnvironmentUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    fun getRetrofitApi(
        context: Context,
        baseUrl: String = Constants.BASE_URL
    ): ApiInterface {
        return createService(context, baseUrl, ApiInterface::class.java)
    }

    private fun <T> createService(
        context: Context,
        baseUrl: String,
        interfaceApiClass: Class<T>
    ): T {
        // Define okHttpClient
        val okHttpClient = createOkHttpClient(context, baseUrl)

        // Define json converter
        val converterFactory = createGsonConverter()

        // RetrofitBuilder
        val retrofitBuilder = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .baseUrl(baseUrl)
            .build()

        return retrofitBuilder.create(interfaceApiClass)
    }

    private fun createGsonConverter(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    private fun createOkHttpClient(context: Context, baseUrl: String): OkHttpClient {
        // OkHttpClientBuilder
        val okHttpClientBuilder = OkHttpClient.Builder()

        // Logging
        if (EnvironmentUtils.isDebug()) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }

        // Timeouts
        okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS)

        // Define okHttpClient
        return okHttpClientBuilder.build()
    }

}
