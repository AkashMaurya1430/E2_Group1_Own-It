package com.example.ownit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Retrieving the value using its keys the file name
        // must be same in both saving and retrieving the data
//        SharedPreferences sh = getSharedPreferences("UserData", MODE_PRIVATE);
//
//        // The value will be default as empty string because for
//        // the very first time when the app is opened, there is nothing to show
//        String name = sh.getString("name", "");
//        String contact = sh.getString("contact","");
//
//        if(contact != ""){
//            Intent intent = new Intent(login.this, dashboard.class);
//            startActivity(intent);
//        }

        textView = (TextView) findViewById(R.id.loginText3);
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
        } else{
            loginUser();
        }


    }

    private void loginUser() {
        TextInputLayout mobileNumberText = (TextInputLayout)findViewById(R.id.mobileNumber);
        String contact = mobileNumberText.getEditText().getText().toString();

        TextInputLayout passwordText = (TextInputLayout)findViewById(R.id.password);
        String password = passwordText.getEditText().getText().toString();

        User user = new User(contact,password);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://own-it-app-server.herokuapp.com").addConverterFactory(GsonConverterFactory.create()).build();
            JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);
            Call<User> call = jsonPlaceholderAPI.login(user);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.d("Response",response.body().getMessage());
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if( response.body().getMessage().equals("User Logged In")){
                       Intent intent = new Intent(login.this, dashboard.class);
                       startActivity(intent);

                        // Storing data into SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);

                        // Creating an Editor object to edit(write to the file)
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

                        // Storing the key and its value as the data fetched from edittext
                        myEdit.putString("contact", contact);
                        myEdit.putString("userId", response.body().getUserId());


                        // Once the changes have been made,
                        // we need to commit to apply those changes made,
                        // otherwise, it will throw an error
                        myEdit.commit();
                    }


                }

                @Override
                public void onFailure(Call<User> call, Throwable throwable) {
                    Log.d("ResponseErr",throwable.toString());

                }
            });

    }
}