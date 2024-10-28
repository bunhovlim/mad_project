package kh.edu.rupp.ite.mad_project.api

//import kh.edu.rupp.ite.mad_project.model.Home_Categories
import kh.edu.rupp.ite.mad_project.model.HomeProducts
import kh.edu.rupp.ite.mad_project.model.LoginRequest
import kh.edu.rupp.ite.mad_project.model.SignupRequest
import kh.edu.rupp.ite.mad_project.model.UserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<UserResponse>

    @POST("register")
    fun signUp(@Body signupRequest: SignupRequest): Call<UserResponse>

    @GET("products")
    fun getProducts(): Call<List<HomeProducts>>

    @GET("products/byModel/{models}")
    fun getProductsByCategory(@Path("models") categoryName: String): Call<List<HomeProducts>>

    @GET("favorites/{userId}") // Update with your actual endpoint
    fun getFavoriteProducts(@Path("userId") userId: String): Call<List<HomeProducts>>
}
