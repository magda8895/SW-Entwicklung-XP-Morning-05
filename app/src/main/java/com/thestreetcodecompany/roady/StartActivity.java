package com.thestreetcodecompany.roady;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.User;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final ListView listview = (ListView) findViewById(R.id.start_list);

        listview.setAdapter(createAdapter());

    }

    public ArrayAdapter createAdapter(){
        User karl = new User("Karl Heinz", 14,1000);


        final List<DrivingSession> sessions = new ArrayList<DrivingSession>();
        sessions.add(new DrivingSession(true,"12-12-2012 12:12:12",1200,karl));
        sessions.add(new DrivingSession(true,"12-12-2012 12:12:12",2000,karl));
        sessions.add(new DrivingSession(true,"12-12-2012 12:12:12",4000,karl));

        ArrayAdapter adapter = new ArrayAdapter<DrivingSession>(this,
                R.layout.listitem_start,R.id.startitem_name, sessions) {
            @Override
            public View getView(int position,View convertView, ViewGroup parent) {
                View view = super.getView(position,convertView,parent);

                TextView tv_name = (TextView) view.findViewById(R.id.startitem_name);
                TextView tv_time = (TextView) view.findViewById(R.id.startitem_time);
                TextView tv_distance = (TextView) view.findViewById(R.id.startitem_distance);

                tv_name.setText(position + ".)" + sessions.get(position).getName());
                tv_time.setText("" + sessions.get(position).getTimeSpan());
                tv_distance.setText("" + sessions.get(position).getDistance());

                return view;
            }

        };


        return adapter;

    }
}




