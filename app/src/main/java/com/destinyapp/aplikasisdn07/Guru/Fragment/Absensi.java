package com.destinyapp.aplikasisdn07.Guru.Fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterKelasSpinner;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterNIS;
import com.destinyapp.aplikasisdn07.Guru.Adapter.AdapterNamaMapel;
import com.destinyapp.aplikasisdn07.Models.DataModel;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;
import com.destinyapp.aplikasisdn07.Session.DB_Helper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Absensi extends Fragment {

    DB_Helper dbHelper;
    String User="";
    Button Insert;
    Spinner Kelas;
    AutoCompleteTextView NIS,NamaMapel;
    EditText NamaSiswa,idMapel,izin,alpa,sakit;
    private List<DataModel> aList = new ArrayList<>();
    private AdapterKelasSpinner aSpinner;

    public Absensi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_absensi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Insert = (Button)view.findViewById(R.id.btnBeriAbsen);
        Kelas = (Spinner)view.findViewById(R.id.SpinnerKelasPenilaianGuru);
        NIS = (AutoCompleteTextView)view.findViewById(R.id.ACTNISPenilaianGuru);
        NamaSiswa = (EditText) view.findViewById(R.id.tvNamaSiswaPenilaianGuru);
        izin = (EditText)view.findViewById(R.id.etIzin);
        sakit = (EditText)view.findViewById(R.id.etSakit);
        alpa = (EditText)view.findViewById(R.id.etAlpa);
        getKelas();
        dbHelper = new DB_Helper(getActivity());
        Cursor cursor = dbHelper.checkSession();

        while (cursor.moveToNext()){
            User=cursor.getString(0);
        }

        aSpinner = new AdapterKelasSpinner(getActivity(),aList);
        Kelas.setAdapter(aSpinner);

        Kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataModel clickedItem = (DataModel) parent.getItemAtPosition(position);
                String clickedItemNamaKelas = clickedItem.getNama_kelas();
                GetIDKelas(clickedItemNamaKelas);
                kosong();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        NIS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String nis = NIS.getEditableText().toString();
                    getNamaFromNIS(nis);
                    AutoAbsen(nis);
                }
            }
        });
        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
    }
    private void insertData(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetKelas = api.insertAbsenSiswa(NIS.getEditableText().toString(),
                User,
                sakit.getText().toString(),
                izin.getText().toString(),
                alpa.getText().toString());
        GetKelas.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String ress=response.body().getResponse();
                if(ress.equals("Insert")){
                    Toast.makeText(getActivity(),"Absen berhasil Dimasukan",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Absen Sudah Pernah di isi",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Kelas Tidak Ditemukan",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getKelas(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetKelas = api.getKelas();
        GetKelas.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                aList=response.body().getResult();
                AdapterKelasSpinner adapter = new AdapterKelasSpinner(getActivity(),aList);
                Kelas.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Kelas Tidak Ditemukan",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void GetIDKelas(String NamaKelas){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getIDKelas = api.GetIDKelas(NamaKelas);
        getIDKelas.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String idKelas = response.body().getId_kelas();
                getAutoText(idKelas);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam getAUtoNISData",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAutoText(String idKelas){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getAuto = api.getAllSiswaFromGuru(idKelas);
        getAuto.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                aList = response.body().getResult();
                AdapterNIS adapterNIS = new AdapterNIS(getActivity(),aList);
                NIS.setAdapter(adapterNIS);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error pada getAutoText Method",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getNamaFromNIS(String nis){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getNama = api.getDataSiswa(nis);
        getNama.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String nama = response.body().getNama_siswa();
                NamaSiswa.setText(nama);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam getNamaFromNis Method",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void AutoAbsen(String nis){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> getNilai = api.getAbsenSiswa(nis);
        getNilai.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String Response = response.body().getResponse();
                if(Response.equals("Succes")){
                    izin.setText(response.body().getIzin());
                    alpa.setText(response.body().getAlpa());
                    sakit.setText(response.body().getSakit());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error di AutoNilai",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void kosong(){
        NIS.setText("");
        NamaSiswa.setText("");
    }
}
