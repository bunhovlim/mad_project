package kh.edu.rupp.ite.mad_project.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.mad_project.data.api.client.ApiClient
import kh.edu.rupp.ite.mad_project.data.model.ApiState
import kh.edu.rupp.ite.mad_project.data.model.LoginResponse
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private var _signUpData = MutableLiveData<ApiState<LoginResponse>>()
    val signUpData get() = _signUpData

    fun signUn(email: String, password: String, fullName: String) {
        var apiState = ApiState.loading<LoginResponse>()
        _signUpData.postValue(apiState)

        viewModelScope.launch {
            apiState = try {
                val response = ApiClient.get().apiService.signUp(email, password, fullName)
                if(response.isSuccess()) {
                    ApiState.success(response.data)
                } else {
                    ApiState.error("Fail to signUp")
                }
            } catch (e: Exception) {
                ApiState.error(e.message)
            }

        }
    }

}