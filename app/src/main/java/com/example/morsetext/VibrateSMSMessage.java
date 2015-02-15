package com.example.morsetext;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Sean on 2/14/2015.
 */
import android.os.PowerManager.WakeLock;
public class VibrateSMSMessage extends IntentService{

    public VibrateSMSMessage() {
        super("vibrate");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        WakeLock wakeLock = ((PowerManager)getSystemService(Context.POWER_SERVICE)).newWakeLock(26,"Tag");
       // wakeLock.acquire();
        //.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        wakeLock.acquire();
        System.out.println("I'm here");
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        System.out.println("I'm here");
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        String message = intent.getStringExtra(Constants.message);
        vibrator.vibrate(MorseCalculator.stringToBuzz(message),-1);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        wakeLock.release();
        //SMSReceiver.completeWakefulIntent(intent);
    }
}
