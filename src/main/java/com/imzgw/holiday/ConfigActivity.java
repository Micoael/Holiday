 package com.imzgw.holiday;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imzgw.holiday.util.Values;

import java.util.Calendar;
import java.util.Date;

 public class ConfigActivity extends AppCompatActivity {
    EditText startYear,startMonth,startDay,endYear,endMonth,endDay;
    TextView holidayName;
    Button startToggleToday,endToggleToday,save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        startYear=findViewById(R.id.start_year_input);
        startMonth=findViewById(R.id.start_month_input);
        startDay=findViewById(R.id.start_day_input);
        endDay=findViewById(R.id.end_day_input);
        endMonth= findViewById(R.id.end_month_input);
        endYear=findViewById(R.id.end_year_input);
        startToggleToday =findViewById(R.id.start_toogle_today);
        endToggleToday=findViewById(R.id.end_toogle_today);
        holidayName=findViewById(R.id.holiday_name);
        save=findViewById(R.id.save);
        startToggleToday.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleToday("set start");
            }
        });
        endToggleToday.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleToday("set end");
            }
        });
        save.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue();
                finish();
            }
        });
    }

    public void toggleToday(String str){
        Date date = new Date(System.currentTimeMillis());
        String year=Calendar.getInstance().get(Calendar.YEAR)+"";
        String month=Calendar.getInstance().get(Calendar.MONTH)+"";
        String day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"";
        if(str.equals("set start")){
            startYear.setText(year);
            startDay.setText(day);
            startMonth.setText(month);
        }else if(str.equals("set end")){
            endYear.setText(year);
            endDay.setText(day);
            endMonth.setText(month);
        }else{
            throw new IllegalArgumentException();
        }
    }

    public void setValue(){
        try {
            Date startDate=new Date(Integer.parseInt(startYear.getText().toString())-1900,
                    Integer.parseInt(startMonth.getText().toString()),
                    Integer.parseInt(startDay.getText().toString()));
            Date endDate=new Date(Integer.parseInt(endYear.getText().toString())-1900,
                    Integer.parseInt(endMonth.getText().toString()),
                    Integer.parseInt(endDay.getText().toString()));

            SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
            editor.putString(Values.START_DATE,startDate.toString());
            editor.putString(Values.END_DATE,endDate.toString());
            editor.putString(Values.HOLIDAY_NAME,holidayName.getText().toString());
            editor.putBoolean(Values.HAD_RUN,true);
            editor.apply();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "You input an invalid number", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }catch (Exception e){
            Toast.makeText(this, "Unexpected exception", Toast.LENGTH_SHORT).show();
        }
    }
}
