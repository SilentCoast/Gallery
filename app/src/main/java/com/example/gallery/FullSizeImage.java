package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

public class FullSizeImage extends AppCompatActivity {

    ImageView imageViewFullSize;
    ArrayList<Uri> images;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_size_image);

        imageViewFullSize = findViewById(R.id.imageViewFullSize);

        images = getIntent().getBundleExtra("imagesArray").getParcelableArrayList("images");

        position = getIntent().getIntExtra("currentPosition",0);

        imageViewFullSize.setImageURI(images.get(position));

        imageViewFullSize.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()){
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();

                if(position<images.size())
                    position++;
                imageViewFullSize.setImageURI(images.get(position));
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();

                if(position>0){
                    position--;
                }
                imageViewFullSize.setImageURI(images.get(position));
            }
        });

    }

}