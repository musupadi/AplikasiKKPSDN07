package com.destinyapp.aplikasisdn07.Admin.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

public class AdapterVerifNilaiAdmin extends RecyclerView.Adapter<AdapterVerifNilaiAdmin.HolderData>{
    private List<DataModel> mList;
    private Context ctx;

    public AdapterVerifNilaiAdmin(Context ctx, List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_nilai_admin,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVerifNilaiAdmin.HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        holderData.Nilai.setText(dm.getNilai());
        holderData.NIP.setText(dm.getNip());
        holderData.NIS.setText(dm.getNis());
        holderData.NamaSiswa.setText(dm.getNama_siswa());
        holderData.NamaMapel.setText(dm.getNama_mapel());
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
        TextView NIP,NIS,NamaSiswa,NamaGuru,NamaMapel,Nilai,Verif;
        DataModel dm;
        HolderData(View v){
            super(v);
            NIP = (TextView)v.findViewById(R.id.tvNIP);
            NIS = (TextView)v.findViewById(R.id.tvNIS);
            NamaSiswa = (TextView)v.findViewById(R.id.tvNamaSiswa);
            NamaMapel = (TextView)v.findViewById(R.id.tvNamaMapel);
            Verif = (TextView)v.findViewById(R.id.tvVerif);
            NamaGuru = (TextView)v.findViewById(R.id.tvNamaPengajar);
            Nilai = (TextView)v.findViewById(R.id.tvNilai);
            LinearCard = (LinearLayout)v.findViewById(R.id.LinearCard);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String verif=dm.getVerif();
                    String nis=dm.getNis();
                    String id_mapel=dm.getId_mapel();
                    if(verif.equals("belum")){
                        Belum(verif,nis,id_mapel);
                    }else if(verif.equals("sudah")){
                        Sudah(verif,nis,id_mapel);
                    }else{
                        Toast.makeText(ctx,"Data Error",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    private void Sudah(String verif,String nis,String idMapel){
        Intent goInput = new Intent(ctx, MainAdminActivity.class);
        goInput.putExtra("UPDATE_NILAI",nis);
        goInput.putExtra("UPDATE_IDMAPEL",idMapel);
        ctx.startActivities(new Intent[]{goInput});
    }
    private void Belum(final String verif, final String nis,final String id_mapel){
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("Apakah Anda ingin Memverivikasi Data ini ?")
                .setCancelable(false)
                .setPositiveButton("Verifikasi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UpdateVerif(verif,nis,id_mapel);
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
    private void UpdateVerif(String verif,String nis,String id_mapel){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseModel> insertAbsen = api.UpdateVerifNilai(nis,"sudah",id_mapel);
        insertAbsen.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String ress = response.body().getResponse();
                if (ress.equals("Update")){
                    Toast.makeText(ctx,"Data Nilai Berhasil Terupdate",Toast.LENGTH_SHORT).show();
                    Intent goInput = new Intent(ctx, MainAdminActivity.class);
                    goInput.putExtra("NilaiVerif","belum");
                    ctx.startActivities(new Intent[]{goInput});
                }else{
                    Toast.makeText(ctx,"Error Data Tidak berhasil Disimpan",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx,"Data Error When Insert Data",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
