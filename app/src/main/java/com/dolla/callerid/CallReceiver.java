package com.dolla.callerid;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;

import java.util.ArrayList;


public class CallReceiver extends BroadcastReceiver {
    String callerID;
    ArrayList<String> contactName = new ArrayList<>();
    ArrayList<String> contactNumber = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1 = new Intent();
        intent1.setClassName(context.getPackageName(), "com.dolla.callerid.Dialog");
        intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
        String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            if (number != null) {
                addContacts(context);
                for (int i = 0; i < contactNumber.size(); i++) {
                    if (number.trim().equals(contactNumber.get(i)))
                        callerID = contactName.get(i);
                    else if (callerID == null)
                        callerID = "Unknown number";
                }
                intent1.putExtra("callerID", callerID);
                context.startActivity(intent1);
            }
        }
    }

    public void addContacts(Context context) {
        try {
            Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactName.add(name);
                contactNumber.add(phoneNumber);
            }
            phones.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}