package com.example.mycamera_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    EditText username , password ;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        username = findViewById(R.id.etName);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(username.getText().toString() , password.getText().toString());

            }
        });

    }

    private void validate( String Name , String Password) {

        if ((Name.equals("admin")) && (Password.equals("pass"))) {
            Toast.makeText(this,"Login Successful" , Toast.LENGTH_LONG).show();
            Intent i = new Intent(this , MainActivity.class);
            startActivity(i);
        }

        else {
            Toast.makeText(this,"Login Failed" , Toast.LENGTH_LONG).show();

        }
    }
}
