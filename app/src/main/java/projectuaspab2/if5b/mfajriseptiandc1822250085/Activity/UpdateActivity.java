package projectuaspab2.if5b.mfajriseptiandc1822250085.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import projectuaspab2.if5b.mfajriseptiandc1822250085.API.APIRequestData;
import projectuaspab2.if5b.mfajriseptiandc1822250085.API.RetroServer;
import projectuaspab2.if5b.mfajriseptiandc1822250085.Model.ResponseModel;
import projectuaspab2.if5b.mfajriseptiandc1822250085.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {
    private EditText etNama, etSpesies,etHabitat,etIlmiah,etPernapasan;
    private Button btnUpdate;
    private int xId;
    private String xNama,xSpesies,xHabitat,xIlmiah,xPernapasan;
    private String yNama,ySpesies,yHabitat,yIlmiah,yPernapasan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intentTerima = getIntent();
        xId = intentTerima.getIntExtra("xId", -1);
        xNama = intentTerima.getStringExtra("xNama");
        xSpesies = intentTerima.getStringExtra("xSpesies");
        xHabitat = intentTerima.getStringExtra("xHabitat");
        xIlmiah = intentTerima.getStringExtra("xIlmiah");
        xPernapasan = intentTerima.getStringExtra("xPernapasan");

        etNama = findViewById(R.id.et_nama);
        etSpesies = findViewById(R.id.et_spesies);
        etHabitat = findViewById(R.id.et_habitat);
        etIlmiah = findViewById(R.id.et_ilmiah);
        etPernapasan = findViewById(R.id.et_pernapasan);
        btnUpdate = findViewById(R.id.btn_update);

        etNama.setText(xNama);
        etSpesies.setText(xSpesies);
        etHabitat.setText(xHabitat);
        etIlmiah.setText(xIlmiah);
        etPernapasan.setText(xPernapasan);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yNama = etNama.getText().toString();
                ySpesies = etSpesies.getText().toString();
                yHabitat = etHabitat.getText().toString();
                yIlmiah = etIlmiah.getText().toString();
                yPernapasan = etPernapasan.getText().toString();
                updateData();
            }
        });

    }
    private void updateData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> updateData = ardData.ardUpdateData(xId,yNama,ySpesies,yHabitat,yIlmiah,yPernapasan);

        updateData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UpdateActivity.this, "Data Berhasil Diupdate", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Gagal Mengupdate Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}