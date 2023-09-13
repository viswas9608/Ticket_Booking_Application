package com.example.ticketbookingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class signupActivity extends AppCompatActivity {


    Button login,signup;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    EditText name,email,contact,password,confirmPassword,dob;

    //RadioButton male, female;
    RadioGroup gender;
    
    Spinner city;
    //String[] cityArray = {"City","Gir Somnath","Ahmedabad","Baroda","Surat","Rajkot","Jamnagar","Bhuj","Junagadh","Veraval","Diu","Mahesana","Dahod","Bhavanagar"};

    String sGENDER;

    SQLiteDatabase db;
    ArrayList<String> arrayList;

    Calendar calendar;

    String sCity ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = openOrCreateDatabase("Ticket_Book",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT INT(10),PASSWORD VARCHAR(20),GENDER VARCHAR(6),CITY VARCHAR(20),DOB VARCHAR(10))";
        db.execSQL(tableQuery);

        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        contact = findViewById(R.id.signup_contact);
        password = findViewById(R.id.signup_password);
        confirmPassword = findViewById(R.id.confirm_password);

        dob = findViewById(R.id.signup_dob);

        calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateClick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                dob.setText(sdf.format(calendar.getTime()));
            }
        };

        dob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(signupActivity.this,dateClick,calendar.get(calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        city = findViewById(R.id.signup_city);

        arrayList = new ArrayList<>();

        arrayList.add("Select City");
        arrayList.add("Gir somnath");
        arrayList.add("Keshod");
        arrayList.add("Junagadh");
        arrayList.add("Virpur");
        arrayList.add("Jetpur");
        arrayList.add("Gondel");
        arrayList.add("Rajkot");
        arrayList.add("Chotila");
        arrayList.add("Limdi");
        arrayList.add("Ahmedabad");

        ArrayAdapter adapter = new ArrayAdapter(signupActivity.this, android.R.layout.simple_list_item_1,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        city.setAdapter(adapter);

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0) {
                    sCity = "";
                }
                else {
                    sCity = arrayList.get(i);
                    new CommonMethod(signupActivity.this, sCity);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gender = findViewById(R.id.signup_gender);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                RadioButton radioButton = findViewById(i); //i = R.id.signup_male,R.id.signup_female;
                sGENDER = radioButton.getText().toString();
                new CommonMethod(signupActivity.this, sGENDER);
            }
        });

        //male = findViewById(R.id.signup_male);
        //female = findViewById(R.id.signup_female);

        /*male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommonMethod(signupActivity.this,male.getText().toString());
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommonMethod(signupActivity.this,female.getText().toString());
            }
        });*/

        signup = findViewById(R.id.signup_signup);
        login = findViewById(R.id.signup_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().trim().equals("")){
                    name.setError("Name Required");
                }
                else if (email.getText().toString().trim().equals("")) {
                    email.setError("Email Id Required");
                }
                else if (!email.getText().toString().trim().matches(emailPattern)){
                    email.setError("Valid Email Id Required");
                }
                else if (contact.getText().toString().trim().equals("")){
                    contact.setError("Contact No. Required" );
                }
                else if (contact.getText().toString().trim().length()<10){
                    contact.setError("Valid Contact No. Required" );
                }
                else if (password.getText().toString().trim().equals("")) {
                    password.setError("Password Required");
                }
                else if (password.getText().toString().trim().length()<6) {
                    password.setError("Min. 6 Char Password Required");
                }
                else if (confirmPassword.getText().toString().trim().equals("")) {
                    confirmPassword.setError("Confirm Password Required");
                }
                else if (confirmPassword.getText().toString().trim().length()<6) {
                    confirmPassword.setError("Min. 6 Char Confirm Password Required");
                }
                else if (!confirmPassword.getText().toString().trim().matches(password.getText().toString().trim())) {
                    confirmPassword.setError("Password Does Not Match");
                } else if (gender.getCheckedRadioButtonId() == -1) {
                     new CommonMethod(signupActivity.this,"please Select Gender");
                } else if (sCity.equals("")) {
                    new CommonMethod(signupActivity.this,"Please Select Your City");
                } else if (dob.getText().toString().trim().equals("")) {
                    dob.setError("Please Select Date of Birth");
                } else {

                    String insertQuery = "INSERT INTO USERS VALUES(NULL,'"+name.getText().toString()+"','"+email.getText().toString()+"','"+contact.getText().toString()+"','"+password.getText().toString()+"','"+sGENDER+"','"+sCity+"','"+dob.getText().toString()+"')";
                    db.execSQL(insertQuery);

                    System.out.println("Signup Successfully");
                    new CommonMethod(signupActivity.this, "Signup Successfully");
                    onBackPressed();
                }

            }
        });
    }
}