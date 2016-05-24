package hamza.example.fitkitapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Progress extends AppCompatActivity implements View.OnClickListener {

    Button home,foodDiary,progress;
    TextView startWeight,currentWeight,change;
    FrameLayout frameLayout;
    LineChart lineChart;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        home = (Button)findViewById(R.id.Btn_Home);
        foodDiary = (Button)findViewById(R.id.Btn_FD);
        progress = (Button)findViewById(R.id.Btn_Progress);
        startWeight = (TextView)findViewById(R.id.startWeight);
        change = (TextView)findViewById(R.id.change);
        db = new Database(this);
        currentWeight = (TextView)findViewById(R.id.currentWeight);
        startWeight.setText(String.valueOf(getCurrentWeight()));
        currentWeight.setText(String.valueOf(getChangeInWeight()));
        change.setText(String.valueOf(getCurrentWeight() - getChangeInWeight()));
        frameLayout = (FrameLayout)findViewById(R.id.graph);
        lineChart = new LineChart(this);
        frameLayout.addView(lineChart);


       /* lineChart.setDescription("");
        lineChart.setNoDataTextDescription("No data at this moment");

        lineChart.setHighlightPerTapEnabled(true);
        lineChart.setTouchEnabled(true);

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);

        lineChart.setPinchZoom(true);

        lineChart.setBackgroundColor(Color.LTGRAY);

        LineData lineData = new LineData();
        lineData.setValueTextColor(Color.WHITE);

        lineChart.setData(lineData);

        Legend legend = lineChart.getLegend();

        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextColor(Color.WHITE);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setAvoidFirstLastClipping(true);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setTextColor(Color.WHITE);
        yAxis.setAxisMaxValue(120f);
        yAxis.setDrawGridLines(true);

        YAxis yAxis2 = lineChart.getAxisRight();
        yAxis2.setEnabled(false);
*/
        add();

        home.setOnClickListener(this);
        foodDiary.setOnClickListener(this);
        progress.setOnClickListener(this);

    }

    private void add()
    {
            ArrayList<Entry> entries = new ArrayList<>();
            entries.add(new Entry(55f, 0));
            entries.add(new Entry(54.9f, 1));
            entries.add(new Entry(54.8f, 2));
            entries.add(new Entry(54.7f, 3));
            entries.add(new Entry(53f, 4));
            entries.add(new Entry(54f, 5));

            LineDataSet lineDataSet = new LineDataSet(entries,"Change in Weight");

            ArrayList<String> labels = new ArrayList<String>();
            labels.add("January");
            labels.add("February");
            labels.add("March");
            labels.add("April");
            labels.add("May");
            labels.add("June");

            LineData data = new LineData(labels,lineDataSet);

            lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS); //
            lineDataSet.setDrawCubic(true);
            lineDataSet.setDrawFilled(true);

            lineChart.setData(data);
            lineChart.animateY(5000);


    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null,"SPL DB");
        set.setDrawCubic(true);
        set.setCubicIntensity(0.2f);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(ColorTemplate.getHoloBlue());
        set.setLineWidth(2f);
        set.setCircleSize(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 177));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(10f);

        return set;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.Btn_Home:
                startActivity(new Intent(Progress.this,Home.class));
                break;

            case R.id.Btn_FD:
                startActivity(new Intent(Progress.this,Food_Diary.class));
                break;

            case R.id.Btn_Progress:
                startActivity(new Intent(Progress.this,Progress.class));
                break;

            case R.id.go2diary:
                startActivity(new Intent(Progress.this,Food_Diary.class));
        }
    }

    private float getCurrentWeight()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int UserID=pref.getInt("UserID", 1);

        Cursor c = db.getLoggedInUser(UserID);;
        if(c.moveToFirst())
        {
            return (c.getFloat(6));
        }
        return -1;
    }

    private int getAllFoodsCalories()
    {
        Cursor c = db.getAllFoodsCalories();
        if(c.moveToFirst())
        {
            return (c.getInt(0));
        }
        return -1;
    }

    private float getCaloriesSum()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int UserID=pref.getInt("UserID", 1);
        Cursor c = db.getCaloriesSum(UserID);
        if(c.moveToFirst())
        {
            return (c.getInt(0));
        }
        return -1;
    }


    private float getChangeInWeight()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int UserID=pref.getInt("UserID", 1);

        Cursor c = db.getLoggedInUser(UserID);;
        if(c.moveToFirst())
        {
            float currentWeight = c.getFloat(6);
            return (float)(currentWeight + (getCaloriesSum() - currentWeight * 12.5)/3500 );
        }
        return -1;
    }
}
