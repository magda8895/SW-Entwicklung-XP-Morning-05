package com.thestreetcodecompany.roady;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.orm.SchemaGenerator;
import com.orm.SugarDb;
import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.formatters.DayAxisValueFormatter;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.User;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InfosActivity extends AppCompatActivity {
    private BarChart barChart;
    private PieChart pieChart;
    private PieChart pieChart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);
        barChart = findViewById(R.id.bar_chart);

        List<DrivingSession> sessions = DrivingSession.listAll(DrivingSession.class);

        List<Integer> sums = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

        for(DrivingSession session: sessions) {
            Calendar c = Calendar.getInstance();
            //c.setTime(session.getDateTimeStart());
            //int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 2;
            //sums.set(dayOfWeek, sums.get(dayOfWeek) + (int)(session.getDistance()));
        }

        List<BarEntry> entries = new ArrayList<>();
        for(int i = 0; i < sums.size(); i++) {
            entries.add(new BarEntry((i) % 7, sums.get(i)));
        }

        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setDrawGridBackground(false);
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.setDoubleTapToZoomEnabled(false);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter();

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();

        leftAxis.setDrawAxisLine(false);
        rightAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        rightAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(false);
        rightAxis.setDrawLabels(false);
        leftAxis.setDrawZeroLine(false);
        rightAxis.setDrawZeroLine(false);

        BarDataSet dataSet = new BarDataSet(entries, "Driving sessions");
        BarData data = new BarData(dataSet);
        barChart.setData(data);
        barChart.fitScreen();

        barChart.invalidate();

        DBHandler db = new DBHandler();
        db.makeTestData();
        User u = User.getTestUser();
        Date start = DrivingSession.formatDateTimeDate("12-12-2012 05:21:12");
        Date end = DrivingSession.formatDateTimeDate("12-12-2018 05:21:12");
        List <DrivingSession> drivingSession = DrivingSession.getAllDrivingSessions(u);


        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String output_name = "YourApp_" + df.format(new Date()) + ".db";
        File output_file = new File(getExternalCacheDir() + File.separator + output_name);
        try {
            File file = new File(new SugarDb(this).getDB().getPath()); // get private db reference
            if (file.exists() == false || file.length() == 0) throw new Exception("Empty DB");
            IOUtils.copy(new FileInputStream(file), new FileOutputStream(output_file));
            Intent i = new Intent(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(output_file));
            i.setType("*/*");
            startActivity(Intent.createChooser(i, "Send db"));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Unable to export db: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
