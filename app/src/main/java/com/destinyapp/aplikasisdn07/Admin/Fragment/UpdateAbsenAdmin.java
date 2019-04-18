package com.destinyapp.aplikasisdn07.Admin.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.MainAdminActivity;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateAbsenAdmin extends Fragment {

    EditText sakit,izin,alpa;
    Button Update;

    public UpdateAbsenAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_absen_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String NIS = this.getArguments().getString("KEY_NIS").toString();
        sakit = (EditText)view.findViewById(R.id.etSakit);
        izin = (EditText)view.findViewById(R.id.etIzin);
        alpa = (EditText)view.findViewById(R.id.etAlpa);
        Update = (Button)view.findViewById(R.id.btnUpdate);
        getDataRaport(NIS);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData(NIS);
            }
        });
    }
    private void getDataRaport(String NIS){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseModel> getRaport = api.getRaportData(NIS);
        getRaport.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                sakit.setText(response.body().getSakit());
                izin.setText(response.body().getIzin());
                alpa.setText(response.body().getAlpa());
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error When Insert Data",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void UpdateData(String NIS){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseModel> updateAbsen = api.UpdateDataAbsen(NIS,sakit.getText().toString(),izin.getText().toString(),alpa.getText().toString());
        updateAbsen.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String ress = response.body().getResponse();
                if (ress.equals("Update")){
                    Toast.makeText(getActivity(),"Data Absensi Berhasil Terupdate",Toast.LENGTH_SHORT).show();
                    Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                    goInput.putExtra("AbsenVerif","sudah");
                    getActivity().startActivities(new Intent[]{goInput});
                }else{
                    Toast.makeText(getActivity(),"Error Data Tidak berhasil Disimpan",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error When Insert Data",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
