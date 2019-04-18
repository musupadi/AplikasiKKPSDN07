package com.destinyapp.aplikasisdn07.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.destinyapp.aplikasisdn07.R;

public class DialogUpdateAbsen extends AppCompatDialogFragment{
    EditText Alpa,Sakit,Izin;
    private DialogUpdateInterface dialogUpdateInterface;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_update_absen,null);
        Alpa = view.findViewById(R.id.etAlpa);
        Sakit = view.findViewById(R.id.etSakit);
        Izin = view.findViewById(R.id.etIzin);
        builder.setView(view)
                .setTitle("")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String alpa = Alpa.getText().toString();
                        String sakit = Sakit.getText().toString();
                        String izin = Izin.getText().toString();
                        dialogUpdateInterface.applyText(alpa,sakit,izin);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogUpdateInterface = (DialogUpdateInterface) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "Must Implement ExampleDialogListener");
        }


    }

    public interface DialogUpdateInterface{
        void applyText(String Alpa,String Izin,String Sakit);
    }
}
