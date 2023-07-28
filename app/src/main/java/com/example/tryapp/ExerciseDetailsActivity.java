package com.example.tryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ExerciseDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);

        // Retrieve exercise details from the Intent
        Intent intent = getIntent();
        String bodyPart = intent.getStringExtra("bodyPart");
        String equipment = intent.getStringExtra("equipment");
        String gifUrl = intent.getStringExtra("gifUrl");
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String target = intent.getStringExtra("target");

        // Get references to the views in the layout
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewBodyPart = findViewById(R.id.textViewBodyPart);
        TextView textViewTarget = findViewById(R.id.textViewTarget);
        TextView textViewEquipment = findViewById(R.id.textViewEquipment);
        TextView textViewId = findViewById(R.id.textViewId);
        ImageView imageViewGif = findViewById(R.id.imageViewGif);

        // Set the exercise details to the views
        textViewName.setText(name);
        textViewBodyPart.setText("Body Part: " + bodyPart);
        textViewTarget.setText("Target: " + target);
        textViewEquipment.setText("Equipment: " + equipment);
        textViewId.setText("ID: " + id);

        // Load the GIF image using a library like Glide or Picasso
        Glide.with(this).load(gifUrl).into(imageViewGif);
    }
}