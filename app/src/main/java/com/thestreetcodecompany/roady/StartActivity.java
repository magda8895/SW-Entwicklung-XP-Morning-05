package com.thestreetcodecompany.roady;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import android.widget.Toast;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.RoadyData;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.User;

import java.util.List;

import static com.thestreetcodecompany.roady.classes.Helper.MakeSnackbar;
import static com.thestreetcodecompany.roady.classes.Helper.MakeToast;

public class StartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listview;
    ProgressBar progressBar;
    TextView display;
    RoadyData rd;
    View headerView;
    TextView navUsername;
    com.github.clans.fab.FloatingActionMenu fab_menu;

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
        listview = (ListView) findViewById(R.id.start_list);
        fab_menu = (com.github.clans.fab.FloatingActionMenu) findViewById(R.id.start_floating_menu);
        final com.github.clans.fab.FloatingActionButton fab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.start_fab_new);
        final com.github.clans.fab.FloatingActionButton fab2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.start_fab_old);
        display = (TextView) findViewById(R.id.start_display);
        progressBar = (ProgressBar) findViewById(R.id.start_progressBar);

        ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.start_container);
        final DBHandler dbh = new DBHandler();
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_menu.close(true);
            }
        });



        //Singleton Test
        //TODO: if the database contains one user, add this user object to the Singleton (RoadyData)
        //TODO: if not: Intent to Settings and create the User
        rd = RoadyData.getInstance();
        Log.d("Singleton","username: " + rd.user.getName() + " (" +rd.user.getId()+ ")" );


        //get Data
        final List<DrivingSession> sessions = dbh.getAllDrivingSessions(rd.user);




        //set Name in Navigation
        headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.navName);
        navUsername.setText(rd.user.getName());


        //set List Adapter
        listview.setAdapter(createAdapter(sessions));

        //set Display
        display.setText(rd.user.getDrivenKm() + " / " + rd.user.getGoalKm() + " km");
        progressBar.setMax((int)rd.user.getGoalKm());
        progressBar.setProgress((int)rd.user.getDrivenKm());



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(rd.user.getCoDrivers().size() > 0 && rd.user.getCars().size() > 0)
                {
                    Intent i = new Intent(getApplicationContext(), NewDrivingSession.class);
                    startActivity(i);
                }
                else {
                    MakeSnackbar("you have no car("+rd.user.getCars().size()+") entries or no co-driver (" + rd.user.getCars().size() + ") entries ",view);
                } MakeSnackbar("Click Start Driving",view);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbh.logAllCoDrivers();
                dbh.logAllUsers();
                Log.d("Singleton","username: " + rd.user.getName() + " (" +rd.user.getId()+ ")" );

                if(rd.user.getCoDrivers().size() > 0 && rd.user.getCars().size() > 0)
                {
                    Intent i = new Intent(getApplicationContext(), DrivingSessionAfter.class);
                    startActivity(i);
                }
                else {
                    MakeSnackbar("you have no car("+rd.user.getCars().size()+") entries or no co-driver (" + rd.user.getCars().size() + ") entries ",view);
                }
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fab_menu.close(true);
                MakeSnackbar("Click item: index: " + i,view);
            }
        });


        // change nav name
        //TextView textNavName = findViewById(R.id.navName);
        //textNavName.setText("" + user.getName());
        //Log.d("user", user.toString());


    }



    @Override
    protected void onResume(){
        super.onResume();

        if ( rd.user.getLastDrivingSession() != null ) {

            unfinishedSession();
        }
        //get all Driving Sessions
        final List<DrivingSession> sessions = rd.getUser().getAllDrivingSessions();

        //set List Adapter
        listview.setAdapter(createAdapter(sessions));

        //set Progressbar / Display
        display.setText(rd.getUser().getDrivenKm() + " / " + rd.getUser().getGoalKm() + " km");
        progressBar.setProgress((int)rd.user.getDrivenKm());
        progressBar.setMax((int)rd.user.getGoalKm());


        fab_menu.close(true);
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

        if (id == R.id.nav_achievements) {
            Intent i = new Intent(getApplicationContext(), AchievementsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_export) {
            MakeToast("export",getApplicationContext());
        } else if (id == R.id.nav_settings) {
            Intent i = new Intent(getApplicationContext(), SettingsBackend.class);
            startActivity(i);
        } else if (id == R.id.nav_infos) {
            Intent intent = new Intent(getApplicationContext(), InfosActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public ArrayAdapter createAdapter(final List<DrivingSession> sessions) {

        ArrayAdapter adapter = new ArrayAdapter<DrivingSession>(this,
                R.layout.listitem_start,R.id.startitem_name, sessions) {
            @Override
            public View getView(int position,View convertView, ViewGroup parent) {
                View view = super.getView(position,convertView,parent);

                TextView tv_name = (TextView) view.findViewById(R.id.startitem_name);
                TextView tv_time = (TextView) view.findViewById(R.id.startitem_date);
                TextView tv_distance = (TextView) view.findViewById(R.id.startitem_distance);

                tv_name.setText(sessions.get(position).getName());
                tv_time.setText(sessions.get(position).getDateStringStart());
                tv_distance.setText(sessions.get(position).getDistance() + " km");



                return view;
            }

        };


        return adapter;

    }


    //if a Driving Session is left unfinished you will be asked if u want to finish it now or to stash it
    public void unfinishedSession()
    {
        DrivingSession lastDrivingSession = rd.user.getLastDrivingSession();
        //Toast.makeText(this, "" + lastDrivingSession.getActive() , Toast.LENGTH_SHORT).show();

        if(((lastDrivingSession.getActive()) == true )){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(StartActivity.this);
            mBuilder.setTitle("This should not happen!");
            mBuilder.setMessage("Roady was quit unexpectedly. The last Driving Session is still unfinished. Should we stash?");
            mBuilder.setPositiveButton("Stash it!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            mBuilder.setNegativeButton("Resume!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();
                    Intent intent = new Intent(StartActivity.this,StopWatch.class);

                    intent.putExtra("from_NDS_to_SW","1");
                    startActivity(intent);


                }
            });
            AlertDialog alertDialog = mBuilder.create();
            alertDialog.show();
        }

        }




}


