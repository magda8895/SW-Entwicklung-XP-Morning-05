package com.thestreetcodecompany.roady;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.User;

import java.util.List;

import static com.thestreetcodecompany.roady.classes.Helper.MakeSnackbar;
import static com.thestreetcodecompany.roady.classes.Helper.MakeToast;

/**
 * TODO: document your custom view class.
 */
public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //get View Elements
        /*final ListView listview = (ListView) findViewById(R.id.start_list);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.start_menuitem_new);
        final FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.start_menuitem_old);
        final TextView display = (TextView) findViewById(R.id.start_display);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);*/

        //get Data
        DBHandler dbh = new DBHandler();
        User user = dbh.getTestUser();
        final List<DrivingSession> sessions = dbh.getAllDrivingSessions(user);

        //set List Adapter
        listview.setAdapter(createAdapter(sessions));


        //set Display
        /*display.setText(user.getDriven_km() + "/" + user.getGoal_km() + " km");
        progressBar.setMax(user.getGoal_km());
        progressBar.setProgress(user.getDriven_km());




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakeSnackbar("Klick Neue Fahrt",view);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakeToast("Klick Alte Fahrt",getApplicationContext());
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MakeToast("Klick Item: index: " + i,getApplicationContext());
            }
        });*/

    }

    public ArrayAdapter createAdapter(final List<DrivingSession> sessions){




        ArrayAdapter adapter = new ArrayAdapter<DrivingSession>(this,
                R.layout.listitem_start,R.id.startitem_name, sessions) {
            @Override
            public View getView(int position,View convertView, ViewGroup parent) {
                View view = super.getView(position,convertView,parent);

                TextView tv_name = (TextView) view.findViewById(R.id.startitem_name);
                TextView tv_time = (TextView) view.findViewById(R.id.startitem_date);
                TextView tv_distance = (TextView) view.findViewById(R.id.startitem_distance);

                tv_name.setText(sessions.get(position).getName());
                tv_time.setText("" + sessions.get(position).getTimeSpan());
                tv_distance.setText("" + sessions.get(position).getDistance() + " km");

                return view;
            }

        };


        return adapter;

    }

    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    public settings(Context context) {
        super(context);
        init(null, 0);
    }

    public settings(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public settings(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        /*final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.settings, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.settings_exampleString);
        mExampleColor = a.getColor(
                R.styleable.settings_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.settings_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.settings_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.settings_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();*/

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        canvas.drawText(mExampleString,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}
