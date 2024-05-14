package com.techindia.mgggggggg.Contest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MyAlertDialog {
    public static void showAlert(Context context, String title, String message,
                                 String positiveButtonText, String negativeButtonText,
                                 DialogInterface.OnClickListener positiveListener,
                                 DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        // Set positive button and listener
        builder.setPositiveButton(positiveButtonText, positiveListener);

        // Set negative button and listener
        builder.setNegativeButton(negativeButtonText, negativeListener);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
