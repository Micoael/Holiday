package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.imzgw.holiday.R;
import com.imzgw.holiday.db.EverydayEvent;

import java.util.List;

public class EventAdapters extends ArrayAdapter {
    private int resourceId;
    public EventAdapters(@NonNull Context context, int textViewResourceId,
                         @NonNull List<EverydayEvent> objects) {
        super(context, textViewResourceId, textViewResourceId, objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        EverydayEvent event = (EverydayEvent) getItem(position);
        View view;

        if(convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

        }else{
            view=convertView;
        }

        TextView childEventName=view.findViewById(R.id.item_name);
        TextView childEventIsTemp=view.findViewById(R.id.item_isTemp);
        childEventName.setText(event.getName());
        childEventIsTemp.setText(event.getType()+"");

        return view;
    }


}
