package com.example.ownit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
//import com.google.android.gms.cast.framework.media.ImagePicker;
//import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("unchecked")
public class createadd extends AppCompatActivity {
    public TextView textView;
    AutoCompleteTextView autoCompleteTextView;
    ImageView advImage;
    FloatingActionButton uploadImgBtn;
    boolean isImageAdded = false;
    private Uri ImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createadd);



        // Set categories
        autoCompleteTextView  = findViewById(R.id.categoriesInput);
        String []option = {"Book","ED Instruments","Lab Coat"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.categories_option,option);
         autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(),false );

        autoCompleteTextView.setAdapter(arrayAdapter);

        // Image Upload
        advImage = findViewById(R.id.advImage);
        uploadImgBtn = findViewById(R.id.uploadImageBtn);

        uploadImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(createadd.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
//                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });
    }

    private Boolean validateTitle(){
        TextInputLayout title = (TextInputLayout)findViewById(R.id.addTitle);

        String val = title.getEditText().getText().toString();

        if (val.isEmpty()){
            title.setError("Field cannot be empty");
            return false;
        }else{
            title.setError(null);
            return true;
        }
    }

    private Boolean validateDescription(){
        TextInputLayout description = (TextInputLayout)findViewById(R.id.addDescription);

        String val = description.getEditText().getText().toString();

        if (val.isEmpty()){
            description.setError("Field cannot be empty");
            return false;
        }else{
            description.setError(null);
            return true;
        }
    }

    private Boolean validatePrice(){
        TextInputLayout price = (TextInputLayout)findViewById(R.id.addPrice);

        String val = price.getEditText().getText().toString();

        if (val.isEmpty()){
            price.setError("Field cannot be empty");
            return false;
        }else{
            price.setError(null);
            return true;
        }
    }

    private Boolean validateImageUploaded(){
        if(isImageAdded){
            return true;
        }else{
            Toast.makeText(getApplicationContext(),"Upload Image",Toast.LENGTH_SHORT);
            return false;
        }
    }

    public void createAdver(View view) {
        if(!validateTitle() | !validateDescription() | !validatePrice() | !validateImageUploaded()){
            return;
        } else{
            saveAdvertisement();

        }

    }

    private void saveAdvertisement() {
        TextInputLayout titleText = (TextInputLayout)findViewById(R.id.addTitle);
        String title = titleText.getEditText().getText().toString();

        TextInputLayout descText = (TextInputLayout)findViewById(R.id.addDescription);
        String description = descText.getEditText().getText().toString();

        TextInputLayout priceText = (TextInputLayout)findViewById(R.id.addPrice);
        String price = priceText.getEditText().getText().toString();

        TextInputLayout categoryText = (TextInputLayout)findViewById(R.id.addCategory);
        String category = categoryText.getEditText().getText().toString();

        File file = new File(getRealPathFromURI(ImageUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part requestImage = MultipartBody.Part
                .createFormData("photo",file.getName(),requestFile);

        // Retrieving the value using its keys the file name
        // must be same in both saving and retrieving the data
        SharedPreferences sh = getSharedPreferences("UserData", MODE_PRIVATE);

        // The value will be default as empty string because for
        // the very first time when the app is opened, there is nothing to show
        String sessionId = sh.getString("userId","");


        RequestBody userId =
                RequestBody.create(MediaType.parse("multipart/form-data"), sessionId);
        RequestBody titleBody = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody descriptionBody = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody priceBody = RequestBody.create(MediaType.parse("text/plain"), price);
        RequestBody categoryBody = RequestBody.create(MediaType.parse("text/plain"), category);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://own-it-app-server.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create()).build();

        JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);
        Call<Advertisement> call = jsonPlaceholderAPI
                .createAdv(userId,titleBody,descriptionBody,categoryBody,requestImage,priceBody);


        call.enqueue(new Callback<Advertisement>() {
            @Override
            public void onResponse(Call<Advertisement> call, Response<Advertisement> response) {
                Log.d("Response",response.body().getMessage());
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                if(response.body().getMessage().equals("Adv Created Successfully")){
                    Intent intent = new Intent(createadd.this, dashboard.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Advertisement> call, Throwable throwable) {
                Log.w("ResponseErr",throwable);

            }
        });



    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageUri = data.getData();
        advImage.setImageURI(ImageUri);
        isImageAdded = true;

    }


}
