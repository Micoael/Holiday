package com.imzgw.holiday;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.imzgw.holiday.db.EverydayEvent;
import com.imzgw.holiday.db.Holiday;
import com.imzgw.holiday.util.Util;
import com.imzgw.holiday.util.Values;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TextView showTask;
    EditText editText;
    int today;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showTask=findViewById(R.id.task_show);
        showTask.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DataSupport.deleteAll(EverydayEvent.class);
                return false;
            }
        });



        today=getToday();
        showTodayTask();
    }

    @Override
    protected void onResume() {
        today=getToday();
        showTodayTask();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_button:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,AddActivity.class);
                startActivity(intent);
                break;
            case R.id.add_config_button:
                Intent intents = new Intent();
                intents.setClass(MainActivity.this,ConfigActivity.class);
                startActivity(intents);
                break;
            default:
                break;
        }
        return true;
    }



    private void showTodayTask(){

        showTask.setText(Util.showText(Util.readStack(),today));
    }
    private int getToday(){
        Holiday holiday = readHolidayMainfist();
        Log.d(TAG, "getToday: "+Util.getTodayIndicator(holiday));
        return Util.getTodayIndicator(holiday);
    }
    private Holiday readHolidayMainfist(){
        Log.d(TAG, "readHolidayMainfist");
        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        Date startDate=new Date(
                pref.getString(Values.START_DATE,"Feb 14,2004"
                ));
        Date endDate = new Date(
                pref.getString(Values.END_DATE,"Feb 14,2005"
                ));
        boolean bool=pref.getBoolean(Values.HAD_RUN,false);
        Log.d(TAG, "readHolidayMainfist: isBoolean "+bool);
        if(bool){
            Log.d(TAG, "readHolidayMainfist: 1st branch");
            Log.d(TAG, "readHolidayMainfist: "+startDate.toString()+""+endDate.toString());
            return new Holiday(startDate,endDate,pref.getString(Values.HOLIDAY_NAME,"null"));
        }else{
            Log.d(TAG, "readHolidayMainfist: Next branch");
            startNewActivityForConfig();
            return new Holiday(startDate,endDate,pref.getString(Values.HOLIDAY_NAME,"null"));
        }

    }
    private void startNewActivityForConfig(){
        Log.d(TAG, "startNewActivityForConfig");
        Intent intent= new Intent();
        intent.setClass(MainActivity.this,ConfigActivity.class);
        startActivity(intent);
    }
}
