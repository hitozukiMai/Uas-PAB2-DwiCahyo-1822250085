package projectuaspab2.if5b.mfajriseptiandc1822250085.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import projectuaspab2.if5b.mfajriseptiandc1822250085.API.APIRequestData;
import projectuaspab2.if5b.mfajriseptiandc1822250085.API.RetroServer;
import projectuaspab2.if5b.mfajriseptiandc1822250085.Activity.AddActivity;
import projectuaspab2.if5b.mfajriseptiandc1822250085.Activity.MainActivity;
import projectuaspab2.if5b.mfajriseptiandc1822250085.Activity.UpdateActivity;
import projectuaspab2.if5b.mfajriseptiandc1822250085.Model.DataModel;
import projectuaspab2.if5b.mfajriseptiandc1822250085.Model.ResponseModel;
import projectuaspab2.if5b.mfajriseptiandc1822250085.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData>{
    private Context context;
    private List<DataModel> listData;
    private List<DataModel> listH;
    private int id;

    public AdapterData(Context context, List<DataModel> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModel dm = listData.get(position);

        holder.tvId.setText(String.valueOf(dm.getId()));
        holder.tvNama.setText(dm.getNama());
        holder.tvSpesies.setText("Spesies : " + dm.getSpesies());
        holder.tvHabitat.setText("Habitat : " + dm.getHabitat());
        holder.tvNilmiah.setText("Nama Ilmiah : " + dm.getIlmiah());
        holder.tvPernapasan.setText("Organ Pernapasan : " + dm.getPernapasan());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvId,tvNama,tvSpesies,tvHabitat,tvNilmiah,tvPernapasan;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvSpesies = itemView.findViewById(R.id.tv_spesies);
            tvHabitat = itemView.findViewById(R.id.tv_habitat);
            tvNilmiah = itemView.findViewById(R.id.tv_ilmiah);
            tvPernapasan = itemView.findViewById(R.id.tv_pernapasan);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(context);
                    dialogPesan.setTitle("Perhatian");
                    dialogPesan.setIcon(R.drawable.ic_round_android_24);
                    dialogPesan.setMessage("Apakah anda ingin untuk");
                    dialogPesan.setCancelable(true);
                    id = Integer.parseInt(tvId.getText().toString());

                    dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteData();
                            dialog.dismiss();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((MainActivity)context).retriveData();
                                }
                            }, 1000);
                        }
                    });

                    dialogPesan.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getData();
                            dialog.dismiss();
                        }
                    });
                    dialogPesan.show();
                    return false;
                }
            });

        }
        private void deleteData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> deleteData = ardData.ardDeleteData(id);

            deleteData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    Toast.makeText(context, "Berhasil Menghapus Data", Toast.LENGTH_SHORT).show();

                }
                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(context, "Gagal Menghapus data", Toast.LENGTH_SHORT).show();

                }
            });
        }

        private void getData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> getData = ardData.ardGetData(id);

            getData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    listH = response.body().getData();

                    int vId = listH.get(0).getId();
                    String vNama = listH.get(0).getNama();
                    String vSpesies = listH.get(0).getSpesies();
                    String vHabitat = listH.get(0).getHabitat();
                    String vIlmiah = listH.get(0).getIlmiah();
                    String vPernapasan = listH.get(0).getPernapasan();

                    //Toast.makeText(context, vNama + " " + vSpesies + " " + vHabitat + " "+ vIlmiah + " "+ vPernapasan, Toast.LENGTH_SHORT).show();

                    Intent intentKirim = new Intent(context, UpdateActivity.class);
                    intentKirim.putExtra("xId", vId);
                    intentKirim.putExtra("xNama", vNama);
                    intentKirim.putExtra("xSpesies", vSpesies);
                    intentKirim.putExtra("xHabitat", vHabitat);
                    intentKirim.putExtra("xIlmiah", vIlmiah);
                    intentKirim.putExtra("xPernapasan", vPernapasan);
                    context.startActivity(intentKirim);

                }
                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(context, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
