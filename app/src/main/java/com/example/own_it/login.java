package com.example.own_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class login extends AppCompatActivity {
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView = (TextView) findViewById(R.id.loginText2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private Boolean validateMobileNo(){
        TextInputLayout mobileNumber = (TextInputLayout)findViewById(R.id.mobileNumber);

        String val = mobileNumber.getEditText().getText().toString();

        if (val.isEmpty()){
            mobileNumber.setError("Field cannot be empty");
            return false;
        }else if(val.length() != 10){
            mobileNumber.setError("Invalid contact");
            return false;
        } else{
            mobileNumber.setError(null);
            return true;
        }
    }

    private Boolean validatePassword(){
        TextInputLayout password = (TextInputLayout)findViewById(R.id.password);

        String val = password.getEditText().getText().toString();

        if (val.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }else{
            password.setError(null);
            return true;
        }
    }

    public void login(View view){
        if(!validateMobileNo() | !validatePassword()){
            return;
        }

        Intent intent = new Intent(login.this,dashboard.class);
        startActivity(intent);
    }
}