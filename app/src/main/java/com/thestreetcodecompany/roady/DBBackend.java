package com.thestreetcodecompany.roady;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.Helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.thestreetcodecompany.roady.classes.Helper.MakeToast;

public class DBBackend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbbackend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DBHandler dbh = new DBHandler();
        dbh.makeDB();
        dbh.makeTestData();

        String pattern = "dd-mm-yyyy hh:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            Date date = format.parse("12-12-2012 05:21:12");
            System.out.println("Date is: " + date);
            MakeToast(date.toString(),getApplicationContext());
        } catch (ParseException e) {
            e.printStackTrace();
        }




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakeToast("hallo",getApplicationContext());
                Helper.MakeSnackbar("Hallo",view);
            }
        });

    }


}
