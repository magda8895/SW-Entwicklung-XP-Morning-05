package com.thestreetcodecompany.roady;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.model.Achievement;
import com.thestreetcodecompany.roady.classes.model.Car;
import com.thestreetcodecompany.roady.classes.model.CoDriver;
import com.thestreetcodecompany.roady.classes.model.User;

import java.util.List;

public class SettingsBackend extends AppCompatActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get View Elements
        final ListView listview_achievements = (ListView) findViewById(R.id.achievementsUserCreatedList);
        final ListView listview_codriver = (ListView) findViewById(R.id.coDriverList);
        final ListView listview_car = (ListView) findViewById(R.id.carList);
        final FloatingActionButton fab_achievemtents = (FloatingActionButton) findViewById(R.id.achievementUserCreatedAdd);
        final FloatingActionButton fab_codriver = (FloatingActionButton) findViewById(R.id.coDriverAdd);
        final FloatingActionButton fab_car = (FloatingActionButton) findViewById(R.id.carAdd);
        final EditText editText_achievements = (EditText) findViewById(R.id.achievementsUserCreated);
        final EditText editText_achievements_km = (EditText) findViewById(R.id.achievementsUserCreatedKm);
        final EditText editText_codriver = (EditText) findViewById(R.id.coDriverName);
        final EditText editText_car_name = (EditText) findViewById(R.id.carName);
        final EditText editText_car_kfz = (EditText) findViewById(R.id.carKfz);
        final EditText edittext_name = (EditText) findViewById(R.id.userName);
        final SeekBar seekbar_drivenkm = (SeekBar) findViewById(R.id.drivenKm);
        final SeekBar seekbar_goalkm = (SeekBar) findViewById(R.id.goalKm);
        final Button button_savesettings = (Button) findViewById(R.id.saveSettings);

        //get Data
        DBHandler dbh = new DBHandler();
        //dbh.makeDB();
        //dbh.makeTestData();
        user = dbh.getTestUser();

        final List<Car> cars = user.getCars();
        final List<CoDriver> co_drivers = user.getCoDrivers();
        final List<Achievement> achievements = user.getAchievements();


        edittext_name.setText(user.getName());
        seekbar_drivenkm.setProgress((int)user.getDrivenKm());
        seekbar_goalkm.setProgress((int)user.getGoalKm());

        //set List Adapter
        listview_car.setAdapter(createCarAdapter(cars));
        adaptListViewHeight(listview_car);
        listview_codriver.setAdapter(createCoDriverAdapter(co_drivers));
        adaptListViewHeight(listview_codriver);
        listview_achievements.setAdapter(createAchievmentAdapter(achievements));
        adaptListViewHeight(listview_achievements);



        fab_achievemtents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText_achievements.getText().toString();
                float km = Float.valueOf(editText_achievements_km.getText().toString());
                Achievement a = new Achievement(name, "custom achievement", 2, km, "/", false, user);
                a.save();
                achievements.add(a);
                listview_achievements.setAdapter(createAchievmentAdapter(achievements));
                adaptListViewHeight(listview_achievements);
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
                adaptListViewHeight(listview_codriver);
            }
        });

        fab_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText_car_name.getText().toString();
                String kfz = editText_car_kfz.getText().toString();
                Car c = new Car(name, kfz, user);
                c.save();
                cars.add(c);
                listview_car.setAdapter(createCarAdapter(cars));
                adaptListViewHeight(listview_car);
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

                tv_name.setText(achievements.get(position).getTitle());

                return view;
            }
        };
        return adapter;
    }


    public static void adaptListViewHeight(ListView listview) {

        ListAdapter adapter = listview.getAdapter();
        int height = 0;
        int divider_height = listview.getDividerHeight();
        int i = 0;

        while(i < adapter.getCount())
        {
            View item = adapter.getView(i, null, listview);
            item.measure(0, 0);
            height += item.getMeasuredHeight() + divider_height;
            i++;
        }

        ViewGroup.LayoutParams listviewParams = listview.getLayoutParams();
        listviewParams.height = height;

        listview.setLayoutParams(listviewParams);
        listview.requestLayout();
    }

}
