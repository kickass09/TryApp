package com.example.tryapp;// MainActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tryapp.Exercise;
import com.example.tryapp.ExerciseAdapter;
import com.example.tryapp.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.*;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExerciseAdapter exerciseAdapter;
    private List<Exercise> exerciseList;

    // ExerciseDB API constants
    private static final String API_URL = "https://exercisedb.p.rapidapi.com/exercises";
    private static final String API_KEY = "29fa35a3famsh33afaf21dacf203p1d6c6bjsn353f5cd31aa7";
    private static final String API_HOST = "exercisedb.p.rapidapi.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        exerciseList = new ArrayList<>();
        exerciseAdapter = new ExerciseAdapter(exerciseList);
        Log.d("exerciseListinit", String.valueOf(exerciseList));
        recyclerView.setAdapter(exerciseAdapter);

        fetchExerciseData();


    }

    private void fetchExerciseData() {
        OkHttpClient client = new OkHttpClient();
        String apiUrl = API_URL;

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("X-RapidAPI-Key", API_KEY)
                .header("X-RapidAPI-Host", API_HOST)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                // Handle API request failure
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Log.d("msg",responseBody);

                    try {
                        // Check if the response is a JSON array
                        if (responseBody.startsWith("[")) {
                            JSONArray exerciseArray = new JSONArray(responseBody);
                            for (int i = 0; i < exerciseArray.length(); i++) {
                                JSONObject exerciseObject = exerciseArray.getJSONObject(i);

                                String bodyPart = exerciseObject.getString("bodyPart");
                                String equipment = exerciseObject.getString("equipment");
                                String gifUrl = exerciseObject.getString("gifUrl");
                                String id = exerciseObject.getString("id");
                                String name = exerciseObject.getString("name");
                                String target = exerciseObject.getString("target");

                                // Parse other exercise data fields

                                Exercise exercise = new Exercise(bodyPart,equipment,gifUrl,id,name,target);
                                exerciseList.add(exercise);
//                                Log.d("exerciseList0", String.valueOf(exerciseList));
//                                Log.d("exerciseList1", String.valueOf(exercise));
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    exerciseAdapter.notifyDataSetChanged();


//                                    Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
//                                    startActivity(intent);

                                }
                            });
                        } else {
                            // Handle error: Unexpected JSON response format (not a JSON array)
                            Log.e("ExerciseApp", "Unexpected JSON response format: " + responseBody);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Handle JSON parsing error
                        Log.e("ExerciseApp", "JSON parsing error: " + e.getMessage());
                    }
                } else {
                    // Handle API request failure (non-successful response)
                }
            }
        });
    }

}
