package kh.edu.rupp.ite.mad_project.data.api.client

import kh.edu.rupp.ite.mad_project.data.api.service.ApiService
import kh.edu.rupp.ite.mad_project.global.AppConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient private constructor() {

    private val logInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logInterceptor)
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(AuthInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.API_BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    companion object {

        private var instance: ApiClient? = null

        fun get(): ApiClient {
            if (instance == null) {
                instance = ApiClient()
            }

            return instance!!
        }

    }

}