package com.solveda.watsonchatbot;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

//import com.squareup.picasso.Picasso;

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
            Glide.with(this).load(url).into(imageView);
        }
        else
        {
            Toast.makeText(this,"Something went wrong!",Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
