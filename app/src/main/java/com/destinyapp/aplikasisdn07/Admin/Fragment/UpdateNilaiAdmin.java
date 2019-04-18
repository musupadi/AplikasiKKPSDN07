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
public class UpdateNilaiAdmin extends Fragment {

    EditText Nilai;
    Button Update;

    public UpdateNilaiAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_nilai_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String NIS = this.getArguments().getString("KEY_NIS").toString();
        final String ID_Mapel = this.getArguments().getString("KEY_MAPEL").toString();
        Nilai = (EditText)view.findViewById(R.id.etNilai);
        Update = (Button)view.findViewById(R.id.btnUpdate);
        getDataNilai(NIS,ID_Mapel);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData(NIS,ID_Mapel);
            }
        });
    }
    private void getDataNilai(String NIS,String ID_Mapel){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseModel> getRaport = api.GetNilai(NIS,ID_Mapel);
        getRaport.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Nilai.setText(response.body().getNilai());
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error When Insert Data",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void UpdateData(String NIS,String ID_Mapel){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseModel> getRaport = api.updateNilaiSiswa(NIS,Nilai.getText().toString(),ID_Mapel);
        getRaport.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Toast.makeText(getActivity(),"Data Berhasil Terupdate",Toast.LENGTH_SHORT).show();
                Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                goInput.putExtra("NilaiVerif","sudah");
                getActivity().startActivities(new Intent[]{goInput});
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Data Error When Insert Data",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
