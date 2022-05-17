package com.example.gallery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickListener {


    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    Button button;
    ArrayList<String> data ;
    ArrayList<Uri> images;
    ArrayList<Drawable> imagesImprove;
    InputStream inputStream;

    public static final int PICK_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data  = new ArrayList<>();
        images = new ArrayList<>();
        imagesImprove = new ArrayList<>();

        button = findViewById(R.id.buttonAdd);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),4));
        recyclerAdapter = new RecyclerAdapter(getApplicationContext(),images);
        recyclerAdapter.setClickListener(this);
        recyclerView.setAdapter(recyclerAdapter);








        button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  Intent intent = new Intent();
                  intent.setType("image/*");
                  intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                  intent.setAction(Intent.ACTION_GET_CONTENT);
                  startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
              }
          });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE) {
            Uri uri = null;
            Drawable drawable;
            if (data.getData() == null) {
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        uri = clipData.getItemAt(i).getUri();
                       images.add(uri);
                    }
                }
            }else{

                uri = data.getData();

            }
            images.add(uri);
//            try {
//                InputStream inputStream= getContentResolver().openInputStream(uri);
//                drawable = Drawable.createFromStream(inputStream,uri.toString());
//                imagesImprove.add(drawable);
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
        }
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d("HJF", "onItemClick: clicked on item");
        Intent intent = new Intent(getApplicationContext(),FullSizeImage.class);
//        Bitmap bitmap = BitmapFactory
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("images",images);

        intent.putExtra("currentPosition",position);
        intent.putExtra("imagesArray",bundle);
        //intent.putExtra("imagesArrayList",images);
        startActivity(intent);


    }
}