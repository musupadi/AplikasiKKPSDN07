package com.destinyapp.aplikasisdn07.Guru.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.destinyapp.aplikasisdn07.Admin.MainAdminActivity;
import com.destinyapp.aplikasisdn07.Guru.MainGuruActivity;
import com.destinyapp.aplikasisdn07.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPenilaian extends Fragment {

    LinearLayout Nilai,Absen;

    public MainPenilaian() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_penilaian, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Nilai=(LinearLayout)view.findViewById(R.id.inputData);
        Absen=(LinearLayout)view.findViewById(R.id.checkData);

        Nilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputNilai();
            }
        });
        Absen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputAbsen();
            }
        });
    }
    private void InputNilai(){
        Intent goInput = new Intent(getActivity(), MainGuruActivity.class);
        goInput.putExtra("INPUT_NILAI","input_nilai");
        getActivity().startActivities(new Intent[]{goInput});
    }
    private void InputAbsen(){
        Intent goInput = new Intent(getActivity(), MainGuruActivity.class);
        goInput.putExtra("INPUT_ABSEN","input_absen");
        getActivity().startActivities(new Intent[]{goInput});
    }
}
