package com.thestreetcodecompany.roady;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.model.Achievement;
import com.thestreetcodecompany.roady.classes.model.User;

import org.w3c.dom.Text;

import java.util.List;

public class AchievementsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context c = this;
        setContentView(R.layout.activity_achievements);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new gridAdapter(this));

    }
}




class gridAdapter extends BaseAdapter {
    private Context mContext;

    public gridAdapter(Context c) {
        mContext = c;
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

    /*// create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        ImageView imageView;
        TextView textView;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            layout = new LinearLayout(mContext);
            layout.setOrientation(LinearLayout.VERTICAL);

            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(128, 128));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);

            textView = new TextView(mContext);
            textView.setText("test");
        } else {
            layout = (LinearLayout) convertView;
        }

        imageView.setImageResource(achievements.get(position).getImage());

        layout.addView(imageView);
        layout.addView(textView);

        return imageView;
    }*/

    public View getView(int position, View convertView, ViewGroup parent) {
        View v;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //getLayoutInflater();
            v = li.inflate(R.layout.achievement_item, null);

            ImageView iv = (ImageView) v.findViewById(R.id.imageViewAchievement);
            iv.setImageResource(achievements.get(position).getImage());

            TextView tv = (TextView) v.findViewById(R.id.textViewAchievementTitle);
            tv.setText(achievements.get(position).getTitle());
        } else {
            v = convertView;
        }

        return v;
    }

    private Integer[] mThumbIds = {
            R.drawable.ic_stars, R.drawable.ic_settings,
            R.drawable.ic_stars, R.drawable.ic_settings
    };

    //get Data
    DBHandler dbh = new DBHandler();
    User user = dbh.getTestUser();
    private List<Achievement> achievements = user.getAchievements();
}