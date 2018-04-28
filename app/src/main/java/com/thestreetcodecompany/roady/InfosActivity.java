package com.thestreetcodecompany.roady;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
import com.thestreetcodecompany.roady.classes.formatters.DayAxisValueFormatter;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
        pieChart = findViewById(R.id.pieChart);
        pieChart2 = findViewById(R.id.pieChart2);

        List<DrivingSession> sessions = DrivingSession.listAll(DrivingSession.class);

        List<Integer> sums = Arrays.asList(0, 0, 0, 0, 0, 0, 0);
        List<Integer> weatherSums = Arrays.asList(0, 0, 0, 0, 0, 0, 0);
        List<Integer> roadSums = Arrays.asList(0, 0, 0, 0, 0, 0, 0);

        for(DrivingSession session: sessions) {
            Calendar c = Calendar.getInstance();
            c.setTime(session.getDateTimeStart());
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 2;
            sums.set(dayOfWeek, sums.get(dayOfWeek) + (int)(session.getDistance()));

            weatherSums.set(session.getStreetCondition(), weatherSums.get(session.getStreetCondition()) + 1);
            roadSums.set(session.getStreetCondition(), roadSums.get(session.getStreetCondition()) + 1);
        }

        Log.i("InfosActivity", weatherSums.toString());

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

        List<PieEntry> weatherEntries = new ArrayList<>();
        for(int i = 0; i < weatherSums.size(); i++) {
            int weatherSum = weatherSums.get(i);
            if(weatherSum != 0) {
                weatherEntries.add(new PieEntry(weatherSum, new String[] {"Dry", "Rain", "Snow", "Ice"}[i]));
            }
        }

        PieDataSet pieDataSet = new PieDataSet(weatherEntries, "weatherData");
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieChart.setDrawHoleEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);

        pieChart.invalidate();

        List<PieEntry> roadEntries = new ArrayList<>();
        for(int i = 0; i < roadSums.size(); i++) {
            int roadSum = roadSums.get(i);
            if(roadSum != 0) {
                roadEntries.add(new PieEntry(roadSum, new String[] {"Clear", "Crowd", "Roadworks", "Accident"}[i]));
            }
        }

        PieDataSet pieDataSet2 = new PieDataSet(roadEntries, "weatherData");
        PieData pieData2 = new PieData(pieDataSet2);
        pieChart2.setData(pieData2);

        pieChart2.setDrawHoleEnabled(false);
        pieChart2.getLegend().setEnabled(false);
        pieChart2.getDescription().setEnabled(false);

        pieChart2.invalidate();
    }
}
