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
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterDataGuruAdmin;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterGuruAutoNama;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterGuruAutoNip;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterNamaMapel;
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
public class DataGuruAdmin extends Fragment {

    Button input;
    AutoCompleteTextView NamaGuru,NIP;
    private RecyclerView.Adapter mAdapter;
    private List<DataModel> mItems = new ArrayList<>();
    private RecyclerView.LayoutManager mManager;
    RecyclerView DataGuru;
    public DataGuruAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_guru_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DataGuru = (RecyclerView)view.findViewById(R.id.recyclerDataGuruAdmin);
        NamaGuru = (AutoCompleteTextView)view.findViewById(R.id.ACTNama);
        NIP = (AutoCompleteTextView)view.findViewById(R.id.ACTNIP);
        input = (Button)view.findViewById(R.id.btnMencari);

        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mencariData();
            }
        });

        getAllGuru();
        getAutoTextNama();
        //getAutoTextNip();
        NamaGuru.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    getNipFromNama();
                }
            }
        });
        NIP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    getNamaFromNip();
                }
            }
        });

        mManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        DataGuru.setLayoutManager(mManager);
    }
    private void mencariData(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GuruData = api.getDataGuru(NamaGuru.getEditableText().toString(),NIP.getEditableText().toString());
        GuruData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body().getKode().equals("1")){
                    mItems=response.body().getResult();
                    mAdapter = new AdapterDataGuruAdmin(getActivity(),mItems);
                    DataGuru.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }else if(response.body().getKode().equals("0")){
                    Toast.makeText(getActivity(),"Data Tidak Ditemukan",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error pada Method getAllGuru",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllGuru(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetAllDataGuru = api.getAllDataGuru();
        GetAllDataGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                mAdapter = new AdapterDataGuruAdmin(getActivity(),mItems);
                DataGuru.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error pada Method getAllGuru",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAutoTextNama(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getGuru = api.getAllDataGuru();
        getGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterGuruAutoNama adapter = new AdapterGuruAutoNama(getActivity(),mItems);
                NamaGuru.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error di getMapelAutotext",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAutoTextNip(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getGuru = api.getAllDataGuru();
        getGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                AdapterGuruAutoNip adapter = new AdapterGuruAutoNip(getActivity(),mItems);
                NamaGuru.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error di getMapelAutotext",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getNipFromNama(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getGuru = api.getGuru(NamaGuru.getEditableText().toString(),"");
        getGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (NIP.getEditableText().toString().isEmpty()){
                    NIP.setText(response.body().getNip());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error di getMapelAutotext",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getNamaFromNip(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getGuru = api.getNamaFromNip(NIP.getEditableText().toString());
        getGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                NamaGuru.setText(response.body().getNama());
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                //Toast.makeText(getActivity(),"Data Error di getMapelAutotext",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
