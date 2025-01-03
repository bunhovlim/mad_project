package kh.edu.rupp.ite.mad_project.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

import kh.edu.rupp.ite.mad_project.R;
import kh.edu.rupp.ite.mad_project.api.ApiManager;
import kh.edu.rupp.ite.mad_project.model.HomeProducts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView productRecyclerView;
    private RecyclerView categoryRecyclerView;
    private HomeProductAdapter productAdapter;
    private HomeModelAdapter categoryAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Setting up Product RecyclerView
        productRecyclerView = view.findViewById(R.id.place_products);
        int numberOfColumns = 2;
        productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        fetchProducts();  // Fetch products from API

        // Setting up Category RecyclerView
        categoryRecyclerView = view.findViewById(R.id.position_categories);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        setupCategoryRecyclerView();  // Set up category names

        return view;
    }

    private void fetchProducts() {
        ApiManager.getInstance().getApiService().getProducts().enqueue(new Callback<List<HomeProducts>>() {
            @Override
            public void onResponse(@NonNull Call<List<HomeProducts>> call, @NonNull Response<List<HomeProducts>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productAdapter = new HomeProductAdapter(response.body());
                    productRecyclerView.setAdapter(productAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<HomeProducts>> call, @NonNull Throwable t) {
                // Handle failure
            }
        });
    }

    private void setupCategoryRecyclerView() {
        // Sample static data for categories
        List<String> categoryNames = Arrays.asList("Dell", "Asus", "Msi", "Acer");
        categoryAdapter = new HomeModelAdapter(categoryNames, this::navigateToFragmentShowCategory);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    // Navigate to FragmentShowCategory when a category is clicked
    private void navigateToFragmentShowCategory(String categoryName) {
        FragmentShowModel fragmentShowCategory = FragmentShowModel.newInstance(categoryName);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragmentShowCategory);  // Replace with actual container ID
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
