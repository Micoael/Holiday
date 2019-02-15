package com.imzgw.holiday;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.imzgw.holiday.db.Holiday;
import com.imzgw.holiday.util.Util;
import com.imzgw.holiday.util.Values;

import java.util.Date;

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
        editText=findViewById(R.id.today);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(editText.getText()==null){
                    today=0;
                }else{
                    today=Integer.parseInt(editText.getText().toString());
                }
                showTodayTask();
                return false;
            }
        });
        today=getToday();
        showTodayTask();
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
