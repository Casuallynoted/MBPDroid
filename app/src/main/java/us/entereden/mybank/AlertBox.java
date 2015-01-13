package us.entereden.mybank;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class AlertBox extends DialogFragment{
    private MainActivity.OnMyDialogClick _onMyDialogClick = null;


public AlertBox(MainActivity.OnMyDialogClick ref) {
      _onMyDialogClick = ref;
}

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_overdraft_account)
                .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Accepting Overcharge
                        _onMyDialogClick.onPositiveButtonClick();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        _onMyDialogClick.onNegativeButtonClick();

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

    }

}