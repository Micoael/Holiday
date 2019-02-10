package com.imzgw.holiday;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.imzgw.holiday.db.Holiday;
import com.imzgw.holiday.util.Util;
import com.imzgw.holiday.util.Values;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
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
        return Util.getTodayIndicator(holiday);
    }
    private Holiday readHolidayMainfist(){
        SharedPreferences pref = getSharedPreferences("name",MODE_PRIVATE);
        Date startDate=new Date(
                pref.getString(Values.START_DATE,"Feb 14,2004"
                ));
        Date endDate = new Date(
                pref.getString(Values.END_DATE,"Feb 14,2005"
                ));
        if(!pref.getBoolean(Values.HAD_RUN,false)){
            return new Holiday(startDate,endDate,pref.getString(Values.HOLIDAY_NAME,"null"));
        }else{
            startNewActivityForConfig();
            return null;
        }

    }
    private void startNewActivityForConfig(){

    }
}
