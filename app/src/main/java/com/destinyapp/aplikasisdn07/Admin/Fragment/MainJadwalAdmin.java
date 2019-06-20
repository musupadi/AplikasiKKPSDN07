package com.destinyapp.aplikasisdn07.Admin.Fragment;


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
import com.destinyapp.aplikasisdn07.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainJadwalAdmin extends Fragment {

    LinearLayout Input,Check;

    public MainJadwalAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_jadwal_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Input=(LinearLayout)view.findViewById(R.id.inputData);
        Check=(LinearLayout)view.findViewById(R.id.checkData);

        Input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Input();
            }
        });
        Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Output();
            }
        });
    }
    private void Input(){
        Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
        goInput.putExtra("INPUT_JADWAL","input_jadwal");
        getActivity().startActivities(new Intent[]{goInput});
    }
    private void Output(){
        Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
        goInput.putExtra("OUTPUT_JADWAL","output_jadwal");
        getActivity().startActivities(new Intent[]{goInput});
    }
}