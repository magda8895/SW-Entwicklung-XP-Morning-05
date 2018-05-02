package com.thestreetcodecompany.roady;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.model.Achievement;
import com.thestreetcodecompany.roady.classes.model.User;

import java.util.Collections;
import java.util.List;



public class AchievementsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context c = this;
        setContentView(R.layout.activity_achievements);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // DB Connect
        DBHandler dbh = new DBHandler();
        User user = dbh.getTestUser();


        // Conditions
        List<Achievement> achievementsCondition = user.getAchievementsCondition();
        GridView gridViewCondition = (GridView) findViewById(R.id.gridViewCondition);
        gridViewCondition.setAdapter(new gridAdapter(this, achievementsCondition));


        final List<Achievement> achievementsLevel = Achievement.find(Achievement.class, "type < ?", "0");

        // Streak
        List<Achievement> achievementsStreak = user.getAchievementsTypeReached(5);
        if (achievementsStreak.isEmpty()) {
            achievementsLevel.add(user.getAchievementsType(5).get(0));
        } else {
            achievementsLevel.add(achievementsStreak.get(achievementsStreak.size() - 1));
        }

        // Distance
        List<Achievement> achievementsDistance = user.getAchievementsTypeReached(4);
        if (achievementsDistance.isEmpty()) {
            achievementsLevel.add(user.getAchievementsType(4).get(0));
        } else {
            achievementsLevel.add(achievementsDistance.get(achievementsDistance.size() - 1));
        }

        // Time
        List<Achievement> achievementsTime = user.getAchievementsTypeReached(6);
        //List<Achievement> achievementsTime = Achievement.findWithQuery(Achievement.class, "Select * from Achievement where type = 6 and reached = true LIMIT 4");
        Log.d("achievements counter", "" + achievementsTime.size());
        if (achievementsTime.isEmpty()) {
            achievementsLevel.add(user.getAchievementsType(6).get(0));
        } else {
            achievementsLevel.add(achievementsTime.get(achievementsTime.size() - 1));
        }

        // Fast & Furious
        List<Achievement> achievementsFastAndFurious = user.getAchievementsTypeReached(7);
        if (achievementsFastAndFurious.isEmpty()) {
            achievementsLevel.add(user.getAchievementsType(7).get(0));
        } else {
            achievementsLevel.add(achievementsFastAndFurious.get(achievementsFastAndFurious.size() - 1));
        }

        // initialize grid
        GridView gridViewLevel = (GridView) findViewById(R.id.gridViewLevel);
        gridViewLevel.setAdapter(new gridAdapter(this, achievementsLevel));

    }
}




class gridAdapter extends BaseAdapter {
    private Context mContext;
    private List<Achievement> achievements;

    public gridAdapter(Context c, List<Achievement> achievements) {
        this.mContext = c;
        this.achievements = achievements;
    }

    public int getCount() {
        return achievements.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //getLayoutInflater();
            v = li.inflate(R.layout.achievement_item, null);

            if (v != null) {
                ImageView iv = (ImageView) v.findViewById(R.id.imageViewAchievement);
                TextView tv = (TextView) v.findViewById(R.id.textViewAchievementTitle);

                if (achievements.get(position).getReached()) {
                    iv.setImageResource(achievements.get(position).getImage());
                    tv.setText(achievements.get(position).getTitle());
                } else {
                    iv.setAlpha((float) 0.36);
                    tv.setAlpha((float) 0.36);

                    if (achievements.get(position).getType() == 7) {
                        iv.setImageResource(R.drawable.ic_help_black_24dp);
                        tv.setText(R.string.achievements_hidden);
                    } else {
                        iv.setImageResource(R.drawable.ic_stars);
                        tv.setText(achievements.get(position).getTitle());
                    }
                }
            }

        } else {
            v = convertView;
        }

        return v;
    }

}