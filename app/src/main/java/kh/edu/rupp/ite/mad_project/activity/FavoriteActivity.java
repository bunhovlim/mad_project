package kh.edu.rupp.ite.mad_project.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kh.edu.rupp.ite.mad_project.Adapter.FavoriteAdapter;
import kh.edu.rupp.ite.mad_project.Adapter.OnRecyclerViewItemClickListener;
import kh.edu.rupp.ite.mad_project.R;
import kh.edu.rupp.ite.mad_project.api.ApiManager;
import kh.edu.rupp.ite.mad_project.model.Favorite;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteAdapter favouriteAdapter;
    private List<Favorite> favouriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = findViewById(R.id.recyclerView);
        favouriteList = new ArrayList<>();
        favouriteAdapter = new FavoriteAdapter(favouriteList);

        favouriteAdapter.setListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(favouriteAdapter);

        fetchFavorites();
    }

    private void fetchFavorites() {
        ApiManager.getInstance().getApiService().getFavorites().enqueue(new Callback<List<Favorite>>() {
            @Override
            public void onResponse(Call<List<Favorite>> call, Response<List<Favorite>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    favouriteList.addAll(response.body());
                    favouriteAdapter.notifyDataSetChanged();
                    Log.d("FavouriteActivity", "Fetched favorites: " + favouriteList);
                } else {
                    Toast.makeText(FavoriteActivity.this, "Failed to fetch favorites", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Favorite>> call, Throwable t) {
                Log.e("FavouriteActivity", "Error fetching favorites", t);
                Toast.makeText(FavoriteActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
