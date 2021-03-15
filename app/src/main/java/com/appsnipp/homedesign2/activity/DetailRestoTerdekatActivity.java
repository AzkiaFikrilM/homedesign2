package com.appsnipp.homedesign2.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsnipp.homedesign2.R;
import com.squareup.picasso.Picasso;

public class DetailRestoTerdekatActivity extends AppCompatActivity {

    private String nama, kategori, desc, imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_resto_terdekat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView img_foto = findViewById(R.id.img_resto_dekat);
        TextView tv_name = findViewById(R.id.tv_name_dekat);
        TextView tv_desc = findViewById(R.id.tv_deskripsi_dekat);
        TextView tv_kategori = findViewById(R.id.tv_kategori_dekat);
        Button btn_booking = findViewById(R.id.btn_booking);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            nama = bundle.getString("nama");
            kategori = bundle.getString("kategori");
            desc = bundle.getString("deskripsi");
            imageUrl = bundle.getString("imageUrl");

            tv_name.setText(nama);
            tv_kategori.setText("Jarak: "+kategori);
            tv_desc.setText(desc);
            Picasso.get().load(imageUrl).placeholder(R.drawable.restaurant).into(img_foto);
            getSupportActionBar().setTitle(nama);
        }

        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Berhasil booking!", Toast.LENGTH_SHORT).show();
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