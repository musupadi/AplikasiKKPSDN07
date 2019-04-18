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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterDataGuruAdmin;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterDataJadwalAdmin;
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
public class DataJadwal extends Fragment {
    String id_kelas;
    Button cari;
    Spinner Kelas,Hari;
    RecyclerView DataJadwal;
    private RecyclerView.Adapter mAdapter;
    private List<DataModel> mItems = new ArrayList<>();
    private RecyclerView.LayoutManager mManager;
    public DataJadwal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_jadwal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DataJadwal = (RecyclerView)view.findViewById(R.id.recyclerDataJadwalAdmin);
        Kelas = (Spinner)view.findViewById(R.id.SpinnerKelas);
        Hari = (Spinner)view.findViewById(R.id.SpinnerHari);
        cari = (Button)view.findViewById(R.id.btnMencari);
        mManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        DataJadwal.setLayoutManager(mManager);
        getAllJadwalData();
        getKelas();
        getHariSpinner();
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MencariJadwal(id_kelas);
            }
        });
        Kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataModel clickedItem = (DataModel) parent.getItemAtPosition(position);
                String clickedItemNamaKelas = clickedItem.getNama_kelas();
                GetIDKelas(clickedItemNamaKelas);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void GetIDKelas(String NamaKelas){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getIDKelas = api.GetIDKelas(NamaKelas);
        getIDKelas.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                id_kelas = response.body().getId_kelas();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam GetIDKelas",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void MencariJadwal(final String id_kelas){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetAllDataGuru = api.getAllJadwalSpec(id_kelas,Hari.getSelectedItem().toString());
        GetAllDataGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                mAdapter = new AdapterDataJadwalAdmin(getActivity(),mItems);
                DataJadwal.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error pada Method getAllGuru"+id_kelas+Hari.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAllJadwalData(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetAllDataGuru = api.getAllJadwal();
        GetAllDataGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                mItems=response.body().getResult();
                mAdapter = new AdapterDataJadwalAdmin(getActivity(),mItems);
                DataJadwal.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error pada Method getAllGuru",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getKelas(){
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
    private void getHariSpinner(){
        List<String> TK = new ArrayList<>();
        TK.add(0,"Pilih Hari Jadwal");
        TK.add("Senin");
        TK.add("Selasa");
        TK.add("Rabu");
        TK.add("Kamis");
        TK.add("Jum'at");
        TK.add("Sabtu");
        TK.add("Minggu");
        ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,TK);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Hari.setAdapter(dataAdapter);
    }
}
