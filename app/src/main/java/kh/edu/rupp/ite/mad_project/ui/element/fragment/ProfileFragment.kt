package kh.edu.rupp.ite.mad_project.ui.element.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kh.edu.rupp.ite.mad_project.R
import kh.edu.rupp.ite.mad_project.activity.LoginActivity
import kh.edu.rupp.ite.mad_project.data.model.ProfileResponse
import kh.edu.rupp.ite.mad_project.data.model.State
import kh.edu.rupp.ite.mad_project.databinding.FragmentProfileBinding
import kh.edu.rupp.ite.mad_project.ui.viewmodel.ProfileViewModel
import kh.edu.rupp.ite.mad_project.utils.SharedPrefManager

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPrefManager = SharedPrefManager(requireContext())

        val userId = sharedPrefManager.userId
        if (userId != null) {
            observeProfileData()
            viewModel.loadProfile(userId)
        }

        binding.btnLogout.setOnClickListener {
            showCustomLogoutDialog()
        }
    }

    private fun observeProfileData() {
        viewModel.profileState.observe(viewLifecycleOwner) { apiState ->
            when (apiState.state) {
                State.loading -> {
                    showLoading(true)
                }
                State.success -> {
                    showLoading(false)
                    apiState.data?.let { profile ->
                        displayProfile(profile)
                    }
                }
                State.error -> {
                    showLoading(false)
                    Toast.makeText(
                        requireContext(),
                        apiState.message ?: "An error occurred",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                State.none -> {
                    // Optionally handle the 'none' state
                    showLoading(false)
                }
            }
        }
    }


    private fun displayProfile(profile: ProfileResponse) {
        binding.userName.text = profile.fullName
        binding.email.text = profile.email
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.userName.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.email.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun showCustomLogoutDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_logout_confirmation, null)
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
        binding.progressBar.postDelayed({
            onLogoutComplete()
        }, 2000)
    }
}
