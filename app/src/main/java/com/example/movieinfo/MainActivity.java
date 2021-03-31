package com.example.movieinfo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.et_text);
        ImageButton imageButton = findViewById(R.id.iv_search);
        progressBar = findViewById(R.id.pb_loading);
        textView = findViewById(R.id.tv_result);

        imageButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            if (editText.getText().toString().isEmpty()) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Empty text!!", Toast.LENGTH_SHORT).show();
            } else {
                serchMovie(editText.getText().toString());
            }
        });
    }

    void serchMovie(String title) {
        String url = "https://www.omdbapi.com/?&apikey=c3aec5be&s=" + title;

        @SuppressLint("SetTextI18n") StringRequest req = new StringRequest(url, (String response) -> {
            try {
                progressBar.setVisibility(View.GONE);
                JSONObject resp = new JSONObject(response);
                if (resp.getString("Response").equals("True")) {
                    textView.setText("For \" " + title + " \" " + resp.getString("totalResults") + " results found!!");
                    JSONArray movieArr = resp.getJSONArray("Search");
                    ArrayList<MovieData> movies = new ArrayList<>();
                    for (int i = 0; i < movieArr.length(); i++) {
                        JSONObject movie = movieArr.getJSONObject(i);
                        String poster = movie.getString("Poster");
                        String mTitle = movie.getString("Title");
                        movies.add(new MovieData(poster, mTitle));
                    }
                    recyclerView = findViewById(R.id.rv_showResult);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(new MovieAdapter(movies, MainActivity.this));

                } else {
                    Toast.makeText(MainActivity.this, "NO Result Found!!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, (VolleyError error) -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "Something went wrong!! " + error.toString(), Toast.LENGTH_SHORT).show();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(req);
    }
}