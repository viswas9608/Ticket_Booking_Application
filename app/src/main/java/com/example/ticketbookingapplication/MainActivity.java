package com.example.ticketbookingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button login,signup;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    EditText email,password;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = openOrCreateDatabase("Ticket_Book",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT INT(10),PASSWORD VARCHAR(20),GENDER VARCHAR(6),CITY VARCHAR(20),DOB VARCHAR(10))";
        db.execSQL(tableQuery);

        email = findViewById(R.id.main_email);
        password = findViewById(R.id.password);


        signup = findViewById(R.id.main_signup);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);*/
                new CommonMethod(MainActivity.this, signupActivity.class);
            }
        });


        login = findViewById(R.id.main_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email.getText().toString().trim().equals("")) {
                    email.setError("Email Id Required");
                }
                else if (!email.getText().toString().trim().matches(emailPattern)){
                    email.setError("Valid Email Id Required");
                }
                else if (password.getText().toString().trim().equals("")) {
                    password.setError("Password Required");
                }
                else if (password.getText().toString().trim().length()<6) {
                    password.setError("Min. 6 Char Password Required");
                }
                else {

                    String selectQuery = "SELECT * FROM USERS WHERE EMAIL='"+email.getText().toString()+"' AND PASSWORD='"+password.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(selectQuery,null);
                    Log.d("CURSOR_COUNT", String.valueOf(cursor.getCount()));

                    if (cursor.getCount()>0) {

                        System.out.println("Login Successfully");
                        //Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        new CommonMethod(MainActivity.this, "Login Successfully");

                        /*Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);*/
                        /* new CommonMethod(MainActivity.this, HomeActivity.class);*/
                    }
                    else {
                        new CommonMethod(MainActivity.this, "Login Unsuccessfully");
                    }
                }

            }
        });
    }
}