package com.imzgw.holiday;

import android.os.Bundle;
import android.app.Activity;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.imzgw.holiday.db.EverydayEvent;
import com.imzgw.holiday.util.Util;

import org.litepal.crud.DataSupport;

import java.util.List;

import adapters.EventAdapters;

public class DeleteActivity extends Activity {
    ListView eventList;
    List<EverydayEvent> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list= Util.readStack();
        setContentView(R.layout.activity_delete);
        eventList=findViewById(R.id.list_delete);
        final EventAdapters adapters=new EventAdapters(DeleteActivity.this
                ,R.layout.event_item,list);
        eventList.setAdapter(adapters);
        eventList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataSupport.deleteAll(EverydayEvent.class,"id=?",
                        (list.get(i).getId()+""));
                list.remove(i);
                adapters.notifyDataSetChanged();
                return false;
            }
        });
    }

}
