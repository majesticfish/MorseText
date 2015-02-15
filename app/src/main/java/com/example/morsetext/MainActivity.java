package com.example.morsetext;

import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    public static int currentBit = 0;
    public static int position = 4;
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
}
