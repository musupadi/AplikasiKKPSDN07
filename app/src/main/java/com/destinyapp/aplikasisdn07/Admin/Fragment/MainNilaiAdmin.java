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
public class MainNilaiAdmin extends Fragment {

    LinearLayout BelumAbsen,SudahAbsen,BelumNilai,SudahNilai;
    public MainNilaiAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_nilai_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BelumAbsen = (LinearLayout)view.findViewById(R.id.belumAbsen);
        SudahAbsen = (LinearLayout)view.findViewById(R.id.sudahAbsen);
        BelumNilai = (LinearLayout)view.findViewById(R.id.belumNilai);
        SudahNilai = (LinearLayout)view.findViewById(R.id.sudahNilai);

        BelumAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                goInput.putExtra("AbsenVerif","belum");
                getActivity().startActivities(new Intent[]{goInput});
            }
        });

        SudahAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                goInput.putExtra("AbsenVerif","sudah");
                getActivity().startActivities(new Intent[]{goInput});
            }
        });
        BelumNilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                goInput.putExtra("NilaiVerif","belum");
                getActivity().startActivities(new Intent[]{goInput});
            }
        });
        SudahNilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goInput = new Intent(getActivity(), MainAdminActivity.class);
                goInput.putExtra("NilaiVerif","sudah");
                getActivity().startActivities(new Intent[]{goInput});
            }
        });

    }
}
