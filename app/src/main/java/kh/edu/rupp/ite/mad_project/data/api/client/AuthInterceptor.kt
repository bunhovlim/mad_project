package kh.edu.rupp.ite.mad_project.data.api.client

import android.util.Log
import kh.edu.rupp.ite.mad_project.global.App
import kh.edu.rupp.ite.mad_project.global.AppEncryptedPref
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = AppEncryptedPref.get().getToken(App.get())
        Log.d("ruppite", "intercept: $token")
        if(token == null) {
            return chain.proceed(chain.request())
        } else {
            Log.d("ruppite", "include token")
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            return chain.proceed(request)
        }
    }
}