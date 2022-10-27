package com.radya.sfa.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.radya.sfa.R;


/**
 * Created by RadyaLabs PC on 08/02/2018.
 */

public class AlertUtils {

    private Context activity;

    public AlertUtils(Context activity) {
        this.activity = activity;
    }

    public interface positiveButton {
        void onYes(DialogInterface dialogInterface);
    }

    public interface negativeButton {
        void onNo(DialogInterface dialogInterface);
    }

    public void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialog);
        builder.setMessage(message);
        builder.show();
    }

    public void showAlert(String message, final positiveButton positiveButton, String positiveLabel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialog);
        builder.setMessage(message);
        builder.setPositiveButton(positiveLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (positiveButton != null) {
                    positiveButton.onYes(dialogInterface);
                }
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    public void showAlert(String message, final negativeButton negativeButton,
                          final positiveButton positiveButton, String negativeLabel, String positiveLabel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialog);
        builder.setMessage(message);
        builder.setNegativeButton(negativeLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (negativeButton != null) {
                    negativeButton.onNo(dialogInterface);
                }
            }
        });

        builder.setPositiveButton(positiveLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (positiveButton != null) {
                    positiveButton.onYes(dialogInterface);
                }
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

}
