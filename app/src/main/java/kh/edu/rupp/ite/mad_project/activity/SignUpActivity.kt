package kh.edu.rupp.ite.mad_project.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kh.edu.rupp.ite.mad_project.api.ApiManager
import kh.edu.rupp.ite.mad_project.databinding.ActivitySignupBinding
import kh.edu.rupp.ite.mad_project.model.SignupRequest
import kh.edu.rupp.ite.mad_project.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

        // Declare view binding
        private lateinit var binding: ActivitySignupBinding

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)

                // Initialize view binding
                binding = ActivitySignupBinding.inflate(layoutInflater)
                setContentView(binding.root)

                // Set up button click listener
                binding.btnSignup.setOnClickListener { signUpUser() }
        }

        private fun signUpUser() {
                val fullName = binding.etUsername.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()

                if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                        return
                }

                val signupRequest = SignupRequest(fullName, email, password)

                // Use ApiManager to get the ApiService instance
                val apiService = ApiManager.getInstance().apiService
                apiService.signUp(signupRequest).enqueue(object : Callback<UserResponse?> {
                        override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                                if (response.isSuccessful && response.body() != null) {
                                        Toast.makeText(
                                                this@SignupActivity,
                                                "Sign-up successful!",
                                                Toast.LENGTH_SHORT
                                        ).show()
                                        finish()  // Close the activity after successful sign-up
                                } else {
                                        Toast.makeText(
                                                this@SignupActivity,
                                                "Sign-up failed",
                                                Toast.LENGTH_SHORT
                                        ).show()
                                }
                        }

                        override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                                Toast.makeText(
                                        this@SignupActivity,
                                        "Network error",
                                        Toast.LENGTH_SHORT
                                ).show()
                        }
                })
        }
}
