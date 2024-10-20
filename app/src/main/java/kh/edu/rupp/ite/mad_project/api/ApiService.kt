package kh.edu.rupp.ite.mad_project.api

import kh.edu.rupp.ite.mad_project.model.LoginRequest
import kh.edu.rupp.ite.mad_project.model.SignupRequest
import kh.edu.rupp.ite.mad_project.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<UserResponse>

    @POST("register")
    fun signUp(@Body signupRequest: SignupRequest): Call<UserResponse>
}
