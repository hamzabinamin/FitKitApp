package hamza.example.fitkitapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Join extends AppCompatActivity implements View.OnClickListener {

    ImageView forward,back;
    EditText currentWeight,goalWeight,height,age;
    Button male,female;
    boolean Mclicked= false;
    boolean Fclicked = false;
    Drawable d;
    Bundle b;
    Intent n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        forward = (ImageView)findViewById(R.id.forwardJoin2);
        back = (ImageView)findViewById(R.id.backSL);
        currentWeight = (EditText)findViewById(R.id.CWeight);
        goalWeight = (EditText)findViewById(R.id.GWeight);
        height = (EditText)findViewById(R.id.Height);
        age = (EditText)findViewById(R.id.age);
        male = (Button)findViewById(R.id.genderM);
        female = (Button)findViewById(R.id.genderF);
        n = new Intent(Join.this,Join_2.class);
        b = new Bundle();
        d = male.getBackground();

        forward.setOnClickListener(this);
        back.setOnClickListener(this);
        currentWeight.setOnClickListener(this);
        goalWeight.setOnClickListener(this);
        height.setOnClickListener(this);
        male.setOnClickListener(this);
        female.setOnClickListener(this);
        age.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.genderM:
                if(!Mclicked)
                {
                    Mclicked = true;
                    male.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorBlueTopBar));
                }
                else
                {
                    Mclicked = false;
                    male.setBackground(d);
                }
                break;

            case R.id.genderF:
                if(!Fclicked)
                {
                    Fclicked = true;
                    female.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorBlueTopBar));
                }
                else
                {
                    Fclicked = false;
                    female.setBackground(d);
                }
                break;


            case R.id.forwardJoin2:
                if(Validation())
                {
                    b.putFloat("CWeight",Float.parseFloat((currentWeight.getText().toString())));
                    b.putFloat("GWeight", Float.parseFloat(goalWeight.getText().toString()));
                    b.putFloat("Height", Float.parseFloat(height.getText().toString()));
                    b.putInt("Age",Integer.parseInt(age.getText().toString()));
                    String selected = isSelected();
                    b.putString("Gender",selected);
                    n.putExtras(b);
                    startActivity(n);
                }
                break;

            case R.id.backSL:
                startActivity(new Intent(Join.this,SignUp_LogIn.class));
                break;

        }
    }

    public String isSelected()
    {
        if(Fclicked == false)
            return male.getText().toString();
        else
            return female.getText().toString();
    }


    public boolean Validation() {

        final String your_Cweight = currentWeight.getText().toString();
        final String your_Gweight = goalWeight.getText().toString();
        final String your_height = height.getText().toString();
        final String your_age = age.getText().toString();

        if (your_Cweight.length() == 0)
        {
            currentWeight.requestFocus();
            currentWeight.setError("Please enter a Weight Value.");
            return false;
        }
        else if (your_Cweight.matches("[a-zA-Z]+"))
        {
            currentWeight.requestFocus();
            currentWeight.setError("Please enter only Numerical Values.");
            return false;
        }
        else if (your_age.length() == 0)
        {
            age.requestFocus();
            age.setError("Please enter a Weight Value.");
            return false;
        }
        else if (your_age.matches("[a-zA-Z]+"))
        {
            age.requestFocus();
            age.setError("Please enter only Numerical Values.");
            return false;
        }
        else if (your_Gweight.length() == 0)
        {
            goalWeight.requestFocus();
            goalWeight.setError("Please enter a Weight Value.");
            return false;
        }
        else if (your_Gweight.matches("[a-zA-Z]+"))
        {
            goalWeight.requestFocus();
            goalWeight.setError("Please enter only Numerical Values.");
            return false;
        }
        else if (your_height.length() == 0)
        {
            height.requestFocus();
            height.setError("Please enter a Height Value.");
            return false;
        }
        else if (your_height.matches("[a-zA-Z]+"))
        {
            height.requestFocus();
            height.setError("Please enter only Numerical Values.");
            return false;
        }
        else if (!Fclicked && !Mclicked)
        {
            Toast.makeText(Join.this, "Please select a Gender Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (Fclicked && Mclicked)
        {
            Toast.makeText(Join.this, "Please select only one Gender Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            Toast.makeText(Join.this, "Validation Successful", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
