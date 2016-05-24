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
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements View.OnClickListener {

    Button home, foodDiary, progress, quickfood;
    TextView foodCount, caloriesRemaining, caloriesGoal;
    Database db;
    PieChart mChart;
    float yData[];
    String xData[] = {"Carbohydrates", "Fat", "Protein"};
    FrameLayout framelayout;
    float carbs, fat, protein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home = (Button) findViewById(R.id.Btn_Home);
        foodDiary = (Button) findViewById(R.id.Btn_FD);
        progress = (Button) findViewById(R.id.Btn_Progress);
        quickfood = (Button) findViewById(R.id.go2diary);
        foodCount = (TextView) findViewById(R.id.FoodCount);
        caloriesRemaining = (TextView) findViewById(R.id.caloriesRemaining);
        caloriesGoal = (TextView) findViewById(R.id.caloriesGoal);
        yData = new float[3];

        db = new Database(this);
        mChart = new PieChart(this);
        framelayout = (FrameLayout) findViewById(R.id.framelayout);
        framelayout.addView(mChart, 0);
        foodCount.setText(String.valueOf(getFoodCount()));
        caloriesGoal.setText(String.valueOf(calculateBMR()));
        caloriesRemaining.setText(String.valueOf(getCaloriesSum()));
        carbs = getCarbsSum();
        fat = getFatSum();
        protein = getProteinSum();

        if(getFoodCount() == 0) {
            yData[0] = 1;
            yData[1] = 1;
            yData[2] = 1;
            mChart.setUsePercentValues(false);
        }
        else
        {
            yData[0] = carbs;
            yData[1] = fat;
            yData[2] = protein;
            mChart.setUsePercentValues(true);
        }
        mChart.setDescription("");
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                if (entry == null)
                    return;

                Toast.makeText(getBaseContext(), xData[entry.getXIndex()] + " = " + entry.getVal(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {


            }
        });

        addData();

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        l.setXEntrySpace(20);
        l.setYEntrySpace(20);


        home.setOnClickListener(this);
        foodDiary.setOnClickListener(this);
        progress.setOnClickListener(this);
        quickfood.setOnClickListener(this);
    }

    private void addData() {
        ArrayList<Entry> yvalues = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; i++) {
            yvalues.add(new Entry(yData[i], i));
        }



        ArrayList<String> xvalues = new ArrayList<String>();

        for(int i=0;i<xData.length;i++)
        {
            xvalues.add(xData[i]);
        }

        PieDataSet dataSet = new PieDataSet(yvalues,"");
        dataSet.setSliceSpace(5);
        dataSet.setSelectionShift(5);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        //for(int c: ColorTemplate.LIBERTY_COLORS)
        colors.add(Color.rgb(255, 165, 0));

        //for(int c: ColorTemplate.LIBERTY_COLORS)
        colors.add(Color.rgb(30, 144, 255));

        //for(int c: ColorTemplate.COLORFUL_COLORS)
        colors.add(Color.rgb(255, 215, 0));


       // colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data = new PieData(xvalues, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);

        mChart.setData(data);

        mChart.highlightValue(null);

        mChart.invalidate();

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.Btn_Home:
                startActivity(new Intent(Home.this,Home.class));
                break;

            case R.id.Btn_FD:
                startActivity(new Intent(Home.this,Food_Diary.class));
                break;

            case R.id.Btn_Progress:
                startActivity(new Intent(Home.this,Progress.class));
                break;

            case R.id.go2diary:
                startActivity(new Intent(Home.this,Food_Diary.class));
        }
    }

   private int getFoodCount()
   {
       SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
       int UserID=pref.getInt("UserID", 1);
       Cursor c = db.getFoodCount(UserID);
       if(c.moveToFirst())
       {
           if(c.getInt(0) > 0)
        return c.getInt(0);
           else
               return 0;
       }
       else
           return -1;
   }

    private float getCaloriesSum()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int UserID=pref.getInt("UserID", 1);
        Cursor c = db.getCaloriesSum(UserID);
        if(c.moveToFirst())
        {
            return (calculateBMR() - c.getInt(0));
        }
        return -1;
    }

    private float getCarbsSum()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int UserID=pref.getInt("UserID", 1);
        Cursor c = db.getCarbsSum(UserID);
        ArrayList<Entry> entries = new ArrayList<>();
        if(c.moveToFirst())
        {
            return (c.getFloat(0));
        }
        return -1;
    }

    private float getFatSum()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int UserID=pref.getInt("UserID", 1);
        Cursor c = db.getFatSum(UserID);
        if(c.moveToFirst())
        {
            return (c.getFloat(0));
        }
        return -1;
    }

    private float getProteinSum()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int UserID=pref.getInt("UserID", 1);
        Cursor c = db.getProteinSum(UserID);
        if(c.moveToFirst())
        {
            return (c.getFloat(0));
        }
        return -1;
    }

    private float calculateBMR()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int UserID=pref.getInt("UserID", 1);
        Cursor c = db.getLoggedInUser(UserID);
        if(c.moveToFirst())
        {
            float height = c.getFloat(8);
            float weight = c.getFloat(6);
            int age = c.getInt(9);
            String gender = c.getString(3);

            if(gender == "Male")
                return (float)(10 * weight + 6.25 * height - 5 * age + 5);

            else
                return (float)(10 * weight + 6.25 * height - 5 * age - 161);
        }
        return -1;
    }
}
