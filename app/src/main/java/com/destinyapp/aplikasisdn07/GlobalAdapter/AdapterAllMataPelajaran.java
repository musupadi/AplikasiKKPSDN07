package com.destinyapp.aplikasisdn07.GlobalAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.destinyapp.aplikasisdn07.Guru.MainGuruActivity;
import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.R;

import java.util.List;

public class AdapterAllMataPelajaran extends RecyclerView.Adapter<AdapterAllMataPelajaran.HolderData> {
    private List<DataModel> mList;
    private Context ctx;

    public AdapterAllMataPelajaran (Context ctx, List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_data_mapel,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        holderData.idMapel.setText(dm.getId_mapel());
        holderData.NamaMapel.setText(dm.getNama_mapel());
        holderData.tingkatMapel.setText(dm.getTingkat_kelas());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class HolderData extends RecyclerView.ViewHolder{
        TextView idMapel,NamaMapel,tingkatMapel;
        DataModel dm;
        public HolderData(View v){
            super(v);
            idMapel = (TextView)v.findViewById(R.id.tvIDMapel);
            NamaMapel = (TextView)v.findViewById(R.id.tvNamaMapel);
            tingkatMapel = (TextView) v.findViewById(R.id.tvTingkatMapel);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}