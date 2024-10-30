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
import kh.edu.rupp.ite.mad_project.Adapter.CartAdapter
import kh.edu.rupp.ite.mad_project.R
import kh.edu.rupp.ite.mad_project.api.ApiManager
import kh.edu.rupp.ite.mad_project.model.CartResponse
import kh.edu.rupp.ite.mad_project.utils.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardFragment : Fragment() {

    private lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter // Assuming you have created CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragemnt_cart, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewProducts)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        cartAdapter = CartAdapter(mutableListOf())  // Start with an empty list
        recyclerView.adapter = cartAdapter
        sharedPrefManager = SharedPrefManager(requireContext())

        // Replace "user_id" with actual user ID
        val userId = sharedPrefManager.userId
        if (userId != null) {
            fetchCartItems(userId)
        }
//        fetchCartItems("user_id")
        return view
    }


    private fun fetchCartItems(userId: String) {
        val apiService = ApiManager.getInstance().apiService
        apiService.getUserCart(userId).enqueue(object : Callback<List<CartResponse>> {
            override fun onResponse(call: Call<List<CartResponse>>, response: Response<List<CartResponse>>) {
                if (response.isSuccessful && response.body() != null) {
                    val cartItems = response.body()!!
                    cartAdapter.updateData(cartItems)
                } else {
                    Log.d("CardFragment", "Failed to load cart data. Code: ${response.code()}")
                    Log.d("CardFragment", "Error Body: ${response.errorBody()?.string()}")
                    Toast.makeText(requireContext(), "Failed to load cart data", Toast.LENGTH_SHORT).show()
                }
            }



            override fun onFailure(call: Call<List<CartResponse>>, t: Throwable) {
                Log.d("CardFragment", "Network error: ${t.message}")
                Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
