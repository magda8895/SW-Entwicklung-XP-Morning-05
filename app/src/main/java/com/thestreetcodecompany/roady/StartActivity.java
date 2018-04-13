package com.thestreetcodecompany.roady;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.User;

import java.util.ArrayList;
import java.util.List;

import static com.thestreetcodecompany.roady.classes.Helper.MakeSnackbar;
import static com.thestreetcodecompany.roady.classes.Helper.MakeToast;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //get View Elements
        final ListView listview = (ListView) findViewById(R.id.start_list);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.start_menuitem_new);
        final FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.start_menuitem_old);
        final TextView display = (TextView) findViewById(R.id.start_display);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //get Data
        DBHandler dbh = new DBHandler();
        User user = dbh.getTestUser();
        final List<DrivingSession> sessions = dbh.getAllDrivingSessions(user);

        //set List Adapter
        listview.setAdapter(createAdapter(sessions));


        //set Display
        display.setText(user.getDriven_km() + "/" + user.getGoal_km() + " km");
        progressBar.setMax(user.getGoal_km());
        progressBar.setProgress(user.getDriven_km());




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakeSnackbar("Klick Neue Fahrt",view);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakeToast("Klick Alte Fahrt",getApplicationContext());
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MakeToast("Klick Item: index: " + i,getApplicationContext());
            }
        });

    }

    public ArrayAdapter createAdapter(final List<DrivingSession> sessions){




        ArrayAdapter adapter = new ArrayAdapter<DrivingSession>(this,
                R.layout.listitem_start,R.id.startitem_name, sessions) {
            @Override
            public View getView(int position,View convertView, ViewGroup parent) {
                View view = super.getView(position,convertView,parent);

                TextView tv_name = (TextView) view.findViewById(R.id.startitem_name);
                TextView tv_time = (TextView) view.findViewById(R.id.startitem_date);
                TextView tv_distance = (TextView) view.findViewById(R.id.startitem_distance);

                tv_name.setText(sessions.get(position).getName());
                tv_time.setText("" + sessions.get(position).getTimeSpan());
                tv_distance.setText("" + sessions.get(position).getDistance() + " km");

                return view;
            }

        };


        return adapter;

    }
}




