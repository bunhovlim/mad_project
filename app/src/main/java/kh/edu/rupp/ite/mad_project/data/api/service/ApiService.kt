package kh.edu.rupp.ite.mad_project.data.api.service

import kh.edu.rupp.ite.mad_project.data.model.ApiResponse
import kh.edu.rupp.ite.mad_project.data.model.CartData
import kh.edu.rupp.ite.mad_project.data.model.HomeProducts
import kh.edu.rupp.ite.mad_project.data.model.LoginResponse
import kh.edu.rupp.ite.mad_project.data.model.ProfileResponse
import kh.edu.rupp.ite.mad_project.data.model.UserData
import kh.edu.rupp.ite.mad_project.model.CartResponses
import kh.edu.rupp.ite.mad_project.model.FavoriteProductResponses
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResponse<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    suspend fun signUp(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String
    ): ApiResponse<LoginResponse>

    @GET("products")
    suspend fun getProducts(): Response<List<HomeProducts>>

    @GET("products/byModel/{models}")
    suspend fun getProductsByCategory(
        @Path("models") categoryName: String
    ): ApiResponse<List<HomeProducts>>

    @GET("favorites/{userId}")
    suspend fun getFavoriteProducts(
        @Path("userId") userId: String
    ): List<FavoriteProductResponses>

    @GET("user/{userId}")
    suspend fun loadUser(
        @Path("userId") userId: String
    ): UserData<ProfileResponse>

    @GET("cart/{userId}")
    suspend fun getUserCart(
        @Path("userId") userId: String
    ): List<CartResponses>

    @GET("favorites/{userId}")
    suspend fun getUserFavorite(
        @Path("userId") userId: String
    ): ApiResponse<List<FavoriteProductResponses>>
}