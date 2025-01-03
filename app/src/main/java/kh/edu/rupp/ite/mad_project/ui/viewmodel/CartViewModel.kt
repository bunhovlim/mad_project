package kh.edu.rupp.ite.mad_project.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.mad_project.data.api.client.ApiClient
import kh.edu.rupp.ite.mad_project.data.model.ApiState
import kh.edu.rupp.ite.mad_project.data.model.CartResponse
import kh.edu.rupp.ite.mad_project.model.CartResponse
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val _cartState = MutableLiveData<ApiState<List<CartResponse>>>()
    val cartState: LiveData<ApiState<List<CartResponse>>> get() = _cartState

    fun loadCart(userId: String) {
        _cartState.postValue(ApiState.loading())

        viewModelScope.launch {
            try {
                val response: List<CartResponse> = ApiClient.get().apiService.getUserCart(userId)
                if (response.isNotEmpty()) {
                    _cartState.postValue(ApiState.success(response))
                } else {
                    _cartState.postValue(ApiState.error("Fail to load Cart"))
                }
            } catch (e: Exception) {
                _cartState.postValue(ApiState.error(e.message ?: "An unexpected error occurred"))
            }
        }
    }

}