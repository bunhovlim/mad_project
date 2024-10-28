package kh.edu.rupp.ite.mad_project.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kh.edu.rupp.ite.mad_project.R
import kh.edu.rupp.ite.mad_project.activity.LoginActivity
import kh.edu.rupp.ite.mad_project.api.ApiManager
import kh.edu.rupp.ite.mad_project.model.ProfileResponse
import kh.edu.rupp.ite.mad_project.utils.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var tvFullName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPrefManager = SharedPrefManager(requireContext())
        tvFullName = view.findViewById(R.id.userName)
        tvEmail = view.findViewById(R.id.email)
        progressBar = view.findViewById(R.id.progressBar)

        val userId = sharedPrefManager.userId
        if (userId != null) {
            fetchUserData(userId)
        }

        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            showCustomLogoutDialog()
        }
    }

    private fun fetchUserData(userId: String) {
        val apiService = ApiManager.getInstance().apiService
        apiService.getUser(userId).enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()?.user
                    Log.d("ProfileFragment", "User name: ${user?.fullName}")
                    Log.d("ProfileFragment", "User email: ${user?.email}")

                    tvFullName.text = user?.fullName
                    tvEmail.text = user?.email
                } else {
                    Log.d("ProfileFragment", "Failed to load user data. Code: ${response.code()}")
                    Toast.makeText(requireContext(), "Failed to load user data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.d("ProfileFragment", "Network error: ${t.message}")
                Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        tvFullName.visibility = if (isLoading) View.GONE else View.VISIBLE
        tvEmail.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun showCustomLogoutDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_logout_confirmation, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        val alertDialog = builder.create()

        val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirm)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
        val progressBar = dialogView.findViewById<ProgressBar>(R.id.progressBar)

        btnConfirm.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            btnConfirm.isEnabled = false
            btnCancel.isEnabled = false

            logoutUserWithDelay {
                sharedPrefManager.clearToken()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
                alertDialog.dismiss()
            }
        }

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun logoutUserWithDelay(onLogoutComplete: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            onLogoutComplete()
        }, 2000)
    }
}
