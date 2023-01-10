package projectuaspab2.if5b.mfajriseptiandc1822250085.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.icu.lang.UProperty;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import projectuaspab2.if5b.mfajriseptiandc1822250085.API.APIRequestData;
import projectuaspab2.if5b.mfajriseptiandc1822250085.API.RetroServer;
import projectuaspab2.if5b.mfajriseptiandc1822250085.Adapter.AdapterData;
import projectuaspab2.if5b.mfajriseptiandc1822250085.Model.DataModel;
import projectuaspab2.if5b.mfajriseptiandc1822250085.Model.ResponseModel;
import projectuaspab2.if5b.mfajriseptiandc1822250085.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvData;
    private RecyclerView.Adapter adData;
    private RecyclerView.LayoutManager lmData;
    private List<DataModel> listData;
    private SwipeRefreshLayout srlData;
    private ProgressBar pbLoading;
    private FloatingActionButton fabTambah;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvData = findViewById(R.id.rv_data);
        srlData = findViewById(R.id.srl_data);
        pbLoading = findViewById(R.id.pb_loading);
        fabTambah = findViewById(R.id.fab_tambah);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        retriveData();

        srlData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlData.setRefreshing(true);
                retriveData();
                srlData.setRefreshing(false);
            }
        });

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });

    }

    public void retriveData(){
        ShowProgressBar();
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> tampilData = ardData.ardRetriveData();

        tampilData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                //Toast.makeText(MainActivity.this, "Data Loaded", Toast.LENGTH_SHORT).show();
                listData = response.body().getData();

                adData = new AdapterData(MainActivity.this, listData);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();
                HideProgressBar();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                HideProgressBar();
            }
        });
    }

    private void ShowProgressBar(){
        pbLoading.setVisibility(View.VISIBLE);
    }

    private void HideProgressBar(){
        pbLoading.setVisibility(View.GONE);
    }
}