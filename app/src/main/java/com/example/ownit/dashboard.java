package com.example.ownit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.AuthProvider;
import java.util.ArrayList;
import java.util.List;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import javax.security.auth.login.LoginException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class dashboard extends AppCompatActivity implements PostsAdapter.OnImageListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        RecyclerView postsRecyclerView = findViewById(R.id.postsRecyclerView);
        postsRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://own-it-app-server.herokuapp.com").addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);
        Call<List<Advertisement>> call = jsonPlaceholderAPI.getAdvertisements();
        call.enqueue(new Callback<List<Advertisement>>() {
            @Override
            public void onResponse(Call<List<Advertisement>> call, Response<List<Advertisement>> response) {
                List<Advertisement> posts = response.body();

            }

            @Override
            public void onFailure(Call<List<Advertisement>> call, Throwable t) {

//                textViewResult.setText(t.getMessage());
            }
        });
        List<postitem> postitems = new ArrayList<>();
        postitems.add(new postitem(R.drawable.book1));
        postitems.add(new postitem(R.drawable.book2));
        postitems.add(new postitem(R.drawable.book3));
        postitems.add(new postitem(R.drawable.book4));
        postitems.add(new postitem(R.drawable.book5));
        postitems.add(new postitem(R.drawable.book6));
        postitems.add(new postitem(R.drawable.book7));
        postitems.add(new postitem(R.drawable.image1));
        postitems.add(new postitem(R.drawable.image2));
        postitems.add(new postitem(R.drawable.image3));
        postitems.add(new postitem(R.drawable.image4));
        postitems.add(new postitem(R.drawable.image5));
        postitems.add(new postitem(R.drawable.image6));
        postitems.add(new postitem(R.drawable.image7));
        postitems.add(new postitem(R.drawable.image8));
        postitems.add(new postitem(R.drawable.image9));
        postitems.add(new postitem(R.drawable.image10));
        postitems.add(new postitem(R.drawable.cosplay));


        postsRecyclerView.setAdapter(new PostsAdapter(postitems,this::onImageClick));






    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            AuthProvider sessionManager = null;
            if (sessionManager != null) {
                try {
                    sessionManager.logout();
                } catch (LoginException e) {
                    e.printStackTrace();
                }
            }
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void ShowDialer(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:9969846553"));
        startActivity(intent);
    }


    public void form(View view) {
        Intent intent = new Intent(dashboard.this, createadd.class);
        startActivity(intent);
    }

    @Override
    public void onImageClick(int position) {

        Intent intent = new Intent(this,DetailsActivity.class);
        startActivity(intent);
    }

}
