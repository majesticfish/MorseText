package com.example.morsetext;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Set;


public class MainActivity extends ActionBarActivity {
    public static int currentBit = 0;
    public static int position = 4;
    public static String phonenumber = "425-445-8107";
    TextView currentMessage;
    TextView currentBits;
    TextView currentRecepient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        currentMessage = (TextView) findViewById(R.id.textView2);
        currentBits = (TextView) findViewById(R.id.textView3);
        currentRecepient = (TextView) findViewById(R.id.textView);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        checkBox.setChecked(prefs.getBoolean("receiverOn", true));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefs.edit().putBoolean("receiverOn", isChecked).apply();
            }
        });
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
        if((int)view.getId()==2131230784){
            currentBit += (int)Math.pow(2,position);
            currentBits.setText(currentBits.getText()+"1");
        }
        else{
            currentBits.setText(currentBits.getText()+"0");
        }
        if(position==0){
            //add to string
            //clear current bits
            currentBits.setText("");
            currentMessage.setText(currentMessage.getText()+String.valueOf(MorseCalculator.intToChar(currentBit)));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            switch(requestCode){
                case contactPickerResult:
                    Bundle extras = data.getExtras();
                    /*Set keys = extras.keySet();
                    Iterator iterate = keys.iterator();
                    int counter = 0;
                    while(iterate.hasNext()){
                        String key = (String) iterate.next();
                        System.out.println("This is a result: " + key+ "Number " + counter);
                        counter ++;
                    }*/
                    Uri result = data.getData();
                    System.out.println("GOT A RESULT!! : " + result.getLastPathSegment());
                    break;
            }
        }else{
            System.out.println("Something happened!");
        }
    }
}
