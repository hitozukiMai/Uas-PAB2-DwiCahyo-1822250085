package projectuaspab2.if5b.mfajriseptiandc1822250085.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import projectuaspab2.if5b.mfajriseptiandc1822250085.API.APIRequestData;
import projectuaspab2.if5b.mfajriseptiandc1822250085.API.RetroServer;
import projectuaspab2.if5b.mfajriseptiandc1822250085.Adapter.AdapterData;
import projectuaspab2.if5b.mfajriseptiandc1822250085.Model.ResponseModel;
import projectuaspab2.if5b.mfajriseptiandc1822250085.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {
    private EditText etNama, etSpesies,etHabitat,etIlmiah,etPernapasan;
    private Button btnSimpan;
    private String nama,spesies,habitat,ilmiah,pernapasan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etNama = findViewById(R.id.et_nama);
        etSpesies = findViewById(R.id.et_spesies);
        etHabitat = findViewById(R.id.et_habitat);
        etIlmiah = findViewById(R.id.et_ilmiah);
        etPernapasan = findViewById(R.id.et_pernapasan);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                spesies = etSpesies.getText().toString();
                habitat = etHabitat.getText().toString();
                ilmiah = etIlmiah.getText().toString();
                pernapasan = etPernapasan.getText().toString();

                if(nama.trim().equals("")){
                    etNama.setError("Nama Tidak Boleh Kosong!");
                }
                else if(spesies.trim().equals("")){
                    etSpesies.setError("Spesies Tidak Boleh Kosong!");
                }
                else if(habitat.trim().equals("")){
                    etHabitat.setError("Tempat Habitat Tidak Boleh Kosong!");
                }
                else if(ilmiah.trim().equals("")){
                    etIlmiah.setError("Nama Ilmiah Tidak Boleh Kosong!");
                }
                else if(pernapasan.trim().equals("")){
                    etPernapasan.setError("Organ Pernapasan Hewan Tidak Boleh Kosong!");
                }
                else{
                    createData();
                }
            }
        });
    }

    private void createData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> addData = ardData.ardCreateData(nama,spesies,habitat,ilmiah,pernapasan);

        addData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(AddActivity.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(AddActivity.this, "Gagal Menyimpan Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}