package com.example.bluejackkoslab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluejackkoslab.helper.DatabaseHelperLogin;

import java.util.Calendar;

public class Register extends AppCompatActivity {
    private EditText username, password, phonenumber, confirmpassword;
    private RadioGroup radio;
    private TextView DOB;
    private CheckBox check;
    private Button btn;
    private RadioButton rb;
    private int radioId;
    private String gender;
    DatabaseHelperLogin dblog;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dblog = new DatabaseHelperLogin(Register.this);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        username = findViewById(R.id.et_regusername);
        password = findViewById(R.id.et_regpassword);
        phonenumber = findViewById(R.id.et_regphonenumber);
        confirmpassword = findViewById(R.id.et_confirmpassword);
        radio = findViewById(R.id.rg_reg);
        DOB = findViewById(R.id.reg_date);
        check = findViewById(R.id.reg_check);
        btn = findViewById(R.id.btn_regform);
        radioId = radio.getCheckedRadioButtonId();
        rb = findViewById(radioId);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                DOB.setText(date);
            }
        };
    }

    private void validate() {
        String Username = username.getText().toString().trim();
        String Password = password.getText().toString().trim();
        String Phonenumber = phonenumber.getText().toString().trim();
        String Tanggal = DOB.getText().toString().trim();
        String Cpass = confirmpassword.getText().toString().trim();


        if (Username.length() == 0 && Phonenumber.length() == 0 && Password.length() == 0 ) {
            username.requestFocus();
            username.setError("FIELD CANNOT BE EMPTY");
            phonenumber.requestFocus();
            phonenumber.setError("FIELD CANNOT BE EMPTY");
            password.requestFocus();
            password.setError("FIELD CANNOT BE EMPTY");
        } else if(!valradio(radio)) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_LONG).show();
        } else if(!valdate(DOB)) {
            Toast.makeText(this, "Please Select your Date of Birth",Toast.LENGTH_LONG).show();
        } else if (!check.isChecked()) {
            Toast.makeText(this, "You must agree with the Term & Condition", Toast.LENGTH_LONG).show();
        }else if (!Username.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?!.*[_.]{2})[a-zA-Z0-9_.].{3,25}$")) {
            username.requestFocus();
            username.setError("Username must be 3 - 25 characters and contain minimal 1 number and alphabetic");
        } else if (!Phonenumber.matches("^0?[0-9].{10,12}$")) {
            phonenumber.requestFocus();
            phonenumber.setError("Enter a valid phone number (Start with 0 , 10 - 12 length)");
        } else if (!Password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{7,}$")) {
            password.requestFocus();
            password.setError("Password must be longer than 6 characters and password must contain at least 1 lowercase letter, 1 uppercase letter and 1 digit");
        } else if (!Cpass.equals(Password)) {
            confirmpassword.requestFocus();
            confirmpassword.setError("Password not match");
        } else {
            boolean isUsernameExist = dblog.checkUsername(Username);
            if (!isUsernameExist) {
                // notify user
                Toast.makeText(this, "Username Already Exist.", Toast.LENGTH_SHORT).show();

            } else {
                // proceed
                int num = dblog.Count() + 1;
                String code = String.format("%03d",num);
                String userId = "US"+ code;
                dblog.addData(userId,
                        username.getText().toString().trim(),
                        phonenumber.getText().toString().trim(),
                        password.getText().toString().trim(),
                        gender,
                        DOB.getText().toString().trim());
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            //insert the details on database
        }


    }
    private boolean valradio(RadioGroup Rg) {
        int id = Rg.getCheckedRadioButtonId();
        if (id == -1) {
            return false;
        }
        RadioButton rb = findViewById(id);
        gender = (String) rb.getText();
        return true;
    }

    private boolean valdate(TextView DOB){
        String dob = DOB.getText().toString();

        if(dob == ""){
            return false;
        }
        else{
            return true;
        }
    }
}



















//        if(valusername(username) && valphonenumber(phonenumber) && valpassword(password) && valconfirm(password,confirmpassword) &&
//                valradio(radio) && valdate(DOB) && valcheck(check) )
//        {
//            int num =
//            String code = String.format("%03d",num);
//            String userId = "US"+ code;
//            StorageUser.users.add(new User(userId,Username,Password,Phonenumber,gender,Tanggal));
//            Toast.makeText(this, "Register Successful", Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(Register.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }
//
//    private boolean valusername(EditText Username) {
//        String un = Username.getText().toString();
//        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?!.*[_.]{2})[a-zA-Z0-9_.].{3,25}$";
//        int value = 0;
//
//        for (User user: userDatas) {
//            if (user.getUsername().equals(un)) {
//                value = 1;
//                break;
//            }
//        }
//
//        if(!un.matches(regex))
//        {
//            username.requestFocus();
//            username.setError("Username must be 3 - 25 characters and contain minimal 1 number and alphabetic");
//            return false;
//        }
//        else if(value == 1)
//        {
//            username.requestFocus();
//            username.setError("Username not available");
//            return false;
//        }
//        else
//        {
//            return true;
//        }
//    }
//
//    private boolean valpassword(EditText Password) {
//        String pw = Password.getText().toString();
//        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{7,}$";
//
//        if(!pw.matches(regex))
//        {
//            password.requestFocus();
//            password.setError("Password must be longer than 6 characters and password must contain at least 1 lowercase letter, 1 uppercase letter and 1 digit");
//            return false;
//        }
//        else
//        {
//            return true;
//        }
//    }
//
//    private boolean valphonenumber(EditText Phonenumber) {
//        String phone = Phonenumber.getText().toString();
//        String regex = "^0?[0-9].{10,12}$";
//
//        if(!phone.matches(regex))
//        {
//            phonenumber.requestFocus();
//            phonenumber.setError("Enter a valid phone number");
//            return false;
//        }
//        else
//        {
//            return true;
//        }
//    }
//
//    private boolean valconfirm(EditText Password, EditText Confirmpassword) {
//        String cnfrm = Confirmpassword.getText().toString();
//        String pw = Password.getText().toString();
//
//        if(!cnfrm.equals(pw))
//        {
//            confirmpassword.requestFocus();
//            confirmpassword.setError("Password not match");
//            return false;
//        }
//        else
//        {
//            return true;
//        }
//    }
//
//    private boolean valradio(RadioGroup Rg)
//    {
//        int id = Rg.getCheckedRadioButtonId();
//        if(id == -1){
//            Toast.makeText(this, "Please select your gender",Toast.LENGTH_LONG).show();
//            return false;
//        }
//        RadioButton rb = findViewById(id);
//        gender = (String) rb.getText();
//        return true;
//    }
//
//    private boolean valcheck(CheckBox Check){
//        if (!Check.isChecked())
//        {
//            Toast.makeText(this, "You must agree with the Term & Condition",Toast.LENGTH_LONG).show();
//            return false;
//        }
//        else{
//            return true;
//        }
//
//    }
//
//    private boolean valdate(TextView DOB){
//        String dob = DOB.getText().toString();
//
//        if(dob == ""){
//            Toast.makeText(this, "Please Select your Date of Birth",Toast.LENGTH_LONG).show();
//            return false;
//        }
//        else{
//            return true;
//        }
//    }
//}
