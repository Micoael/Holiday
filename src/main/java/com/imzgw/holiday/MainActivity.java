package com.imzgw.holiday;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.imzgw.holiday.db.EverydayEvent;
import com.imzgw.holiday.db.Holiday;
import com.imzgw.holiday.util.Util;
import com.imzgw.holiday.util.Values;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapters.EventAdapters;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TextView showTask,holidayInfo;
    Holiday holiday;
    int today;
    List<EverydayEvent> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        holidayInfo=findViewById(R.id.holiday_info);
        showTask=findViewById(R.id.task_show);

        today=getToday();
        showTodayTask();
        getHolidayStat();

    }

    @Override
    protected void onResume() {
        refresh();
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
                Intent intent2 = new Intent();
                intent2.setClass(MainActivity.this,ConfigActivity.class);
                startActivity(intent2);
                break;
            case R.id.delete:
                Intent intent3 = new Intent();
                intent3.setClass(MainActivity.this,DeleteActivity.class);
                startActivity(intent3);
            default:
                break;
        }
        return true;
    }



    private void showTodayTask(){
        list=Util.readStack();
        Log.d(TAG, "showTodayTask: List size is "+list.size());
        showTask.setText(Util.showText(list,today));
    }
    private int getToday(){
        holiday = readHolidayMainfist();
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

    public void refresh(){
        today=getToday();
        list.clear();
        list.addAll(Util.readStack());
        showTodayTask();
    }
    public void getHolidayStat(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!(holiday ==null)){
                    holidayInfo.setText(
                            "你的假期已经过了"+
                            Util.getProgress(holiday)+"%. \n"+
                            "它将于"+ String.format(holiday.getEndDate().toString(),"%tF%n")
                            +"结束."
                    );

                }
            }
        });
    }
}
