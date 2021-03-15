package com.appsnipp.homedesign2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.homedesign2.R;
import com.appsnipp.homedesign2.activity.DetailRestoTerdekatActivity;
import com.appsnipp.homedesign2.adapter.ProductAdapter;
import com.appsnipp.homedesign2.model.ProductModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class RestoDiskonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<ProductModel> arrayList;

    TextView tv_kosong;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeRefreshLayout;

    DatabaseReference databaseReference;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_diskon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Restoran Diskon");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("restoDiskon");
        databaseReference = FirebaseDatabase.getInstance().getReference("restoDiskon");

        recyclerView = findViewById(R.id.recycler_view_2);
        progressBar = findViewById(R.id.progress_bar_2);
        tv_kosong = findViewById(R.id.tv_kosong_diskon);
        progressDialog = new ProgressDialog(this);

        swipeRefreshLayout = findViewById(R.id.swipe_diskon);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primaryTextColor), getResources().getColor(R.color.primaryTextColor));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addData();
            }
        });

        tv_kosong.setVisibility(View.INVISIBLE);

        progressBar.setVisibility(View.VISIBLE);
        addData();

    }

    private void addData() {
        progressBar.setVisibility(View.VISIBLE);
        arrayList = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductModel productModel = dataSnapshot.getValue(ProductModel.class);
                    arrayList.add(new ProductModel(productModel.getFotoProduk(), productModel.getNamaProduk(), productModel.getDeskripsiProduk(), productModel.getKategoriProduk()));
                }
                adapter = new ProductAdapter(getApplicationContext(), arrayList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemViewCacheSize(20);
                recyclerView.setDrawingCacheEnabled(true);
                recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        String kategori = arrayList.get(position).getKategoriProduk();
                        String nama = arrayList.get(position).getNamaProduk();
                        String imageUrl = arrayList.get(position).getFotoProduk();
                        String deskripsi = arrayList.get(position).getDeskripsiProduk();
                        Intent intent = new Intent(getApplicationContext(), DetailRestoTerdekatActivity.class);
                        intent.putExtra("nama", nama);
                        intent.putExtra("kategori", kategori);
                        intent.putExtra("imageUrl", imageUrl);
                        intent.putExtra("deskripsi", deskripsi);
                        startActivity(intent);
                    }
                });
                progressBar.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);

                if (arrayList.isEmpty()) {
                    tv_kosong.setVisibility(View.VISIBLE);
                } else {
                    tv_kosong.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}