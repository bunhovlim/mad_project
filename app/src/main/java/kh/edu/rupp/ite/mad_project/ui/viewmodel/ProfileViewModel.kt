package kh.edu.rupp.ite.mad_project.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.mad_project.data.api.client.ApiClient
import kh.edu.rupp.ite.mad_project.data.model.ApiState
import kh.edu.rupp.ite.mad_project.data.model.ProfileResponse
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _profileState = MutableLiveData<ApiState<ProfileResponse>>()
    val profileState: LiveData<ApiState<ProfileResponse>> get() = _profileState

    fun loadProfile(userId: String) {
        _profileState.value = ApiState.loading()

        viewModelScope.launch {
            try {
                Log.d("ProfileViewModel", "Loading profile for user ID: $userId")
                val response = ApiClient.get().apiService.loadUser(userId)
                Log.d("ProfileViewModel", "API Response: $response")
                if (response.isSuccess()) {
                    _profileState.value = ApiState.success(response.user)
                    Log.d("ProfileViewModel", "Profile loaded successfully.")
                } else {
                    Log.d("ProfileViewModel", "API Error: ${response.user}")
                    _profileState.value = ApiState.error("Failed to load profile: ${response.user}")
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Exception occurred: ${e.localizedMessage}")
                _profileState.value = ApiState.error(e.message ?: "An unexpected error occurred")
            }
        }

    }
}

