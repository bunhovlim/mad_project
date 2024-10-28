package kh.edu.rupp.ite.mad_project.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import kh.edu.rupp.ite.mad_project.R;

public class HomeModelAdapter extends RecyclerView.Adapter<HomeModelAdapter.CategoryViewHolder> {

    private final List<String> categoryList;
    private final OnCategoryClickListener listener;

    public HomeModelAdapter(List<String> categoryList, OnCategoryClickListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_model, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String categoryName = categoryList.get(position);
        holder.categoryName.setText(categoryName);

        // Pass the clicked category to the listener
        holder.itemView.setOnClickListener(v -> listener.onCategoryClick(categoryName));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.Model);
        }
    }

    // Interface for handling category clicks
    public interface OnCategoryClickListener {
        void onCategoryClick(String categoryName);
    }
}
