package kh.edu.rupp.ite.mad_project.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kh.edu.rupp.ite.mad_project.data.model.ApiState
import kh.edu.rupp.ite.mad_project.data.model.LoginResponse
import kh.edu.rupp.ite.mad_project.data.model.State
import kh.edu.rupp.ite.mad_project.databinding.ActivityLoginBinding
import kh.edu.rupp.ite.mad_project.databinding.ActivitySignupBinding
import kh.edu.rupp.ite.mad_project.global.AppEncryptedPref
import kh.edu.rupp.ite.mad_project.global.AppPref
import kh.edu.rupp.ite.mad_project.ui.viewmodel.LoginViewModel
import kh.edu.rupp.ite.mad_project.ui.viewmodel.SignUpViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

        // Declare view binding
        private lateinit var binding: ActivitySignupBinding
        private val viewModel by viewModels<SignUpViewModel>()

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)


                setupUi()
                setupListener()
                setupObserver()
                // Initialize view binding
//                binding = ActivitySignupBinding.inflate(layoutInflater)
//                setContentView(binding.root)
//
//                // Set up button click listener
//                binding.btnSignup.setOnClickListener { signUpUser() }
        }

        private fun setupUi() {
                binding = ActivitySignupBinding.inflate(layoutInflater)
                setContentView(binding.root)
        }

        private fun setupListener() {
                binding.btnSignup.setOnClickListener { onSignUpButtonClick() }
        }

        private fun setupObserver() {
                viewModel.signUpData.observe(this) { handleState(it) }
        }

        private fun onSignUpButtonClick() {
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                val fullName = binding.etUsername.text.toString().trim()
                if (email.isEmpty() || password.isEmpty()) {
//                        showAlert("Invalid Input", "Please enter username and password")
                        return
                }

                viewModel.signUn(email, password, fullName)
        }

        private fun handleState(state: ApiState<LoginResponse>) {
                when (state.state) {
                        State.loading -> {
//                                showLoading(true)
                        }
                        State.success -> {
                                AppPref.get().storeProfile(this, state.data!!.userId)
                                AppEncryptedPref.get().storeToken(this, state.data.token)
                                setResult(RESULT_OK)
                                finish()
                        }

                        State.error -> {
//                                hideLoading()
//                                showAlert("Error", state.message ?: "Unexpected Error")
                        }

                        else -> {}
                }
        }

//        private fun signUpUser() {
//                val fullName = binding.etUsername.text.toString().trim()
//                val email = binding.etEmail.text.toString().trim()
//                val password = binding.etPassword.text.toString().trim()
//
//                if (email.isEmpty() || password.isEmpty()) {
//                        Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
//                        return
//                }
//
//                val signupRequest = SignupRequest(fullName, email, password)
//
//                // Use ApiManager to get the ApiService instance
//                val apiService = ApiManager.getInstance().apiService
//                apiService.signUp(signupRequest).enqueue(object : Callback<UserResponse?> {
//                        override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
//                                if (response.isSuccessful && response.body() != null) {
//                                        Toast.makeText(
//                                                this@SignupActivity,
//                                                "Sign-up successful!",
//                                                Toast.LENGTH_SHORT
//                                        ).show()
//                                        finish()  // Close the activity after successful sign-up
//                                } else {
//                                        Toast.makeText(
//                                                this@SignupActivity,
//                                                "Sign-up failed",
//                                                Toast.LENGTH_SHORT
//                                        ).show()
//                                }
//                        }
//
//                        override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
//                                Toast.makeText(
//                                        this@SignupActivity,
//                                        "Network error",
//                                        Toast.LENGTH_SHORT
//                                ).show()
//                        }
//                })
//        }
}
