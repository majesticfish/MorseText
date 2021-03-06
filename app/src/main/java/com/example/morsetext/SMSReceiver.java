package com.example.morsetext;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by Sean on 2/14/2015.
 */
public class SMSReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean("receiverOn", true)) {
            Bundle myBundle = intent.getExtras();
            SmsMessage[] messages = null;
            String strMessage = "", message = "";
            if (myBundle != null) {
                Object[] pdus = (Object[]) myBundle.get("pdus");
                messages = new SmsMessage[pdus.length];
                for (int i = 0; i < messages.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    strMessage += "SMS From: " + messages[i].getOriginatingAddress();
//               message += "From: " + messages[i].getOriginatingAddress();
                    strMessage += " : ";
                    strMessage += messages[i].getMessageBody();
                    message += messages[i].getMessageBody();
                    strMessage += "\n";
                }
                Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(context, VibrateSMSMessage.class);
                intent2.putExtra(Constants.message, message);
                startWakefulService(context,intent2);
            }
        }
    }
}
