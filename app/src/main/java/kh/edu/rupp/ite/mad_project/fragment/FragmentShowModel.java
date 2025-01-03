package kh.edu.rupp.ite.mad_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kh.edu.rupp.ite.mad_project.R;
import kh.edu.rupp.ite.mad_project.api.ApiManager;
import kh.edu.rupp.ite.mad_project.model.HomeProducts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentShowModel extends Fragment {

    private static final String ARG_MODEL_NAME = "Model_name";
    private RecyclerView productsRecyclerView;
    private HomeProductAdapter productAdapter;
    private String categoryName;

    public static FragmentShowModel newInstance(String categoryName) {
        FragmentShowModel fragment = new FragmentShowModel();
        Bundle args = new Bundle();
        args.putString(ARG_MODEL_NAME, categoryName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryName = getArguments().getString(ARG_MODEL_NAME);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_categories, container, false);

        productsRecyclerView = view.findViewById(R.id.show_item_categories);

        int numberOfColumns = 2;
        productsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));

        fetchProductsByCategory(categoryName);
        LinearLayout backFromShowCategories = view.findViewById(R.id.backFromShowCategories);
        backFromShowCategories.setOnClickListener(v -> navigateBackToHomeFragment());

        TextView categoryTextView = view.findViewById(R.id.textViewCategoryName_Model);
        categoryTextView.setText(categoryName);
        return view;
    }

    private void fetchProductsByCategory(String Models) {
        ApiManager.getInstance().getApiService().getProductsByCategory(Models).enqueue(new Callback<List<HomeProducts>>() {
            @Override
            public void onResponse(@NonNull Call<List<HomeProducts>> call, @NonNull Response<List<HomeProducts>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productAdapter = new HomeProductAdapter(response.body());
                    productsRecyclerView.setAdapter(productAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<HomeProducts>> call, @NonNull Throwable t) {

            }
        });
    }
    private void navigateBackToHomeFragment() {
        HomeFragment fragmentHome = new HomeFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmentHome)
                .addToBackStack(null)
                .commit();
    }
}
