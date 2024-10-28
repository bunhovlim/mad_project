package kh.edu.rupp.ite.mad_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kh.edu.rupp.ite.mad_project.Adapter.FavoriteAdapter; // Change to your FavoriteAdapter
import kh.edu.rupp.ite.mad_project.R;
import kh.edu.rupp.ite.mad_project.api.ApiManager;
import kh.edu.rupp.ite.mad_project.model.HomeProducts;
import kh.edu.rupp.ite.mad_project.utils.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter; // Changed to FavoriteAdapter
    private List<HomeProducts> favoriteProducts;
    private SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize SharedPrefManager to get userId
        sharedPrefManager = new SharedPrefManager(requireContext());

        // Fetch favorite products using userId
        String userId = sharedPrefManager.getUserId();
        if (userId != null && !userId.isEmpty()) {
            fetchFavoriteProducts(userId);
        } else {
            // Handle the case where userId is not available (e.g., show an error message)
        }

        return view;
    }

    private void fetchFavoriteProducts(String userId) {
        ApiManager.getInstance().getApiService().getFavoriteProducts(userId).enqueue(new Callback<List<HomeProducts>>() {
            @Override
            public void onResponse(@NonNull Call<List<HomeProducts>> call, @NonNull Response<List<HomeProducts>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    favoriteProducts = response.body();

                    // Initialize FavoriteAdapter and set it to RecyclerView
                    favoriteAdapter = new FavoriteAdapter(favoriteProducts);
                    recyclerView.setAdapter(favoriteAdapter);
                } else {
                    // Handle response error (e.g., log or show a message)
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<HomeProducts>> call, @NonNull Throwable t) {
                // Handle failure (e.g., log or show a message)
            }
        });
    }
}
