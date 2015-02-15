package com.example.morsetext;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import java.util.Iterator;
import java.util.Set;


public class MainActivity extends ActionBarActivity {
    public static int currentBit = 0;
    public static int position = 4;
    public static String phonenumber = "";
    TextView currentMessage;
    TextView currentBits;
    TextView currentRecepient;
    Button sendButton;
    public static boolean hasMessage = false;
    public static boolean hasNumber = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentMessage = (TextView) findViewById(R.id.textView2);
        currentBits = (TextView) findViewById(R.id.textView3);
        currentRecepient = (TextView) findViewById(R.id.textView);
        sendButton = (Button) findViewById(R.id.button4);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        checkBox.setChecked(prefs.getBoolean("receiverOn", true));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefs.edit().putBoolean("receiverOn", isChecked).apply();
            }
        });
        sendButton.setEnabled(false);
    }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addBit(View view){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("Held");
                        break;
                    case MotionEvent.ACTION_UP:
                        double difference = SystemClock.uptimeMillis() - Constants.Time;
                        if(difference > 200){
                            currentBit += (int)Math.pow(2,position);
                            currentBits.setText(currentBits.getText()+"1");
                        }else{
                            currentBits.setText(currentBits.getText()+"0");
                        }
                        break;
                }
                return false;
            }
        });
        /*
        if((int)view.getId()==2131230784){
            currentBit += (int)Math.pow(2,position);
            currentBits.setText(currentBits.getText()+"1");
        }
        else{
            currentBits.setText(currentBits.getText()+"0");
        }*/
        if(position==0){
            //add to string
            //clear current bits
            currentBits.setText("");
            currentMessage.setText(currentMessage.getText()+String.valueOf(MorseCalculator.intToChar(currentBit)));
            hasMessage = true;
            if(hasMessage&&hasNumber) sendButton.setEnabled(true);
            currentBit = 0;
            position = 5;
        }
        position--;
    }
    public void backspace(View view){
        if(currentMessage.length()>0){
            String temp = currentMessage.getText().toString();
            currentMessage.setText(temp.substring(0,temp.length()-1));
        }
        if(currentMessage.getText().toString().length()==0){
            sendButton.setEnabled(false);
            hasMessage = false;
        }
    }
    public void sendSMS(View view){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phonenumber,null,currentMessage.getText().toString(),null,null);
        currentMessage.setText("");
    }
    final int contactPickerResult = 1001;
    public void doLaunchContactPicker(View view){
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, contactPickerResult);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK){
            Uri result = data.getData();
            Cursor c = getContentResolver().query(result, null, null, null, null);
            String contactId = "";
            if(c.moveToFirst()){
                contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                currentRecepient.setText("Recepient: "+name);
            }

            Cursor c1 = getContentResolver().query(ContactsContract.Data.CONTENT_URI,new String[] {ContactsContract.CommonDataKinds.Phone.NUMBER, Phone.TYPE},
                    ContactsContract.Data.CONTACT_ID + "=?",new String[]{String.valueOf(contactId)},null);
            if(c1.moveToFirst()) {
                final int contactNumberColumnIndex = c1.getColumnIndex(Phone.NUMBER);
                final int contactTypeColumnIndex = c1.getColumnIndex(Phone.TYPE);
                int typeLabelResource = 0;
                while(!c1.isAfterLast()){
                    final String number = c1.getString(contactNumberColumnIndex);
                    final int type = c1.getInt(contactTypeColumnIndex);
                    typeLabelResource = Phone.getTypeLabelResource(type);
                    System.out.println("Number is: "+ number+ " Type is "+ typeLabelResource);
                    phonenumber = number;
                    if(typeLabelResource==Phone.getTypeLabelResource(2)) break;
                    c1.moveToNext();
                }
                if(typeLabelResource!=Phone.getTypeLabelResource(2)){
                    hasNumber=false;
                    sendButton.setEnabled(false);
                }
                else {
                    hasNumber = true;
                    if (hasMessage && hasNumber) sendButton.setEnabled(true);
                }
            }
            else{
                hasNumber = false;
                sendButton.setEnabled(false);
            }
        }
    }
}
