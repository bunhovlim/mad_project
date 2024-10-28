package kh.edu.rupp.ite.mad_project.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import kh.edu.rupp.ite.mad_project.databinding.ViewHolderFavoriteBinding;
import kh.edu.rupp.ite.mad_project.model.Favorite;

public class FavoriteViewHolder extends RecyclerView.ViewHolder {

    private final ViewHolderFavoriteBinding binding;
    private final OnRecyclerViewItemClickListener listener;

    public FavoriteViewHolder(@NonNull ViewHolderFavoriteBinding binding, OnRecyclerViewItemClickListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;

        binding.getRoot().setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(getAdapterPosition());
            }
        });
    }

    public void bind(Favorite favourite) {
        binding.productName.setText(favourite.getProductName());
        binding.productBrand.setText(favourite.getProductBrand());
        binding.productPrice.setText(String.valueOf(favourite.getProductPrice()));
        Glide.with(binding.productImage.getContext())
                .load(favourite.getProductImageUrl())
                .into(binding.productImage);
    }
}
