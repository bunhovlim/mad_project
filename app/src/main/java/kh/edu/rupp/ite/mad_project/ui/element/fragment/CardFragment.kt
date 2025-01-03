package kh.edu.rupp.ite.mad_project.ui.element.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.ite.mad_project.Adapter.CartAdapter
import kh.edu.rupp.ite.mad_project.data.model.State
import kh.edu.rupp.ite.mad_project.databinding.FragemntCartBinding
import kh.edu.rupp.ite.mad_project.ui.viewmodel.CartViewModel
import kh.edu.rupp.ite.mad_project.utils.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartFragment : Fragment() {

    private lateinit var binding: FragemntCartBinding
    private lateinit var cartAdapter: CartAdapter

    private val viewModel by viewModels<CartViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragemntCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView and Adapter
        cartAdapter = CartAdapter(mutableListOf())
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewProducts.adapter = cartAdapter

        // Observe Cart State
        observeCartState()

        // Load Cart Items
        SharedPrefManager(requireContext()).userId?.let { userId ->
            viewModel.loadCart(userId)
        } ?: run {
            Toast.makeText(requireContext(), "User ID not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeCartState() {
        viewModel.cartState.observe(viewLifecycleOwner) { apiState ->
            when (apiState.state) {
                State.loading -> {
//                    binding.progressBar.visibili?ty = View.VISIBLE
                }
                State.success -> {
//                    binding.progressBar.visibility = View.GONE
                    cartAdapter.updateData(apiState.data!!)
                }
                State.error -> {
//                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), apiState.message, Toast.LENGTH_SHORT).show()
                }
                State.none -> {
                    // Optionally handle the 'none' state
//                    showLoading(false)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        binding = null
    }
}
