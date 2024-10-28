package kh.edu.rupp.ite.mad_project.Adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kh.edu.rupp.ite.mad_project.R;
import kh.edu.rupp.ite.mad_project.model.HomeProducts;

public class HomeProductAdapter extends RecyclerView.Adapter<HomeProductAdapter.ProductViewHolder> {

    private final List<HomeProducts> productList;

    public HomeProductAdapter(List<HomeProducts> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_item, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        HomeProducts homeProduct = productList.get(position);

        holder.nameTextView.setText(homeProduct.getName());
        holder.titleTextView.setText(homeProduct.getTitle());
        holder.priceTextView.setText("$" + homeProduct.getPrice());

        // Load product image using Picasso
        String imageUrl = homeProduct.getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(holder.productImageView);
        } else {
            holder.productImageView.setImageResource(R.drawable.computer); // Default image
        }

        // Update heart icon based on favorite status
        holder.heartIcon.setImageResource(homeProduct.isFavorite() ? R.drawable.heart : R.drawable.myheart);

        // Click listener for the heart icon
        holder.heartIcon.setOnClickListener(v -> {
            // Toggle favorite status
            boolean newFavoriteStatus = !homeProduct.isFavorite();
            homeProduct.setFavorite(newFavoriteStatus);
            holder.heartIcon.setImageResource(newFavoriteStatus ? R.drawable.heart : R.drawable.myheart);

            // Optional: Log the new state or perform any additional action
            Log.d("HomeProductAdapter", "Product " + homeProduct.getName() + " is now " + (newFavoriteStatus ? "favorite" : "not favorite"));
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView, titleTextView;
        ImageView productImageView;
        ImageView heartIcon;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.productTitle);
            titleTextView = itemView.findViewById(R.id.productDesc);
            priceTextView = itemView.findViewById(R.id.productPrice);
            productImageView = itemView.findViewById(R.id.product_image);
            heartIcon = itemView.findViewById(R.id.heartIcon);
        }
    }
}
