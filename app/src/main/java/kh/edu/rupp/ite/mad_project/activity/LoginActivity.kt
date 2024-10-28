package kh.edu.rupp.ite.mad_project.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kh.edu.rupp.ite.mad_project.MainActivity
import kh.edu.rupp.ite.mad_project.api.ApiManager
import kh.edu.rupp.ite.mad_project.databinding.ActivityLoginBinding
import kh.edu.rupp.ite.mad_project.model.LoginRequest
import kh.edu.rupp.ite.mad_project.model.UserResponse
import kh.edu.rupp.ite.mad_project.utils.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

        private lateinit var binding: ActivityLoginBinding
        private lateinit var sharedPrefManager: SharedPrefManager

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)

                // Initialize view binding
                binding = ActivityLoginBinding.inflate(layoutInflater)
                setContentView(binding.root)

                // Initialize shared preferences
                sharedPrefManager = SharedPrefManager(this)

                // Check if already logged in
                if (sharedPrefManager.token != null) {
                        navigateToHome()
                }

                // Set button click listeners
                binding.btnLogin.setOnClickListener { loginUser() }
                binding.btnSignup.setOnClickListener {
                        val intent = Intent(this@LoginActivity, SignupActivity::class.java)
                        startActivity(intent)
                }
        }

        private fun loginUser() {
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()

                if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                        return
                }

                showLoading(true)

                val loginRequest = LoginRequest(email, password)

                // Use ApiManager to get the ApiService instance
                val apiService = ApiManager.getInstance().apiService
                apiService.login(loginRequest).enqueue(object : Callback<UserResponse?> {
                        override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                                showLoading(false)
                                if (response.isSuccessful && response.body() != null) {
                                        val token = response.body()!!.token
                                        val userId = response.body()!!.userId
                                        sharedPrefManager.saveToken(token)
                                        sharedPrefManager.saveUserId(userId)
                                        Toast.makeText(this@LoginActivity, "Login Successfully", Toast.LENGTH_SHORT).show()
                                        navigateToHome()
                                } else {
                                        Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                                }
                        }

                        override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                                showLoading(false)
                                Toast.makeText(this@LoginActivity, "Network error", Toast.LENGTH_SHORT).show()
                        }
                })
        }

        private fun navigateToHome() {
                try {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                } catch (e: Exception) {
                        Toast.makeText(this, "Navigation failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }

        private fun showLoading(isLoading: Boolean) {
                if (isLoading) {
                        binding.loadingLayout.visibility = View.VISIBLE
                } else {
                        binding.loadingLayout.visibility = View.GONE
                }
        }


}
