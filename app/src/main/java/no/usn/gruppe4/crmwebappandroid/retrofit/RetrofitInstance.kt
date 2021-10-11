package no.usn.gruppe4.crmwebappandroid.retrofit

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.CookieHandler
import java.net.CookieManager
import java.util.*
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://crmapp2000.herokuapp.com/api/"
object RetrofitInstance {

    var cookieHandler: CookieHandler = CookieManager()
    //adding a date format changing factory
    private val moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .addLast(KotlinJsonAdapterFactory())
        .build()
    /**
     * Use the Retrofit builder to build a retrofit object using a Moshi converter.
     */
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
    }

    val api: CrmApi by lazy {
        retrofit.create(CrmApi::class.java)
    }

    private val client : OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            builder.cookieJar(JavaNetCookieJar(cookieHandler))
            builder.connectTimeout(15, TimeUnit.SECONDS)
            builder.readTimeout(20, TimeUnit.SECONDS)
            builder.writeTimeout(20, TimeUnit.SECONDS)
            builder.retryOnConnectionFailure(true)
            return builder.build()
        }
}