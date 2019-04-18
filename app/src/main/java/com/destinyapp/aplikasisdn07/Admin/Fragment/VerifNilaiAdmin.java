package com.destinyapp.aplikasisdn07.Admin.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterVerifAbsenAdmin;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterVerifNilaiAdmin;
import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerifNilaiAdmin extends Fragment {

    TextView TittleMencari,TittleRecycler;
    RecyclerView verif;
    private RecyclerView.Adapter mAdapter;
    private List<DataModel> mItems = new ArrayList<>();
    private RecyclerView.LayoutManager mManager;

    public VerifNilaiAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verif_nilai_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TittleMencari = (TextView)view.findViewById(R.id.tvTittleMencari);

        TittleMencari = (TextView)view.findViewById(R.id.tvTittleMencari);
        TittleRecycler = (TextView)view.findViewById(R.id.tvTittleRecycler);
        verif = (RecyclerView)view.findViewById(R.id.recyclerBelumNilai);
        mManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        verif.setLayoutManager(mManager);
        String Verifikasi = this.getArguments().getString("KEY_VERIF_NILAI").toString();
        if (Verifikasi.equals("belum")){
            BelumVerif();
        }else if(Verifikasi.equals("sudah")){
            SudahVerif();
        }
        DataVerif(Verifikasi);
    }
    private void DataVerif(String Verif){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetAllDataGuru = api.getVerifNilai(Verif);
        GetAllDataGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                mAdapter = new AdapterVerifNilaiAdmin(getActivity(),mItems);
                verif.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error pada Method getAllGuru",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void BelumVerif(){
        TittleRecycler.setText("Data Nilai Yang Belum Terverifikasi");
        TittleMencari.setText("Mencari Data Nilai Yang Belum Terverifikasi");
    }
    private void SudahVerif(){
        TittleRecycler.setText("Data Nilai Yang Sudah Terverifikasi");
        TittleMencari.setText("Mencari Nilai Absen Yang Sudah Terverifikasi");
    }
}
