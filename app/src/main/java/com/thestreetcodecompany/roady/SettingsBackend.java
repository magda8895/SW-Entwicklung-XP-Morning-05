package com.thestreetcodecompany.roady;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.BoringLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.PushService;
import com.thestreetcodecompany.roady.classes.RoadyData;
import com.thestreetcodecompany.roady.classes.model.Achievement;
import com.thestreetcodecompany.roady.classes.model.Car;
import com.thestreetcodecompany.roady.classes.model.CoDriver;
import com.thestreetcodecompany.roady.classes.model.User;

import java.util.Calendar;
import java.util.List;

import static com.thestreetcodecompany.roady.classes.Helper.MakePush;
import static com.thestreetcodecompany.roady.classes.Helper.MakeSnackbar;
import static com.thestreetcodecompany.roady.classes.Helper.MakeToast;

public class SettingsBackend extends AppCompatActivity {
    private Boolean newUser;
    RoadyData rd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rd = RoadyData.getInstance();
        //get Intent data (default: false)
        Intent intent = getIntent();
        newUser = intent.getBooleanExtra("newUser", false);

        //get User Data
        if (newUser) {
            rd.user = new User();
            rd.user.setName("Test User");
            rd.user.save();
        }


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

        final Switch pushSwitch = (Switch) findViewById(R.id.switchPush);


        pushSwitch.setChecked(rd.user.getPushes());


        final List<Car> cars = rd.user.getCars();
        final List<CoDriver> co_drivers = rd.user.getCoDrivers();
        final List<Achievement> achievements = rd.user.getAchievements();


        if(!rd.user.getName().equals("Test User"))
        {
            edittext_name.setText(rd.user.getName());
        }
        else {
            edittext_name.setText("");
        }
        seekbar_drivenkm.setProgress((int) rd.user.getDrivenKm());
        seekbar_goalkm.setProgress((int) rd.user.getGoalKm());

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
                Achievement a = new Achievement(name, 2, km, "No Image", false, rd.user);
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
                CoDriver co = new CoDriver(co_driver, rd.user);
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
                Car c = new Car(name, kfz, rd.user);
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
                if (name != "" && goal_km != 0) {


                    //update User
                    rd.user.setName(name);
                    rd.user.setDrivenKm(driven_km);
                    rd.user.setGoalKm(goal_km);

                    rd.user.update();


                    if (pushSwitch.isChecked() != rd.user.getPushes()) {
                        final AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        final Calendar cal = Calendar.getInstance();
                        Intent intent = new Intent(getApplicationContext(), PushService.class);
                        final PendingIntent pintent = PendingIntent.getService(getApplicationContext(), 0, intent, 0);

                        if (pushSwitch.isChecked() && rd.user.getPushes() == false) {
                            if (pushSwitch.isChecked()) {
                                startService(new Intent(getApplicationContext(), PushService.class));
                                alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                                        AlarmManager.INTERVAL_HOUR, pintent);
                            }
                            rd.user.setPushes(true);
                        } else if (!pushSwitch.isChecked() && rd.user.getPushes() == true) {
                            alarm.cancel(pintent);
                            stopService(new Intent(getApplicationContext(), PushService.class));
                            rd.user.setPushes(false);
                        }
                    }
                    rd.user.update();

                    Snackbar.make(view, "Settings Saved!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    finish();
                } else {
                    MakeSnackbar(getString(R.string.wrong_input), view);
                }


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


    public static void adaptListViewHeight(ListView listview) {

        ListAdapter adapter = listview.getAdapter();
        int height = 0;
        int divider_height = listview.getDividerHeight();
        int i = 0;

        while (i < adapter.getCount()) {
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
