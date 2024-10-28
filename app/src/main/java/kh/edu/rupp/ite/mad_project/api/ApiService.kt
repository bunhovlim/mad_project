package kh.edu.rupp.ite.mad_project.api

import kh.edu.rupp.ite.mad_project.model.LoginRequest
import kh.edu.rupp.ite.mad_project.model.ProfileResponse
import kh.edu.rupp.ite.mad_project.model.SignupRequest
import kh.edu.rupp.ite.mad_project.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<UserResponse>

    @POST("register")
    fun signUp(@Body signupRequest: SignupRequest): Call<UserResponse>

    @GET("user/{userId}")
    fun getUser(@Path("userId") userId: String): Call<ProfileResponse>
}
