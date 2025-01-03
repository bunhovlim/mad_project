package kh.edu.rupp.ite.mad_project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.ite.mad_project.data.model.State
import kh.edu.rupp.ite.mad_project.databinding.FragmentFavoriteBinding
import kh.edu.rupp.ite.mad_project.ui.element.Adapter.FavoriteProductAdapter
import kh.edu.rupp.ite.mad_project.ui.viewmodel.FavoriteViewModel
import kh.edu.rupp.ite.mad_project.utils.SharedPrefManager

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteProductAdapter
    private lateinit var sharedPrefManager: SharedPrefManager

    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout with View Binding
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(requireContext())
        favoriteAdapter = FavoriteProductAdapter(mutableListOf())
        binding.recyclerViewFavorites.adapter = favoriteAdapter

        // Initialize SharedPrefManager
        sharedPrefManager = SharedPrefManager(requireContext())

        // Observe the ViewModel
        viewModel.favoriteState.observe(viewLifecycleOwner) { apiState ->
            when (apiState.state) {
                State.loading -> {
                    // Show a loading spinner
//                    binding.progressBar.visibility = View.VISIBLE
                }
                State.success -> {
                    // Hide loading spinner and update the adapter
//                    binding.progressBar.visibility = View.GONE
                    favoriteAdapter.updateData(apiState.data!!)
                }
                State.error -> {
                    // Hide loading spinner and show error message
//                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), apiState.message, Toast.LENGTH_SHORT).show()
                }
                State.none -> {
                    // Optionally handle the 'none' state
//                    showLoading(false)
                }
            }
        }

        // Fetch favorite products
        val userId = sharedPrefManager.getUserId()
        if (!userId.isNullOrEmpty()) {
            viewModel.loadFavorite(userId)
        } else {
            Toast.makeText(requireContext(), "User ID is not available.", Toast.LENGTH_SHORT).show()
        }
    }
}
