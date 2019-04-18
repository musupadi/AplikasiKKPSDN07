package com.destinyapp.aplikasisdn07.Admin.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.MainAdminActivity;
import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterVerifAbsenAdmin extends RecyclerView.Adapter<AdapterVerifAbsenAdmin.HolderData>{
    private List<DataModel> mList;
    private Context ctx;

    public AdapterVerifAbsenAdmin(Context ctx, List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_absen_admin,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVerifAbsenAdmin.HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        holderData.Sakit.setText(dm.getSakit());
        holderData.Izin.setText(dm.getIzin());
        holderData.Alpa.setText(dm.getAlpa());
        holderData.NIP.setText(dm.getNip());
        holderData.NIS.setText(dm.getNis());
        holderData.NamaSiswa.setText(dm.getNama_siswa());
        holderData.Verif.setText(dm.getVerif());
        holderData.NamaGuru.setText(dm.getNama());
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        LinearLayout LinearCard;
        TextView NIP,NIS,NamaSiswa,NamaGuru,Alpa,Izin,Sakit,Verif;
        DataModel dm;
        HolderData(View v){
            super(v);
            NIP = (TextView)v.findViewById(R.id.tvNIPPengajar);
            NIS = (TextView)v.findViewById(R.id.tvNISSiswa);
            NamaSiswa = (TextView)v.findViewById(R.id.tvNamaSiswa);
            Verif = (TextView)v.findViewById(R.id.tvVerif);
            NamaGuru = (TextView)v.findViewById(R.id.tvNamaPengajar);
            Alpa = (TextView)v.findViewById(R.id.tvAlpa);
            Izin = (TextView)v.findViewById(R.id.tvIzin);
            Sakit = (TextView)v.findViewById(R.id.tvSakit);
            LinearCard = (LinearLayout)v.findViewById(R.id.LinearCard);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String verif=dm.getVerif();
                    String nis=dm.getNis();
                    if(verif.equals("belum")){
                        Belum(verif,nis);
                    }else if(verif.equals("sudah")){
                        Sudah(verif,nis);
                    }else{
                        Toast.makeText(ctx,"Data Error",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    private void Sudah(String verif,String nis){
        Intent goInput = new Intent(ctx, MainAdminActivity.class);
        goInput.putExtra("UPDATE_ABSEN",nis);
        ctx.startActivities(new Intent[]{goInput});
    }
    private void Belum(final String verif, final String nis){
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("Apakah Anda ingin Memverivikasi Data ini ?")
                .setCancelable(false)
                .setPositiveButton("Verifikasi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UpdateVerif(verif,nis);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                //Set your icon here
                .setTitle("Perhatian !!!")
                .setIcon(R.drawable.ic_close_black_24dp);
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void UpdateVerif(final String verif,final String nis){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseModel> insertAbsen = api.UpdateVerifAbsen(nis,"sudah");
        insertAbsen.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                //Log.d("RETRO", "response : " + response.body().toString());
                String ress = response.body().getResponse();
                if (ress.equals("Update")){
                    Toast.makeText(ctx,"Data Absensi Berhasil Terupdate",Toast.LENGTH_SHORT).show();
                    Intent goInput = new Intent(ctx, MainAdminActivity.class);
                    goInput.putExtra("AbsenVerif","belum");
                    ctx.startActivities(new Intent[]{goInput});
                }else{
                    Toast.makeText(ctx,"Error Data Tidak berhasil Disimpan"+ress,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx,"Data Error When Insert Data",Toast.LENGTH_SHORT).show();
            }
        });
    }
}