package com.example.learnvolleypost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    //create
    TextView editTextKey;
    TextView editTextValue;
    TextView editTextUrl;
    Button button;

    RequestQueue queue;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//init
        editTextKey = findViewById(R.id.editTextKey);
        editTextValue = findViewById(R.id.editTextValue);
        editTextUrl = findViewById(R.id.editTextUrl); //TODO

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener( view -> {
            queue = Volley.newRequestQueue(this);
            String url = editTextUrl.getText().toString();
            String key = editTextKey.getText().toString();
            String value = editTextValue.getText().toString();

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        textView.setText(response);
                    }, error -> {
                textView.setText(error.toString());
                Log.d("HERE", "error");
                Log.e("ERROR:", error.toString());
                if (url.length() == 0) {
                    Log.e("TYPE", "Write your URL..");
                    Toast.makeText(getBaseContext(), "Write your URL..", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("TYPE", "Wrong URL or Bad Key-Value");
                    Toast.makeText(getBaseContext(), "Wrong URL or Bad Key-Value", Toast.LENGTH_SHORT).show();
                }
            }
            ) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    String jsonString = "{\"" + key + "\":\"" + value +"\"}";
                    return jsonString.getBytes(StandardCharsets.UTF_8);
                }
            };
// Add the request to the RequestQueue.
            queue.add(stringRequest);
        });
    }
}