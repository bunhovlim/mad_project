package kh.edu.rupp.ite.mad_project.data.model

data class UserData<T>(
    val user: T?
) {
    fun isSuccess(): Boolean {
        // Consider the response successful if `data` is not null
        return user != null
    }
}
