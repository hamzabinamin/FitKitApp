package hamza.example.fitkitapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class Food_Details extends AppCompatActivity implements View.OnClickListener {

    ImageView back,forward;
    TextView foodname,size,quantity,calories,fat,carbs,protein;
    Bundle b;
    Intent n;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food__details);

        foodname = (TextView)findViewById(R.id.foodN);
        size = (TextView)findViewById(R.id.fsize);
        quantity = (TextView)findViewById(R.id.fquantity);
        calories = (TextView)findViewById(R.id.fcalories);
        fat = (TextView)findViewById(R.id.ffat);
        carbs = (TextView)findViewById(R.id.fcarbs);
        protein = (TextView)findViewById(R.id.fprotein);
        db = new Database(this);

        back = (ImageView)findViewById(R.id.backFD);
        forward = (ImageView)findViewById(R.id.forwardFD);
        n = getIntent();
        b = n.getExtras();

        foodname.setText(b.getString("FoodName"));
        size.setText(b.getString("FoodSize"));
        quantity.setText((String.valueOf(b.getInt("FoodQuantity"))));
        calories.setText(String.valueOf(b.getFloat("FoodCalories")));
        fat.setText(String.valueOf(b.getFloat("FoodFat")));
        carbs.setText(String.valueOf(b.getFloat("FoodCarbs")));
        protein.setText(String.valueOf(b.getFloat("FoodProtein")));

        back.setOnClickListener(this);
        forward.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.backFD:
                startActivity(new Intent(Food_Details.this,Food_Diary.class));

            case R.id.forwardFD:

                try {
                    int FoodID = db.getFood((String) foodname.getText()).getInt(0);
                    int UserID = read();
                    db.insertFoodAdded(UserID, FoodID);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, "Food Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Food_Details.this,Food_Diary.class));
        }
    }
    private int read(){

        SharedPreferences pref = this.getSharedPreferences("MyPref", MODE_PRIVATE);

        return pref.getInt("UserID", 1);
    }

}
