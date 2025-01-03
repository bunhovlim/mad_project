package kh.edu.rupp.ite.mad_project.data.api.client

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Log the headers
        Log.d("ruppite", "Request Headers:")
        request.headers.forEach { header ->
            Log.d("ruppite", "${header.first}: ${header.second}")
        }

        return chain.proceed(request) // Continue the chain
    }
}