package com.thestreetcodecompany.roady;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Html;
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

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.RoadyData;
import com.thestreetcodecompany.roady.classes.model.Achievement;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.Push;
import com.thestreetcodecompany.roady.classes.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.thestreetcodecompany.roady.classes.Helper.MakePush;
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
    NavigationView navigationView;
    TextView percentage;
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

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //get View Elements
        listview = (ListView) findViewById(R.id.start_list);
        fab_menu = (com.github.clans.fab.FloatingActionMenu) findViewById(R.id.start_floating_menu);
        final com.github.clans.fab.FloatingActionButton fab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.start_fab_new);
        final com.github.clans.fab.FloatingActionButton fab2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.start_fab_old);
        display = (TextView) findViewById(R.id.start_display);
        progressBar = (ProgressBar) findViewById(R.id.start_progressBar);
        percentage = findViewById(R.id.percentage);

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
        User user = rd.getUser();
        final List<DrivingSession> sessions = dbh.getAllDrivingSessions(user);

        //set Name in Navigation
        headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.navName);
        navUsername.setText(rd.user.getName());

        //set List Adapter
        listview.setAdapter(createAdapter(sessions));

        //set Display
        final int goal = (int)user.getGoalKm();
        display.setText("0 / " + goal + " km");
        progressBar.setMax((int)user.getGoalKm());

        ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", 0, (int) user.getDrivenKm());
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                int progress = (int)valueAnimator.getAnimatedValue();
                int ratio = (int)100 * progress/goal;
                percentage.setText(ratio + "%");
                display.setText(progress + " / " + goal + " km");
            }
        });
        animator.start();

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
                String str = ((TextView)view.findViewById(R.id.driving_session_id)).getText().toString();
                if(str.equals("")) return;
                long id = Integer.parseInt(str);
                Intent intent = new Intent(StartActivity.this, DrivingSessionActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                // MakeSnackbar("Click item: index: " + id, view);
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

        User user = rd.getUser();
        //get all Driving Sessions
        final List<DrivingSession> sessions = user.getAllDrivingSessions();

        //set List Adapter
        listview.setAdapter(createAdapter(sessions));

        //set Progressbar / Display
        int goal = (int)user.getGoalKm();
        int progress = (int)user.getDrivenKm();
        int ratio = (int) 100 * progress / goal;
        display.setText(progress + " / " + goal + " km");
        percentage.setText(ratio + "%");
        progressBar.setProgress((int)user.getDrivenKm());
        //progressBar.refreshDrawableState();

        //set Name in Navigation
        headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.navName);
        navUsername.setText(rd.user.getName());

        fab_menu.close(true);

        List<Achievement> achievements = rd.getUser().getUserGeneratedAchievements();
        for(Achievement a : achievements)
        {
            boolean r = a.getReached();
            if(a.getValue() <= rd.getUser().getDrivenKm() && !r)
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String currentDate = sdf.format(new Date());
                a.setReached(currentDate);
                a.save();
                MakePush(getString(R.string.user_generated_push_title), a.getTitle(), getApplicationContext());             }
        }

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
            //MakeToast("export",getApplicationContext());
            Intent i = new Intent(getApplicationContext(), ExportActivity.class);
            startActivity(i);
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
        // Add initial
        float sum = 0;
        for (DrivingSession session: sessions) {
            sum += session.getDistance();
        }
        final DrivingSession initialSession = new DrivingSession();
        initialSession.setKmStart(0);

        User user = rd.getUser();
        initialSession.setKmEnd(user.getDrivenKm() - sum);
        sessions.add(initialSession);

        ArrayAdapter adapter = new ArrayAdapter<DrivingSession>(this,
                R.layout.listitem_start, R.id.startitem_name, sessions) {
            @Override
            public View getView(int position,View convertView, ViewGroup parent) {
                View view = super.getView(position,convertView,parent);

                TextView tv_name = (TextView) view.findViewById(R.id.startitem_name);
                TextView tv_time = (TextView) view.findViewById(R.id.startitem_date);
                TextView tv_distance = (TextView) view.findViewById(R.id.startitem_distance);
                TextView tv_id = view.findViewById(R.id.driving_session_id);

                if(position == sessions.size() - 1) {
                    try {
                        long installed = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).firstInstallTime;
                        Date installDate = new Date(installed);
                        SimpleDateFormat df = new SimpleDateFormat("dd. MMM yyyy");
                        tv_time.setText(df.format(installDate));
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    tv_distance.setText(initialSession.getDistance() + " km");
                    tv_name.setText("Already driven");
                } else {
                    tv_name.setText(sessions.get(position).getName());
                    tv_time.setText(sessions.get(position).getDateStringStart());
                    tv_id.setText(sessions.get(position).getId()+"");
                    tv_distance.setText(sessions.get(position).getDistance() + " km");
                }


                return view;
            }

        };


        return adapter;

    }



}


