package com.destinyapp.aplikasisdn07.GlobalAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.R;

import java.util.List;

public class AdapterAllKelas extends RecyclerView.Adapter<AdapterAllKelas.HolderData> {
    private List<DataModel> mList;
    private Context ctx;

    public AdapterAllKelas (Context ctx, List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_data_kelas,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        holderData.NamaKelas.setText(dm.getNama_kelas());
        holderData.TingkatKelas.setText(dm.getTingkat_kelas());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class HolderData extends RecyclerView.ViewHolder{
        TextView NamaKelas,TingkatKelas;
        DataModel dm;
        public HolderData(View v){
            super(v);
            NamaKelas = (TextView)v.findViewById(R.id.tvNamaKelas);
            TingkatKelas = (TextView)v.findViewById(R.id.tvTingkatKelas);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
