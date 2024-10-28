package kh.edu.rupp.ite.mad_project.Adapter;

import android.annotation.SuppressLint;
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

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private final List<HomeProducts> favoriteProducts;

    public FavoriteAdapter(List<HomeProducts> favoriteProducts) {
        this.favoriteProducts = favoriteProducts;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_item, parent, false);
        return new FavoriteViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        HomeProducts product = favoriteProducts.get(position);
        holder.titleTextView.setText(product.getTitle());
        holder.priceTextView.setText("$" + product.getPrice());

        String imageUrl = product.getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(holder.productImageView);
        } else {
            holder.productImageView.setImageResource(R.drawable.computer); // Placeholder image
        }

        // Set click listener for product item if needed
        holder.itemView.setOnClickListener(v -> {
            // Handle item click, for example, navigate to product details
        });
    }

    @Override
    public int getItemCount() {
        return favoriteProducts.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, priceTextView;
        ImageView productImageView;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.productTitle);
            priceTextView = itemView.findViewById(R.id.productPrice);
            productImageView = itemView.findViewById(R.id.product_image);
        }
    }
}
