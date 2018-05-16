package com.thestreetcodecompany.roady;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.RoadyData;
import com.thestreetcodecompany.roady.classes.model.User;

import static com.thestreetcodecompany.roady.classes.Helper.MakePush;
import static com.thestreetcodecompany.roady.classes.Helper.MakeSnackbar;
import static com.thestreetcodecompany.roady.classes.Helper.MakeToast;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        DBHandler dbh = new DBHandler();
        dbh.logAllDrivingSessions();
        dbh.logAllUsers();
        RoadyData rd = RoadyData.getInstance();
        rd.user = dbh.getUser();

        if (rd.user == null) {
            Intent intent = new Intent(getApplicationContext(), SettingsBackend.class);
            intent.putExtra("newUser",true);
            startActivityForResult(intent,1);
        }

        else if(rd.user.hasActiveDrivingSession())
        {
            Intent intent = new Intent(getApplicationContext(), StopWatch.class);
            startActivity(intent);
            finish();
        }
        else {

            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
            startActivity(intent);
            finish();
        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        MakePush("Comeback from Settings " + requestCode +" | "+ resultCode,getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(intent);
        finish();
    }


}
