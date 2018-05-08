package com.thestreetcodecompany.roady;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.thestreetcodecompany.roady.classes.formatters.MonthAxisValueFormatter;
import com.thestreetcodecompany.roady.classes.formatters.YearAxisValueFormatter;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class InfosActivity extends AppCompatActivity {
    private BarChart barChart;
    private TabLayout tabLayout;
    private TextView timeTextView;
    private ImageButton timeForward;
    private ImageButton timeBack;

    private int index = 0;

    public enum MODE {
        YEAR,
        MONTH,
        WEEK,
    }
    private MODE mode = MODE.YEAR;

    private String getTimeText(MODE mode, int index) {
        Date date = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);

        String pattern = "";
        switch (mode) {
            case YEAR:
                gc.add(Calendar.YEAR, -index);
                pattern = "YYYY";
                break;
            case MONTH:
                pattern = "MMMM YYYY";
                gc.add(Calendar.MONTH, -index);
                break;
        }

        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(gc.getTime());
    }

    private void updateData() {
        BarDataSet barDataSet = (BarDataSet) barChart.getBarData().getDataSetByIndex(0);

        List<DrivingSession> sessions = DrivingSession.listAll(DrivingSession.class);
        if(mode == MODE.YEAR) {
            List<Integer> sums = new ArrayList<>(Collections.nCopies(12, 0));

            for(DrivingSession session: sessions) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date(session.getDateTimeStart()));
                int month = c.get(Calendar.MONTH) - 1;
                sums.set(month, sums.get(month) + (int)(session.getDistance()));
            }

            for(int i = 0; i < sums.size(); i++) {
                if(sums.get(i) != 0) {
                    List<BarEntry> entries = barDataSet.getEntriesForXValue(i);
                    if(!entries.isEmpty()) entries.get(0).setY((index+1)*sums.get(i));
                }
            }
        } else {
            List<Integer> sums = new ArrayList<>(Collections.nCopies(31, 0));

            for(DrivingSession session: sessions) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date(session.getDateTimeStart()));
                int dayOfWeek = c.get(Calendar.DAY_OF_MONTH) - 1;
                sums.set(dayOfWeek, sums.get(dayOfWeek) + (int)(session.getDistance()));
            }

            for(int i = 0; i < sums.size(); i++) {
                if(sums.get(i) != 0) {
                    List<BarEntry> entries = barDataSet.getEntriesForXValue(i);
                    if(!entries.isEmpty()) entries.get(0).setY((index+1)*sums.get(i));
                }
            }
        }

        barChart.animateY(1000, Easing.EasingOption.EaseInOutSine);
    }

    private BarData getData(MODE mode) {
        BarData barData = new BarData();
        List<BarEntry> entries = new ArrayList<>();
        List<DrivingSession> sessions = DrivingSession.listAll(DrivingSession.class);
        if(mode == MODE.YEAR) {
            List<Integer> sums = new ArrayList<>(Collections.nCopies(12, 0));

            for(DrivingSession session: sessions) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date(session.getDateTimeStart()));
                int month = c.get(Calendar.MONTH) - 1;
                sums.set(month, sums.get(month) + (int)(session.getDistance()));
            }

            for(int i = 0; i < sums.size(); i++) {
                if(sums.get(i) != 0) entries.add(new BarEntry(i+1, sums.get(i)));
            }
        } else {
            List<Integer> sums = new ArrayList<>(Collections.nCopies(31, 0));

            for(DrivingSession session: sessions) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date(session.getDateTimeStart()));
                int dayOfMonth = c.get(Calendar.DAY_OF_MONTH) - 1;
                int month = c.get(Calendar.MONTH);
                c.setTime(new Date());
                c.add(Calendar.MONTH, -index);
                int currentMonth = c.get(Calendar.MONTH);
                if(currentMonth == month)
                    sums.set(dayOfMonth, sums.get(dayOfMonth) + (int)(session.getDistance()));
            }

            for(int i = 0; i < sums.size(); i++) {
                if(sums.get(i) != 0) entries.add(new BarEntry(i, sums.get(i)));
            }
        }

        BarDataSet dataSet = new BarDataSet(entries, "Driving sessions");
        barData.addDataSet(dataSet);
        return barData;
    }

    private void setMode(MODE mode) {
        index = 0;
        timeTextView.setText(getTimeText(mode, index));

        barChart.setPadding(0, 0, 0, 0);

        XAxis xAxis = barChart.getXAxis();
        if(mode == MODE.YEAR) {
            xAxis.setLabelCount(12, true);
            xAxis.setAxisMinimum(0);
            xAxis.setAxisMaximum(11);

            xAxis.setValueFormatter(new YearAxisValueFormatter());
        } else if(mode == MODE.MONTH) {
            xAxis.setLabelCount(6, true);
            xAxis.setAxisMinimum(0);
            xAxis.setAxisMaximum(30);

            xAxis.setValueFormatter(new MonthAxisValueFormatter());
        }

        barChart.setData(getData(mode));
        barChart.invalidate();
        this.mode = mode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);

        barChart = findViewById(R.id.bar_chart);
        tabLayout = findViewById(R.id.tab_layout);
        timeTextView = findViewById(R.id.time_text_view);
        timeBack = findViewById(R.id.time_back);
        timeForward = findViewById(R.id.time_forward);

        timeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index == 0) {
                    timeForward.setClickable(true);
                }
                index++;
                timeTextView.setText(getTimeText(mode, index));
                updateData();
            }
        });

        timeForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index <= 1) {
                    timeForward.setClickable(false);
                }
                index--;
                timeTextView.setText(getTimeText(mode, index));
                updateData();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String text = null;
                if (tab.getText() != null) {
                    text = tab.getText().toString();
                    if(text.toUpperCase().equals("MONAT")) {
                        setMode(MODE.MONTH);
                    } else {
                        setMode(MODE.YEAR);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setDrawGridBackground(false);
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.setDoubleTapToZoomEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);

        setMode(mode);
    }
}
