package com.destinyapp.aplikasisdn07.Admin.Fragment;


import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.Adapter.AdapterDataGuruAdmin;
import com.destinyapp.aplikasisdn07.Admin.MainAdminActivity;
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
public class InputDataGuruAdmin extends Fragment {

    EditText nip,password,nama,TempatLahir,tgl,bulan,tahun,agama,notelp,jabatan,pendidikan,alamat;
    Spinner jk;
    Button insert;
    String jenisKelamin;
    String defaultPPGuru = "logo.png";
    private RecyclerView.Adapter mAdapter;
    private List<DataModel> mItems = new ArrayList<>();
    private RecyclerView.LayoutManager mManager;


    public InputDataGuruAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_data_guru_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nip = (EditText)view.findViewById(R.id.etNIPGuruAdmin);
        password = (EditText)view.findViewById(R.id.etPasswordGuruAdmin);
        nama = (EditText)view.findViewById(R.id.etNamaGuruAdmin);
        TempatLahir = (EditText)view.findViewById(R.id.etTempatLahirGuruAdmin);
        tgl = (EditText)view.findViewById(R.id.etTglLahirGuruAdmin);
        bulan = (EditText)view.findViewById(R.id.etBulanLahirGuruAdmin);
        tahun = (EditText)view.findViewById(R.id.etTahunLahirGuruAdmin);
        agama = (EditText)view.findViewById(R.id.etAgamaGuruAdmin);
        notelp = (EditText)view.findViewById(R.id.etTeleponGuruAdmin);
        jabatan = (EditText)view.findViewById(R.id.etJabatanGuruAdmin);
        pendidikan = (EditText)view.findViewById(R.id.etPendidikanGuruAdmin);
        jk = (Spinner) view.findViewById(R.id.SpinnerKelamin);
        alamat = (EditText)view.findViewById(R.id.etAlamatGuruAdmin);
        insert = (Button)view.findViewById(R.id.btnInputDataGuruAdmin);

        //getAllGuru();

        List<String> JK = new ArrayList<>();
        JK.add(0,"Pilih Jenis Kelamin");
        JK.add("Pria");
        JK.add("Wanita");

        ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,JK);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jk.setAdapter(dataAdapter);
        jk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Pilih Jenis Kelamin")){
                    jenisKelamin = "Pilih Jenis Kelamin";
                }else{
                    jenisKelamin = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Update Insert
        String UI = this.getArguments().getString("KEY_UI").toString();
        String KEY_NIP = this.getArguments().getString("KEY_NIP").toString();
        if (UI.equals("Update")){
            Update(KEY_NIP);
        }else if(UI.equals("Insert")){
            insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertGuru();
                }
            });
        }
    }
    private void Update(String NIP){
        getDataGuru(NIP);
        insert.setText("Update");
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData();
            }
        });
    }
    private void UpdateData(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> insertDataGuru = api.updateDataGuru(nip.getText().toString(),
                password.getText().toString(),
                nama.getText().toString(),
                TempatLahir.getText().toString(),
                getTanggal(),
                agama.getText().toString(),
                notelp.getText().toString(),
                jabatan.getText().toString(),
                pendidikan.getText().toString(),
                jenisKelamin,
                defaultPPGuru,
                alamat.getText().toString());
        insertDataGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Toast.makeText(getActivity(),"Data Berhasil Diupdate",Toast.LENGTH_SHORT).show();
                Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                goInput.putExtra("OUTPUT_GURU","input");
                getActivity().startActivities(new Intent[]{goInput});
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam Method InsertData",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getDataGuru(String NIP){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> GetData = api.getNamaFromNip(NIP);
        GetData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                nip.setText(response.body().getNip());
                nama.setText(response.body().getNama());
                password.setText(response.body().getPassword());
                TempatLahir.setText(response.body().getTempatlahir());
                agama.setText(response.body().getAgama());
                notelp.setText(response.body().getNotelp());
                jabatan.setText(response.body().getJabatan());
                pendidikan.setText(response.body().getPendidikan());
                if (response.body().getJk().equals("Pria")){
                    jk.setSelection(1);
                }else if(response.body().getJk().equals("Wanita")){
                    jk.setSelection(2);
                }
                alamat.setText(response.body().getAlamat());
                Toast.makeText(getActivity(),response.body().getNip(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getTanggal(){
        String tanggal = tahun.getText().toString()+"-"+bulan.getText().toString()+"-"+tgl.getText().toString();
        return tanggal;
    }
    private void insertGuru(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> insertDataGuru = api.insrertDataGuru(nip.getText().toString(),
                password.getText().toString(),
                nama.getText().toString(),
                TempatLahir.getText().toString(),
                getTanggal(),
                agama.getText().toString(),
                notelp.getText().toString(),
                jabatan.getText().toString(),
                pendidikan.getText().toString(),
                jenisKelamin,
                defaultPPGuru,
                alamat.getText().toString());
        insertDataGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Toast.makeText(getActivity(),"Data Berhasil Disimpan",Toast.LENGTH_SHORT).show();
                Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                goInput.putExtra("OUTPUT_GURU","input");
                getActivity().startActivities(new Intent[]{goInput});
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error dalam Method InsertData",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
