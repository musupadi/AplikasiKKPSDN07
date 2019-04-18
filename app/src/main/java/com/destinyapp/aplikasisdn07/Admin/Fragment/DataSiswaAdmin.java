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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterDataGuruAdmin;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterGuruAutoNama;
import com.destinyapp.aplikasisdn07.GlobalAdapter.AdapterAutoTextDataSiswaNama;
import com.destinyapp.aplikasisdn07.GlobalAdapter.AdapterAutoTextDataSiswaNis;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterGetAllSiswa;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterKelasSpinner;
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
public class DataSiswaAdmin extends Fragment {

    Button cari;
    AutoCompleteTextView NIS,Nama;
    Spinner Kelas;
    private RecyclerView.Adapter mAdapter;
    private List<DataModel> mItems = new ArrayList<>();
    private RecyclerView.LayoutManager mManager;
    RecyclerView DataSiswa;

    public DataSiswaAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_siswa_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DataSiswa= (RecyclerView)view.findViewById(R.id.recyclerDataSiswaAdmin);
        Kelas= (Spinner)view.findViewById(R.id.SpinnerKelas);
        NIS = (AutoCompleteTextView)view.findViewById(R.id.ACTNIS);
        Nama = (AutoCompleteTextView)view.findViewById(R.id.ACTNamaSiswa);
        cari = (Button)view.findViewById(R.id.btnCari);
        mManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        DataSiswa.setLayoutManager(mManager);
        getSpinnerKelas();
        getNamaSiswaAutoText();
        getNisSiswaAutoText();
        getAllSiswa();
        OnClickListener();
    }
    private void MencariDataFromDATA(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getMapel = api.getDataSiswa(NIS.getEditableText().toString(),Nama.getEditableText().toString());
        getMapel.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                mAdapter = new AdapterGetAllSiswa(getActivity(),mItems);
                DataSiswa.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Change(String C){
        if (C.equals("NIS")){
            ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
            Call<ResponseModel> getMapel = api.getSiswaAutoText(NIS.getEditableText().toString(),"");
            getMapel.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    Nama.setText(response.body().getNama_siswa());
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(getActivity(),"Data Error di getMapelAutotext",Toast.LENGTH_SHORT).show();
                }
            });
        }else if(C.equals("Nama")){
            ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
            Call<ResponseModel> getMapel = api.getSiswaAutoText("",Nama.getEditableText().toString());
            getMapel.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    NIS.setText(response.body().getNis());
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(getActivity(),"Data Error di getMapelAutotext",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void OnClickListener(){
        NIS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Change("NIS");
                }
            }
        });
        Nama.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Change("Nama");
                }
            }
        });
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MencariDataFromDATA();
            }
        });
    }
    private void getNisSiswaAutoText(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getMapel = api.getAllSiswa();
        getMapel.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterAutoTextDataSiswaNis adapter = new AdapterAutoTextDataSiswaNis(getActivity(),mItems);
                NIS.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error di getMapelAutotext",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getNamaSiswaAutoText(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getMapel = api.getAllSiswa();
        getMapel.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterAutoTextDataSiswaNama adapter = new AdapterAutoTextDataSiswaNama(getActivity(),mItems);
                Nama.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error di getMapelAutotext",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getSpinnerKelas(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetKelas = api.getKelas();
        GetKelas.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterKelasSpinner adapter = new AdapterKelasSpinner(getActivity(),mItems);
                Kelas.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Kelas Tidak Ditemukan",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllSiswa(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetAllDataGuru = api.getAllSiswa();
        GetAllDataGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                mAdapter = new AdapterGetAllSiswa(getActivity(),mItems);
                DataSiswa.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error pada Method getAllGuru",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
