package com.thestreetcodecompany.roady;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.model.User;

import static com.thestreetcodecompany.roady.classes.Helper.MakeSnackbar;

public class SettingsBackend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_frontend);

        //get View Elements
        //final LinearLayout linearlayout = (LinearLayout) findViewById(R.id.start_list);
        final ListView listview_achievements = (ListView) findViewById(R.id.achievementsUserCreated);
        final ListView listview_codriver = (ListView) findViewById(R.id.coDriver);
        final ListView listview_car = (ListView) findViewById(R.id.car);
        final FloatingActionButton fab_achievemtents = (FloatingActionButton) findViewById(R.id.achievementUserCreatedAdd);
        final FloatingActionButton fab_codriver = (FloatingActionButton) findViewById(R.id.coDriverAdd);
        final FloatingActionButton fab_car = (FloatingActionButton) findViewById(R.id.carAdd);
        final TextView textview_drivenkm = (TextView) findViewById(R.id.drivenKmTitle);
        final TextView textview_goalkm = (TextView) findViewById(R.id.goalKmTitle);
        final TextView textview_achievements = (TextView) findViewById(R.id.achievementsUserCreatedTitle);
        final TextView textview_codriver = (TextView) findViewById(R.id.coDriverTitle);
        final TextView textview_car = (TextView) findViewById(R.id.carLabel);
        final EditText edittext_name = (EditText) findViewById(R.id.nameLabel);
        final SeekBar seekbar_drivenkm = (SeekBar) findViewById(R.id.drivenKm);
        final SeekBar seekbar_goalkm = (SeekBar) findViewById(R.id.goalKm);
        final Button button_savesettings = (Button) findViewById(R.id.saveSettings);

        //get Data
        DBHandler dbh = new DBHandler();
        User user = dbh.getTestUser();
        //final List<DrivingSession> sessions = dbh.getAllDrivingSessions(user);

        //set List Adapter
        //listview_achievements.setAdapter(createAdapter(sessions));


        //set Display
        edittext_name.setText("HALLO!");



        button_savesettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakeSnackbar("Settings Saved!",view);
            }
        });
    }

}
