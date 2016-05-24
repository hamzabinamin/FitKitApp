package hamza.example.fitkitapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;

public class Congrats extends AppCompatActivity implements View.OnClickListener {

    Bundle b;
    Intent n;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congrats);

        Button cont = (Button)findViewById(R.id.Btn_Continue);
        TextView caloriesGoal = (TextView)findViewById(R.id.calorieGoal);
        b = new Bundle();
        n = getIntent();
        db = new Database(this);
        caloriesGoal.setText(String.valueOf(calculate_BMR()));
        cont.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        b = n.getExtras();

        db.insert(b.getString("UserName"), b.getString("Password"), b.getString("Gender"), b.getString("Email"), b.getString("Country"), b.getFloat("CWeight"), b.getFloat("GWeight"), b.getFloat("Height"), b.getInt("Age"));
        try {
            save(b.getString("UserName"), b.getString("Password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(Congrats.this, Home.class));
    }

    private float calculate_BMR()
    {
        b = n.getExtras();
        float height = b.getFloat("Height");
        float weight = b.getFloat("CWeight");
        int age = b.getInt("Age");
        String gender = b.getString("Gender");

        if(gender == "Male")
            return (float)(10 * weight + 6.25 * height - 5 * age + 5);

        else
            return (float)(10 * weight + 6.25 * height - 5 * age - 161);
    }

    private void save(String username,String password) throws SQLException {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        Cursor c = db.getContactThroughUserName(username);

        if(c.getCount() > 0) {
            int UserID = c.getInt(0);

            editor.putInt("UserID", UserID);
            editor.putString("Username", username);
            editor.putString("Password", password);
        }
        editor.commit();
    }
}
