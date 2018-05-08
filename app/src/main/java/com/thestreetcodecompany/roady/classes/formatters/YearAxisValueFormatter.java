package com.thestreetcodecompany.roady.classes.formatters;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class YearAxisValueFormatter implements IAxisValueFormatter {
    private String[] mMonths = new String[] {"Ja", "Feb", "Mär", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if (value < 0 || value >= mMonths.length) return "err";
        return mMonths[(int) value];
    }
}
