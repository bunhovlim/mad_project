package kh.edu.rupp.ite.mad_project.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kh.edu.rupp.ite.mad_project.R;
import kh.edu.rupp.ite.mad_project.model.Favorite;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private final List<Favorite> favourites;
    private OnRecyclerViewItemClickListener listener;

    public FavoriteAdapter(List<Favorite> favourites) {
        this.favourites = favourites;
    }

    public void setListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favorite favourite = favourites.get(position);
        holder.bind(favourite);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }

        void bind(Favorite favourite) {
            nameTextView.setText(favourite.getName()); // Adjust this based on your Favourite model
        }
    }
}
