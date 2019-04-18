package com.destinyapp.aplikasisdn07.Admin.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.R;

import java.util.List;

public class AdapterDataJadwalAdmin extends RecyclerView.Adapter<AdapterDataJadwalAdmin.HolderData> {
    private List<DataModel> mList;
    private Context ctx;

    public AdapterDataJadwalAdmin (Context ctx,List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_data_jadwal,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataJadwalAdmin.HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        holderData.nip.setText(dm.getNip());
        holderData.namaGuru.setText(dm.getNama());
        holderData.kelas.setText(dm.getNama_kelas());
        holderData.mapel.setText(dm.getNama_mapel());
        holderData.dariJam.setText(dm.getDari_jam());
        holderData.sampaiJam.setText(dm.getSampai_jam());
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView nip,namaGuru,kelas,mapel,dariJam,sampaiJam;
        DataModel dm;
        HolderData(View v){
            super(v);
            nip = (TextView)v.findViewById(R.id.tvNIP);
            namaGuru = (TextView)v.findViewById(R.id.tvNamaPengajar);
            kelas = (TextView)v.findViewById(R.id.tvKelas);
            mapel = (TextView)v.findViewById(R.id.tvMapel);
            dariJam = (TextView)v.findViewById(R.id.tvDariJam);
            sampaiJam = (TextView)v.findViewById(R.id.tvSampaiJam);
        }
    }
}