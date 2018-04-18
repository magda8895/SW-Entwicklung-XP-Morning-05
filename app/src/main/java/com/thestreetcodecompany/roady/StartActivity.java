package com.thestreetcodecompany.roady;

import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.User;

import java.util.List;

import static com.thestreetcodecompany.roady.classes.Helper.MakeSnackbar;
import static com.thestreetcodecompany.roady.classes.Helper.MakeToast;

public class StartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //get View Elements
        final ListView listview = (ListView) findViewById(R.id.start_list);
        final com.github.clans.fab.FloatingActionButton fab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.start_menuitem_new);
        final com.github.clans.fab.FloatingActionButton fab2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.start_menuitem_old);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
/*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_achievements) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
