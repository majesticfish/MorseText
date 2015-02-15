package com.example.morsetext;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by Sean on 2/14/2015.
 */
public class VibrateSMSMessage extends IntentService{

    public VibrateSMSMessage() {
        super("vibrate");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        String message = intent.getStringExtra(Constants.message);
        vibrator.vibrate(MorseCalculator.stringToBuzz(message),-1);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        SMSReceiver.completeWakefulIntent(intent);
    }
}
