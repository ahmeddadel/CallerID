package com.dolla.callerid;


import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;
import android.widget.Toast;

public class Dialog extends Activity {
    String callerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        callerID = getIntent().getStringExtra("callerID");
        final AlertDialog.Builder builder = new AlertDialog.Builder(Dialog.this);
        builder.setTitle("Incoming Call From:\n" + callerID);
        builder.setMessage("");
        builder.setPositiveButton("OK", (dialogInterface, i) -> finish());
        final AlertDialog alert = builder.create();
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        alert.show();
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                alert.setMessage("Dismiss: " + l / 1000);
            }

            @Override
            public void onFinish() {
                alert.dismiss();
                finish();
            }
        }.start();
    }
}