package com.ifyou.nowincinema.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.ifyou.nowincinema.R;
import com.ifyou.nowincinema.common.InterfaceCommunicator;

/**
 * Created by Baranov on 02.05.2017.
 **/

public class CityPickerFragment extends DialogFragment {

    public InterfaceCommunicator interfaceCommunicator;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            interfaceCommunicator = (InterfaceCommunicator) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement InterfaceCommunicator");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.city)
                .setItems(R.array.city_actions, (dialog, which) -> {
                    interfaceCommunicator.sendRequestCode(which);
                    dialog.dismiss();
                })
                .create();
    }
}
