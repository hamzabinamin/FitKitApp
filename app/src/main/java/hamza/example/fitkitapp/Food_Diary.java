package hamza.example.fitkitapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Food_Diary extends AppCompatActivity implements View.OnClickListener {

    Button home,foodDiary,progress,makeEntry;
    ImageView addFood1,addFood2,addFood3;
    ListView listview1;
    ListView listview2;
    ListView listview3;
    TextView foodCount,caloriesGoal,caloriesRemaining,goalCals,goalCarbs,goalFat,goalProtein;
    TextView remainingCals,remainingCarbs,remainingFat,remainingProtein;
    Adaptor adaptor1,adaptor2,adaptor3;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food__diary);

        home = (Button)findViewById(R.id.Btn_Home1);
        foodDiary = (Button)findViewById(R.id.Btn_FD1);
        progress = (Button)findViewById(R.id.Btn_Progress1);
        addFood1 = (ImageView)findViewById(R.id.addFood1);
        addFood2 = (ImageView)findViewById(R.id.addFood2);
        addFood3 = (ImageView)findViewById(R.id.addFood3);
        makeEntry = (Button)findViewById(R.id.Btn_MakeEntry);
        listview1 = (ListView)findViewById(R.id.listviewFood);
        listview2 = (ListView)findViewById(R.id.listviewFood2);
        listview3 = (ListView)findViewById(R.id.listviewFood3);
        foodCount = (TextView)findViewById(R.id.FoodCount);
        caloriesGoal = (TextView)findViewById(R.id.caloriesGoal);
        caloriesRemaining = (TextView)findViewById(R.id.caloriesRemaining);
        goalCals = (TextView)findViewById(R.id.goalCal);
        goalCarbs = (TextView)findViewById(R.id.goalCarbs);
        goalFat = (TextView)findViewById(R.id.goalFat);
        goalProtein = (TextView)findViewById(R.id.goalProtein);
        remainingCals = (TextView)findViewById(R.id.remainingCals);
        remainingCarbs = (TextView)findViewById(R.id.remainingCarbs);
        remainingFat = (TextView)findViewById(R.id.remainingFat);
        remainingProtein = (TextView)findViewById(R.id.remainingProtein);

        db = new Database(this);
        foodCount.setText(String.valueOf(getFoodCount()));
        caloriesRemaining.setText(String.valueOf(getCaloriesSum()));
        caloriesGoal.setText(String.valueOf(calculateBMR()));
        goalCals.setText(String.valueOf(getAllFoodsCalories()));
        goalCarbs.setText(String.valueOf(getAllFoodsCarbs()));
        goalFat.setText(String.valueOf(getAllFoodsFat()));
        goalProtein.setText(String.valueOf(getAllFoodsProtein()));
        remainingCals.setText(String.valueOf(getRemainingCalories()));
        remainingCarbs.setText(String.valueOf(getRemainingCarbs()));
        remainingFat.setText(String.valueOf(getRemainingFat()));
        remainingProtein.setText(String.valueOf(getRemainingProtein()));

        showFood(getFood());
     /*  if(adaptor.getCount() > 2){
            View item = adaptor.getView(0, null, listview);
            item.measure(0, 0);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (5.5 * item.getMeasuredHeight()));
            listview.setLayoutParams(params);
        }*/

        addFood1.setOnClickListener(this);
        addFood2.setOnClickListener(this);
        addFood3.setOnClickListener(this);
        home.setOnClickListener(this);
        foodDiary.setOnClickListener(this);
        progress.setOnClickListener(this);
        makeEntry.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.addFood1:
                startActivity(new Intent(Food_Diary.this,Add_Breakfast.class));
                break;

            case R.id.addFood2:
                startActivity(new Intent(Food_Diary.this,Add_Lunch.class));
                break;

            case R.id.addFood3:
                startActivity(new Intent(Food_Diary.this,Add_Dinner.class));
                break;

            case R.id.Btn_MakeEntry:
                startActivity(new Intent(Food_Diary.this,Food_Diary.class));
                break;

            case R.id.Btn_Home1:
                startActivity(new Intent(Food_Diary.this,Home.class));
                break;

            case R.id.Btn_FD1:
                startActivity(new Intent(Food_Diary.this,Food_Diary.class));
                break;

            case R.id.Btn_Progress1:
                startActivity(new Intent(Food_Diary.this,Progress.class));
                break;


        }
    }

    public void showFood(List<Food> food) {
        adaptor1 = new Adaptor(this, food);
        adaptor2 = new Adaptor(this,food);
        adaptor3 = new Adaptor(this,food);
        listview1.setAdapter(adaptor1);
        if(listview1 != null)
        {
            ViewGroup.LayoutParams params = listview1.getLayoutParams();
            params.height = 0;
            listview1.setLayoutParams(params);
            listview1.requestLayout();
        }
        listview2.setAdapter(adaptor2);
        if(listview2 != null)
        {
            ViewGroup.LayoutParams params = listview2.getLayoutParams();
            params.height = 0;
            listview2.setLayoutParams(params);
            listview2.requestLayout();

        }
        listview3.setAdapter(adaptor3);
        if(listview3 != null)
        {
            ViewGroup.LayoutParams params = listview3.getLayoutParams();
            params.height = 0;
            listview3.setLayoutParams(params);
            listview3.requestLayout();
        }
    }

    private List<Food> getFood() {

        List<Food> foodArray = new ArrayList<>();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int UserID=pref.getInt("UserID", 1);
        Cursor c1 = db.getSelectedFoods(UserID);

        if(c1.moveToFirst()) {
            for (int i = 0; i < c1.getCount(); i++) {
                Cursor c2 = db.getFoodsThroughID(c1.getInt(0));
                if(c2.moveToFirst()) {

                    foodArray.add(new Food(c2.getString(1), c2.getString(2), c2.getInt(3), c2.getFloat(4), c2.getFloat(5), c2.getFloat(6), c2.getFloat(7)));
                }
                    c1.moveToNext();

            }
        }
        return foodArray;
    }

    private int getFoodCount()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int UserID=pref.getInt("UserID", 1);
        if(db.getFoodCount(UserID) != null) {
            Cursor c = db.getFoodCount(UserID);
            if (c.moveToFirst()) {
                return c.getInt(0);
            }
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

    private int getAllFoodsCalories()
    {
        Cursor c = db.getAllFoodsCalories();
        if(c.moveToFirst())
        {
            return (c.getInt(0));
        }
        return -1;
    }

    private int getAllFoodsCarbs()
    {
        Cursor c = db.getAllFoodsCarbs();
        if(c.moveToFirst())
        {
            return (c.getInt(0));
        }
        return -1;
    }

    private int getAllFoodsFat()
    {
        Cursor c = db.getAllFoodsFat();
        if(c.moveToFirst())
        {
            return (c.getInt(0));
        }
        return -1;
    }

    private int getAllFoodsProtein()
    {
        Cursor c = db.getAllFoodsProtein();
        if(c.moveToFirst())
        {
            return (c.getInt(0));
        }
        return -1;
    }

    private int getRemainingCalories()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int UserID=pref.getInt("UserID", 1);
        Cursor c = db.getCaloriesSum(UserID);
        if(c.moveToFirst())
        {
            return (getAllFoodsCalories() - c.getInt(0));
        }
        return -1;
    }

    private int getRemainingCarbs()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int UserID=pref.getInt("UserID", 1);
        Cursor c = db.getCarbsSum(UserID);
        if(c.moveToFirst())
        {
            return (getAllFoodsCarbs() - c.getInt(0));
        }
        return -1;
    }

    private int getRemainingFat()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int UserID=pref.getInt("UserID", 1);
        Cursor c = db.getFatSum(UserID);
        if(c.moveToFirst())
        {
            return (getAllFoodsFat() - c.getInt(0));
        }
        return -1;
    }

    private int getRemainingProtein()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int UserID=pref.getInt("UserID", 1);
        Cursor c = db.getProteinSum(UserID);
        if(c.moveToFirst())
        {
            return (getAllFoodsProtein() - c.getInt(0));
        }
        return -1;
    }
}
