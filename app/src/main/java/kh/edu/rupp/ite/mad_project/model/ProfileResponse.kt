package kh.edu.rupp.ite.mad_project.model

data class ProfileResponse(
    val user: UserData
)

data class UserData(
    val id: String,
    val fullName: String,
    val email: String,
    val role: String,
    val userID: Int
)

