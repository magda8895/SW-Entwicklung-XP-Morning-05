package com.thestreetcodecompany.roady;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class ExportActivity extends AppCompatActivity {

    Button shareButton;
    EditText textFieldFileName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shareButton = (Button) findViewById(R.id.buttonShare);
        textFieldFileName = (EditText) findViewById(R.id.editTextFileName);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                String fileName = textFieldFileName.getText().toString();
                fileName += ".txt";

                File file = new File(fileName);
                File file2 = new File(getApplicationContext().getFilesDir(), fileName);

                try {
                    final String TESTSTRING = new String("Hello Android");

                    FileWriter writer = new FileWriter(file2);
                    writer.append(TESTSTRING);
                    writer.flush();
                    writer.close();
                    Log.i("File Writing stuff", "success");


                    /*
                    // catches IOException below
                    final String TESTSTRING = new String("Hello Android");

                    //FileOutputStream fOut2 = new FileOutputStream(getFilesDir() + fileName);

                    FileOutputStream fOut = openFileOutput(file.getName(),
                            MODE_PRIVATE);

                    OutputStreamWriter osw = new OutputStreamWriter(fOut);

                    // Write the string to the file
                    osw.write(TESTSTRING);

                    osw.flush();
                    osw.close();

                    */

                    //Reading the file back...
                    FileInputStream fIn = new FileInputStream(file2);
                    //FileInputStream fIn = openFileInput(fileName);;

                    InputStreamReader isr = new InputStreamReader(fIn);

                    char[] inputBuffer = new char[TESTSTRING.length()];

                    // Fill the Buffer with data from the file
                    isr.read(inputBuffer);

                    // Transform the chars to a String
                    String readString = new String(inputBuffer);

                    // Check if we read back the same chars that we had written out
                    boolean isTheSame = TESTSTRING.equals(readString);

                    Log.i("File Reading stuff", "success = " + isTheSame);


                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }



                Uri csvFile = Uri.fromFile(file2);

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                sharingIntent.setType("text/plain");
                //sharingIntent.setData(csvFile);
                String shareBody = "Here is the share content body";
                //sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file2.getPath()));
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });


        // not is use anymore
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
            }
        });
    }





}
