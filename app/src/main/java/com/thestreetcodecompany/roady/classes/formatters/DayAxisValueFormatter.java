package com.thestreetcodecompany.roady.classes.formatters;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class DayAxisValueFormatter implements IAxisValueFormatter {
    protected String[] mDays = new String[] {"Mon", "Die", "Mit", "Don", "Fr", "Sa", "So"};

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Log.i("DayAxisFormatter", "value : "+value);

        if (value < 0) return "err";
        return mDays[(int) value];
    }
}
