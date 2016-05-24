package hamza.example.fitkitapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.SQLException;

public class Join_2 extends AppCompatActivity implements View.OnClickListener {

    ImageView forward,back;
    EditText username,password,email,country;
    Bundle b;
    Intent n;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_2);

        forward = (ImageView)findViewById(R.id.forwardCongrats);
        back = (ImageView)findViewById(R.id.backJoin1);
        username = (EditText)findViewById(R.id.Username);
        password = (EditText)findViewById(R.id.Password);
        password.setTransformationMethod(new PasswordTransformationMethod());
        email = (EditText)findViewById(R.id.Email);
        country = (EditText)findViewById(R.id.Country);
        b = new Bundle();
        n = getIntent();
        forward.setOnClickListener(this);
        back.setOnClickListener(this);
        db = new Database(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.forwardCongrats:
                try {
                    if(Validation())
                    {
                        b = n.getExtras();
                        b.putString("UserName",username.getText().toString());
                        b.putString("Password",password.getText().toString());
                        b.putString("Email",email.getText().toString());
                        b.putString("Country",country.getText().toString());
                        n = new Intent(Join_2.this,Congrats.class);
                        n.putExtras(b);
                        startActivity(n);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.backJoin1:
                startActivity(new Intent(Join_2.this,Join.class));
                break;

        }
    }

    public boolean Validation() throws SQLException {

        final String your_username = username.getText().toString();
        final String your_password = password.getText().toString();
        final String your_email = email.getText().toString();
        final String your_country = country.getText().toString();


        if(your_username.length()==0)
        {
            username.requestFocus();
            username.setError("Please enter a Username.");
            return false;
        }
        else if(!your_username.matches("[a-zA-Z]+"))
        {
            username.requestFocus();
            username.setError("Please enter only Alphabets");
            return false;
        }

        else if(db.getContactThroughUserName(your_username).getCount() > 0)
        {
            showMessageDialog("Username Already exists!","Chose another username");
            return false;
        }

        else if(your_password.length()==0 || your_password.length()<8)
        {
            password.requestFocus();
            password.setError("Please write a password with a minimum length of 6");
            return false;
        }
        else if(!your_email.matches(  "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"))
        {
            email.requestFocus();
            email.setError("Please enter a valid email address");
            return false;
        }
        else if(your_email.length()==0)
        {
            email.requestFocus();
            email.setError("Please enter only Numerical Values");
            return false;
        }

        else if(your_country.length()==0)
        {
            country.requestFocus();
            country.setError("Please enter a Country.");
            return false;
        }
        else if(!your_country.matches("[a-zA-Z]+"))
        {
            country.requestFocus();
            country.setError("Please enter only Alphabets");
            return false;
        }

        else
        {
            Toast.makeText(Join_2.this, "Validation Successful", Toast.LENGTH_LONG).show();
            return true;
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



}
