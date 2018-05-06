package com.thestreetcodecompany.roady;

import android.content.Context;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.model.Achievement;
import com.thestreetcodecompany.roady.classes.model.User;

import java.util.Collections;
import java.util.Date;
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


        // most recent
        List<Achievement> achievementsList = user.getAchievements();
        int latest = -1;
        for (int position = 0; position < achievementsList.size(); position++) {
            if (achievementsList.get(position).getReached()) {
                if (latest == -1 || achievementsList.get(position).getReachedDate().after(achievementsList.get(latest).getReachedDate())) {
                    latest = position;
                }
            }
        }

        if (latest != -1) {
            ImageView recentImage = findViewById(R.id.imageViewRecentAchievement);
            TextView recentTitle = findViewById(R.id.textViewRecentAchievementTitle);
            TextView recentDesc = findViewById(R.id.textViewRecentAchievementDescription);

            recentImage.setImageResource(achievementsList.get(latest).getImage());
            recentTitle.setText(achievementsList.get(latest).getTitle());
            recentDesc.setText(achievementsList.get(latest).getDescription());
        }


        // Conditions
        List<Achievement> achievementsCondition = user.getAchievementsCondition();
        GridView gridViewCondition = (GridView) findViewById(R.id.gridViewCondition);
        gridViewCondition.setAdapter(new gridAdapter(this, achievementsCondition));


        final List<Achievement> achievementsLevel = Achievement.find(Achievement.class, "type < ?", "0");
        //achievementsLevel.addAll(achievementsCondition);

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
        final GridView gridViewLevel = findViewById(R.id.gridViewLevel);
        gridViewLevel.setAdapter(new gridAdapter(this, achievementsLevel));


        // Implement On Item click listener
        gridViewLevel.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /*long viewId = view.getId();

                String text;
                if (viewId == R.id.textViewAchievementDescription) {
                    text = "desc";
                } else if (viewId == R.id.linearLayoutAchievement) {
                    text = "layout";
                }*/

                TextView tv = view.findViewById(R.id.textViewAchievementTitle);
                String title = (String) tv.getText();

                TextView tvd = view.findViewById(R.id.textViewAchievementDescription);
                String desc = (String) tvd.getText();

                if (desc.isEmpty()) {
                    desc = "";
                } else {
                    desc = "\n" + desc;
                }

                //String text = (String) parent.getAdapter().getDescription(position);
                //String text = parent.getItemAtPosition(position).getDescription();
                //String text = parent.getItemAtPosition(position).findViewById(R.id.textViewAchievementDescription).getText();

                Snackbar.make(view, "" + title + desc, Snackbar.LENGTH_LONG).show();
            }
        });

        gridViewCondition.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /*long viewId = view.getId();

                String text;
                if (viewId == R.id.textViewAchievementDescription) {
                    text = "desc";
                } else if (viewId == R.id.linearLayoutAchievement) {
                    text = "layout";
                }*/

                TextView tv = view.findViewById(R.id.textViewAchievementTitle);
                String title = (String) tv.getText();

                TextView tvd = view.findViewById(R.id.textViewAchievementDescription);
                String desc = (String) tvd.getText();

                if (!desc.isEmpty()) {
                    desc = "\n" + desc;
                }

                //String text = (String) parent.getAdapter().getDescription(position);
                //String text = parent.getItemAtPosition(position).getDescription();
                //String text = parent.getItemAtPosition(position).findViewById(R.id.textViewAchievementDescription).getText();

                Snackbar.make(view, "" + title + desc, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}




class AchievementsGridView extends GridView {

    public AchievementsGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AchievementsGridView(Context context) {
        super(context);
    }

    public AchievementsGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
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
        return achievements.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //getLayoutInflater();
            v = li.inflate(R.layout.achievement_item, null);

            if (v != null) {
                ImageView iv = (ImageView) v.findViewById(R.id.imageViewAchievement);
                TextView tv = (TextView) v.findViewById(R.id.textViewAchievementTitle);
                TextView tvd = (TextView) v.findViewById(R.id.textViewAchievementDescription);

                if (achievements.get(position).getReached()) {
                    iv.setImageResource(achievements.get(position).getImage());
                    tv.setText(achievements.get(position).getTitle());
                    String desc = achievements.get(position).getDescription() + " | " + achievements.get(position).getReachedStringFormated();
                    tvd.setText(desc);

                } else {
                    iv.setAlpha((float) 0.36);
                    tv.setAlpha((float) 0.36);

                    if (achievements.get(position).getType() == 7) {
                        iv.setImageResource(R.drawable.ic_hidden);
                        tv.setText(R.string.achievements_hidden);
                        tvd.setText("This achievment is hidden");
                    } else {
                        iv.setImageResource(achievements.get(position).getImage());
                        tv.setText(achievements.get(position).getTitle());
                        tvd.setText("Not achieved yet");
                    }
                }
            }

        } else {
            v = convertView;
        }

        return v;
    }

}