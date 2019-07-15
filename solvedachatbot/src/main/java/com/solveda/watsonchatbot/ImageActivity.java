package com.solveda.watsonchatbot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatkit_activity_image);
        imageView = findViewById(R.id.image);
        String url = getIntent().getStringExtra("url");
        if(url!=null && !url.isEmpty())
        {
            Picasso.with(this).load(url).into(imageView);
        }
        else
        {
            Toast.makeText(this,"Something went wrong!",Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
