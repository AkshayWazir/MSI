package org.lol.wazirbuild.msilib;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class OpenDialog extends DialogFragment {
    private OpenDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("I hope the data you fed was correct.")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.OnYesClicked();
                    }
                })
                .setNegativeButton("Revise", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

    public interface OpenDialogListener {
        void OnYesClicked();
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            listener = (OpenDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "Must Implement the method");
        }
    }
}
