package com.thestreetcodecompany.roady;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.orm.query.Select;
import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.model.Car;
import com.thestreetcodecompany.roady.classes.model.User;

import java.util.ArrayList;
import java.util.List;

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
        dbh.makeDB();
        dbh.makeTestData();
        User user = dbh.getTestUser();
        final List<Car> cars = dbh.getAllCars(user);

        //set List Adapter
        listview_car.setAdapter(createAdapter(cars));


        //set Display
        edittext_name.setText("HALLO!");

        //List<Car> car_list = Select.from(Car.class).orderBy("name").list();  //get data from database  (SugarORM)
        //setListAdapter(new ArrayAdapter<Car>(this,android.R.layout.settings_frontend, fishList));
        //adapter.setData(car_list);
        //listview_car.setAdapter(adapter);




        button_savesettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakeSnackbar("Settings Saved!", view);
            }
        });
    }


    public ArrayAdapter createAdapter(final List<Car> cars) {
        User karl = new User("Karl Heinz", 14, 1000);



        //final List<Car> cars = new ArrayList<Car>();
        //cars.add(new Car("auto_1", "G1200", karl));
        //cars.add(new Car("auto_2", "LE2000", karl));

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

}
