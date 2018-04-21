package com.thestreetcodecompany.roady;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.orm.query.Select;
import com.orm.SugarApp;
import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.model.Achievement;
import com.thestreetcodecompany.roady.classes.model.Car;
import com.thestreetcodecompany.roady.classes.model.CoDriver;
import com.thestreetcodecompany.roady.classes.model.Coordinate;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.User;

import java.util.ArrayList;
import java.util.List;

import static com.thestreetcodecompany.roady.classes.Helper.MakeSnackbar;

public class SettingsBackend extends AppCompatActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_frontend);

        //get View Elements
        final ListView listview_achievements = (ListView) findViewById(R.id.achievementsUserCreated);
        final ListView listview_codriver = (ListView) findViewById(R.id.coDriver);
        final ListView listview_car = (ListView) findViewById(R.id.car);
        final FloatingActionButton fab_achievemtents = (FloatingActionButton) findViewById(R.id.achievementUserCreatedAdd);
        final FloatingActionButton fab_codriver = (FloatingActionButton) findViewById(R.id.coDriverAdd);
        final FloatingActionButton fab_car = (FloatingActionButton) findViewById(R.id.carAdd);
        final EditText editText_achievements = (EditText) findViewById(R.id.achievementsUserCreatedTitle);
        final TextView editText_codriver = (EditText) findViewById(R.id.coDriverTitle);
        final EditText editText_car = (EditText) findViewById(R.id.carLabel);
        final EditText edittext_name = (EditText) findViewById(R.id.nameLabel);
        final SeekBar seekbar_drivenkm = (SeekBar) findViewById(R.id.drivenKm);
        final SeekBar seekbar_goalkm = (SeekBar) findViewById(R.id.goalKm);
        final Button button_savesettings = (Button) findViewById(R.id.saveSettings);

        //get Data
        DBHandler dbh = new DBHandler();
        dbh.makeDB();
        dbh.makeTestData();
        user = dbh.getTestUser();
        final List<Car> cars = user.getAllCars();
        final List<CoDriver> co_drivers = user.getAllCoDrivers();
        final List<Achievement> achievements = user.getAllAchievments();

        edittext_name.setText(user.getName());
        seekbar_drivenkm.setProgress(user.getDriven_km());
        seekbar_goalkm.setProgress(user.getGoal_km());

        //set List Adapter
        listview_car.setAdapter(createCarAdapter(cars));
        listview_codriver.setAdapter(createCoDriverAdapter(co_drivers));
        listview_achievements.setAdapter(createAchievmentAdapter(achievements));



        fab_achievemtents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String achievement = editText_achievements.getText().toString();
                String[] split = achievement.split(", ");
                Achievement a = new Achievement(split[0], Integer.parseInt(split[1]), Float.valueOf(split[2]), split[3], Boolean.valueOf(split[4]), user);
                a.save();
                achievements.add(a);
                listview_achievements.setAdapter(createAchievmentAdapter(achievements));
            }
        });

        fab_codriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String co_driver = editText_codriver.getText().toString();
                CoDriver co = new CoDriver(co_driver, user);
                co.save();
                co_drivers.add(co);
                listview_codriver.setAdapter(createCoDriverAdapter(co_drivers));
            }
        });

        fab_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String car = editText_car.getText().toString();
                String[] split = car.split(", ");
                Car c = new Car(split[0], split[1], user);
                c.save();
                cars.add(c);
                listview_car.setAdapter(createCarAdapter(cars));
            }
        });


        button_savesettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get Userdetail
                String name = edittext_name.getText().toString();
                float driven_km = seekbar_drivenkm.getProgress();
                float goal_km = seekbar_goalkm.getProgress();
                User user = new User(name, driven_km, goal_km);
                user.save();

                Snackbar.make(view, "Settings Saved!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }


    public ArrayAdapter createCarAdapter(final List<Car> cars) {

        ArrayAdapter adapter = new ArrayAdapter<Car>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, cars) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView tv_name = (TextView) view.findViewById(android.R.id.text1);

                tv_name.setText(cars.get(position).getName());

                return view;
            }
        };

        return adapter;
    }

    public ArrayAdapter createCoDriverAdapter(final List<CoDriver> co_drivers) {

        ArrayAdapter adapter = new ArrayAdapter<CoDriver>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, co_drivers) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView tv_name = (TextView) view.findViewById(android.R.id.text1);

                tv_name.setText(co_drivers.get(position).getName());

                return view;
            }
        };
        return adapter;
    }

    public ArrayAdapter createAchievmentAdapter(final List<Achievement> achievements) {

        ArrayAdapter adapter = new ArrayAdapter<Achievement>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, achievements) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView tv_name = (TextView) view.findViewById(android.R.id.text1);

                tv_name.setText(achievements.get(position).getName());

                return view;
            }
        };
        return adapter;
    }

}
