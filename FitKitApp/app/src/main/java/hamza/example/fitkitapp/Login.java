package hamza.example.fitkitapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.SQLException;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button login;
    ImageView forward,back;
    EditText username,password;
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.Btn_Login2);
        forward =(ImageView)findViewById(R.id.forwardHome);
        back =(ImageView)findViewById(R.id.backSL2);
        username = (EditText)findViewById(R.id.Username);
        password = (EditText)findViewById(R.id.Password);
        //password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setTransformationMethod(new PasswordTransformationMethod());
        db = new Database(this);

        login.setOnClickListener(this);
        forward.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.Btn_Login2:
                try {
                    if(Validation()) {
                        save(username.getText().toString(),password.getText().toString());
                        startActivity(new Intent(Login.this, Home.class));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.forwardHome:
                try {
                    if(Validation()) {
                        save(username.getText().toString(),password.getText().toString());
                        startActivity(new Intent(Login.this, Home.class));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.backSL2:
                startActivity(new Intent(Login.this,SignUp_LogIn.class));
        }
    }

    public boolean Validation() throws SQLException {

        final String your_username = username.getText().toString();
        final String your_password = password.getText().toString();

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
        else if(db.getLoginDetails(your_username,your_password).getCount() == 0)
        {
            showMessageDialog("Account not Found!", "Try again with different Login Credentials");
            return false;
        }
        else if(your_password.length()==0 || your_password.length()<8)
        {
            password.requestFocus();
            password.setError("Please write a password with a minimum length of 6");
            return false;
        }

        else
        {
            Toast.makeText(Login.this, "Validation Successful", Toast.LENGTH_LONG).show();
            return true;
        }
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

    public void showMessageDialog(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
