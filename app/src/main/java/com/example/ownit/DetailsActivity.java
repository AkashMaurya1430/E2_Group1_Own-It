package com.example.ownit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
private ImageView imageView;
private TextView title,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageView=findViewById(R.id.imageView);
//        title=findViewById(R.id.detailTitle);
//        description=findViewById(R.id.detailDescription);

        imageView.setImageResource(getIntent().getIntExtra("image",0 ));
//        title.setText(getIntent().getStringExtra("title"));
//        description.setText(getIntent().getStringExtra("describe"));

    }
}
