package com.png.wolibapracticaltask.core.common;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressBarDialog {
    private static ProgressDialog progressDialog;

    public static void show(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
        }
        progressDialog.show();
    }

    public static void hide() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
