package kh.edu.rupp.ite.mad_project.data.model

data class ApiResponse<T>(
    val data: T?
) {
    fun isSuccess(): Boolean {
        // Consider the response successful if `data` is not null
        return data != null
    }
}
