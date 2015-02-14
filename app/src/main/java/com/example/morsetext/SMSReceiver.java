package com.example.morsetext;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by Sean on 2/14/2015.
 */
public class SMSReceiver extends BroadcastReceiver {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle myBundle = intent.getExtras();
        SmsMessage[] messages = null;
        String strMessage = "";
        if(myBundle != null){
            Object[] pdus =(Object[]) myBundle.get("pdus");
            messages = new SmsMessage[pdus.length];
            for(int i = 0; i < messages.length; i ++){
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                strMessage += "SMS From: " + messages[i].getOriginatingAddress();
                strMessage += " : ";
                strMessage += messages[i].getMessageBody();
                strMessage += "\n";
            }
            Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
