package kh.edu.rupp.ite.mad_project.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kh.edu.rupp.ite.mad_project.R
import kh.edu.rupp.ite.mad_project.model.FavoriteProductResponse

class FavoriteProductAdapter(private var favoriteProducts: MutableList<FavoriteProductResponse>) : RecyclerView.Adapter<FavoriteProductAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.tvProductName)
        val productPrice: TextView = view.findViewById(R.id.tvProductPrice)
        val imgProduct: ImageView = view.findViewById(R.id.imgProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoriteProduct = favoriteProducts[position]
        holder.productName.text = favoriteProduct.product.name
        holder.productPrice.text = favoriteProduct.product.price

        // Load the image using Glide
        Glide.with(holder.itemView.context)
            .load(favoriteProduct.product.image)
            .into(holder.imgProduct)
    }

    override fun getItemCount() = favoriteProducts.size

    fun updateData(newFavoriteProducts: List<FavoriteProductResponse>) {
        favoriteProducts.clear()
        favoriteProducts.addAll(newFavoriteProducts)
        notifyDataSetChanged()
    }
}
