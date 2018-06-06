package com.thestreetcodecompany.roady;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.RoadyData;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.User;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * Created by fklezin on 30/04/2018.
 */

public class InfosActivityTest {

    @Test
    public void testWeatherConditionPercentage() {
        int sum = 0;
        RoadyData rd = RoadyData.getInstance();
        Date start_date = DrivingSession.formatDateTimeDate("12-12-2010 00:00:00");
        Date end_date = DrivingSession.formatDateTimeDate("12-12-2018 00:00:00");
        for(int i = 0; i < 4; i++) {
            sum += DrivingSession.getWeatherConditionPercentageTimePeriod(rd.user, start_date, end_date, i);
        }

        assertEquals("The percentage should be 100%", sum, 100);
    }

    @Test
    public void testRoadConditionPercentage() {
        int sum = 0;
        RoadyData rd = RoadyData.getInstance();
        Date start_date = DrivingSession.formatDateTimeDate("12-12-2010 00:00:00");
        Date end_date = DrivingSession.formatDateTimeDate("12-12-2018 00:00:00");
        for(int i = 0; i < 4; i++) {
            sum += DrivingSession.getWeatherConditionPercentageTimePeriod(rd.user, start_date, end_date, i);
        }

        assertEquals("The percentage should be 100%", sum, 100);
    }
}
