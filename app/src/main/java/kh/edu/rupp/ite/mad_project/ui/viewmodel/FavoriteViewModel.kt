package kh.edu.rupp.ite.mad_project.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.mad_project.data.api.client.ApiClient
import kh.edu.rupp.ite.mad_project.data.model.ApiState
import kh.edu.rupp.ite.mad_project.data.model.FavoriteProductResponses
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {

    private val  _favoriteState = MutableLiveData<ApiState<List<FavoriteProductResponses>>>()
    val favoriteState: LiveData<ApiState<List<FavoriteProductResponses>>> get() = _favoriteState

    fun loadFavorite(userId : String) {
        _favoriteState.postValue(ApiState.loading())

        viewModelScope.launch {
            try {
                val response: List<FavoriteProductResponses> = ApiClient.get().apiService.getFavoriteProducts(userId)
                if (response.isNotEmpty()) {
                    _favoriteState.postValue(ApiState.success(response))
                } else {
                    _favoriteState.postValue(ApiState.error("Fail to load favorite"))
                }
            } catch (e: Exception ) {
                _favoriteState.postValue(ApiState.error(e.message ?: "An unexpected error occurred"))
            }
        }
    }

}