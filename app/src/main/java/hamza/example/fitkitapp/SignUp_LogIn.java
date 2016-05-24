package hamza.example.fitkitapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class SignUp_LogIn extends AppCompatActivity implements View.OnClickListener {
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__log_in);


        Button SignUp = (Button) findViewById(R.id.Btn_SignUp);
        Button LogIn = (Button) findViewById(R.id.Btn_Login);
        //db = new Database(this);
       // db.onUpgrade(db.getWritableDatabase(),3,4);
        SignUp.setOnClickListener(this);
        LogIn.setOnClickListener(this);
       // addFood();

    }


    @Override
    public void onClick(View v) {

    switch(v.getId())
    {
        case R.id.Btn_SignUp:
            startActivity(new Intent(SignUp_LogIn.this, Join.class));
            break;

        case R.id.Btn_Login:
            startActivity(new Intent(SignUp_LogIn.this, Login.class));
            break;
    }

    }

    public void showMessageDialog(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void DisplayContact(Cursor c)
    {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
        "UserName: " + c.getString(1) + "\n",
            Toast.LENGTH_LONG).show();
    }

    public void addFood()
    {
        ArrayList<Food> foodArray = new ArrayList<>();

        Food f1 = new Food();
        f1.setName("Bread");
        f1.setQuantity(1);
        f1.setSize("2 slices");
        f1.setCalories(130);
        f1.setFat((float) 1.5);
        f1.setCarbs(25);
        f1.setProtein(4);

        Food f2 = new Food();
        f2.setName("Banana");
        f2.setSize("Medium (118 g)");
        f2.setQuantity(1);
        f2.setCalories(105);
        f2.setFat(0);
        f2.setCarbs(27);
        f2.setProtein(1);

        Food f3 = new Food();
        f3.setName("Eggs - Fried (whole egg)");
        f3.setSize("Large");
        f3.setQuantity(1);
        f3.setCalories(92);
        f3.setFat(7);
        f3.setCarbs((float) 0.4);
        f3.setProtein((float) 6.3);

        Food f4 = new Food();
        f4.setName("Milk");
        f4.setSize("100 ml");
        f4.setQuantity(1);
        f4.setCalories(40);
        f4.setFat((float) 1.6);
        f4.setCarbs((float) 5.1);
        f4.setProtein((float) 3.3);

        Food f5 = new Food();
        f5.setName("Coffee");
        f5.setSize("1 cup(s)");
        f5.setQuantity(1);
        f5.setCalories(2);
        f5.setFat(0);
        f5.setCarbs(0);
        f5.setProtein((float) 0.3);

        Food f6 = new Food();
        f6.setName("Burger (Mcdonalds)");
        f6.setSize("Medium");
        f6.setQuantity(1);
        f6.setCalories(250);
        f6.setFat(9);
        f6.setCarbs(31);
        f6.setProtein(12);

        Food f7 = new Food();
        f7.setName("Fully Cooked Seekh Kebab (K&Ns)");
        f7.setSize("7 pieces");
        f7.setQuantity(1);
        f7.setCalories(308);
        f7.setFat((float) 15.867);
        f7.setCarbs((float) 4.2);
        f7.setProtein((float) 36.867);

        Food f8 = new Food();
        f8.setName("Nuggets (K&Ns)");
        f8.setSize("12 pieces (268g)");
        f8.setQuantity(1);
        f8.setCalories(556);
        f8.setFat(20);
        f8.setCarbs(52);
        f8.setProtein(0);

        Food f9 = new Food();
        f9.setName("Bbq Chicken Sandwich (Subway)");
        f9.setSize("6 inch foot long");
        f9.setQuantity(1);
        f9.setCalories(380);
        f9.setFat(6);
        f9.setCarbs(57);
        f9.setProtein(32);

        Food f10 = new Food();
        f10.setName("Chapati (Homemade)");
        f10.setSize("50 g");
        f10.setQuantity(1);
        f10.setCalories(120);
        f10.setFat(3);
        f10.setCarbs(20);
        f10.setProtein(4);

        Food f11 = new Food();
        f11.setName("10 Chicken Mcnuggets (Mcdonalds)");
        f11.setSize("Large Container");
        f11.setQuantity(1);
        f11.setCalories(470);
        f11.setFat(30);
        f11.setCarbs(30);
        f11.setProtein(22);

        Food f12 = new Food();
        f12.setName("Mutton (Generic)");
        f12.setSize("100 g");
        f12.setQuantity(1);
        f12.setCalories(234);
        f12.setFat((float) 11.7);
        f12.setCarbs(144);
        f12.setProtein(31);

        foodArray.add(f1);
        foodArray.add(f2);
        foodArray.add(f3);
        foodArray.add(f4);
        foodArray.add(f5);
        foodArray.add(f6);
        foodArray.add(f7);
        foodArray.add(f8);
        foodArray.add(f9);
        foodArray.add(f10);
        foodArray.add(f11);
        foodArray.add(f12);

        for(int i=0; i<12; i++)
        {
            db.insertFood(foodArray.get(i).getName(),foodArray.get(i).getSize(),foodArray.get(i).getQuantity(),foodArray.get(i).getCalories(),foodArray.get(i).getFat(),foodArray.get(i).getCarbs(),foodArray.get(i).getProtein());
        }

    }
}
