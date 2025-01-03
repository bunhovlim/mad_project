package kh.edu.rupp.ite.mad_project.data.model

import com.google.gson.annotations.SerializedName

data class FavoriteProductResponses(
    @SerializedName("_id")
    val id: String,
    val user: String,
    val product: FavoriteProduct
)

data class FavoriteProduct(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val title: String,
    val price: String,
    val image: String,
    val category: String,
    val year: String,
    val rate: String,
    val model: String,
    val createdAt: String
)
