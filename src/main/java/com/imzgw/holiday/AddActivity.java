package com.imzgw.holiday;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.imzgw.holiday.db.EverydayEvent;
import com.imzgw.holiday.util.Util;

import org.litepal.LitePal;

public class AddActivity extends AppCompatActivity {
    private EditText name,rule;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        name=findViewById(R.id.plan_name);
        rule=findViewById(R.id.plan_rule);
        save=findViewById(R.id.add_plan);
        save.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });

    }
    public void addData(){
        try {
            String nameText=name.getText().toString();
            String ruleText=rule.getText().toString();
            if(Util.jugdeIsIlleegal(ruleText)){
                LitePal.getDatabase();
                Toast.makeText(this, "Get a database!", Toast.LENGTH_SHORT).show();
                EverydayEvent event = new EverydayEvent();
                event.setType(1);
                event.setName(nameText);
                event.setRule(ruleText);
                event.save();
                Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Invalid rule text field", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(this, "You input nothing,", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }
    }

}
