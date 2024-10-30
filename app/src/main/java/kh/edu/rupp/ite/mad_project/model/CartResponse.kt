package kh.edu.rupp.ite.mad_project.model

import com.google.gson.annotations.SerializedName

data class CartResponse(
    @SerializedName("cartItem")
    val cartItem: CartItem,
    @SerializedName("productDetail")
    val productDetail: ProductDetail
)

data class CartItem(
    @SerializedName("_id")
    val id: String,
    @SerializedName("user")
    val user: String,
    @SerializedName("product")
    val product: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("__v")
    val version: Int
)

data class ProductDetail(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("year")
    val year: String,
    @SerializedName("rate")
    val rate: Int,
    @SerializedName("model")
    val model: String,
    @SerializedName("productID")
    val productID: Int,
    @SerializedName("__v")
    val version: Int,
    @SerializedName("createdAt")
    val createdAt: String
)
