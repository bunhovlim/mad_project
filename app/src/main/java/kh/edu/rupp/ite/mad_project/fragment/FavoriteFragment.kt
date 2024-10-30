package kh.edu.rupp.ite.mad_project.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.mad_project.Adapter.FavoriteProductAdapter
import kh.edu.rupp.ite.mad_project.R
import kh.edu.rupp.ite.mad_project.api.ApiManager
import kh.edu.rupp.ite.mad_project.model.FavoriteProductResponse
import kh.edu.rupp.ite.mad_project.utils.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavoriteProductAdapter
    private var favoriteProducts: MutableList<FavoriteProductResponse> = mutableListOf()
    private lateinit var sharedPrefManager: SharedPrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize SharedPrefManager to get userId
        sharedPrefManager = SharedPrefManager(requireContext())

        // Initialize the adapter with an empty list
        favoriteAdapter = FavoriteProductAdapter(favoriteProducts)
        recyclerView.adapter = favoriteAdapter

        // Fetch favorite products using userId
        val userId = sharedPrefManager.getUserId()
        if (!userId.isNullOrEmpty()) {
            fetchFavoriteProducts(userId)
        } else {
            Log.e("FavoriteFragment", "User ID is not available.")
            Toast.makeText(requireContext(), "User ID is not available.", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun fetchFavoriteProducts(userId: String) {
        val apiService = ApiManager.getInstance().apiService
        apiService.getFavoriteProducts(userId).enqueue(object : Callback<List<FavoriteProductResponse>> {
            override fun onResponse(call: Call<List<FavoriteProductResponse>>, response: Response<List<FavoriteProductResponse>>) {
                if (response.isSuccessful && response.body() != null) {
                    val newFavoriteProducts = response.body()!!

                    // Update the adapter with the new data
                    favoriteAdapter.updateData(newFavoriteProducts)
                } else {
                    Log.e("FavoriteFragment", "Response was unsuccessful or empty. Code: ${response.code()}")
                    Toast.makeText(requireContext(), "Failed to load favorites.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<FavoriteProductResponse>>, t: Throwable) {
                Log.e("FavoriteFragment", "Failed to load favorite products: ${t.message}")
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
