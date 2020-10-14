package com.ugames.example_engine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ugames.engine.Prefs;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Prefs.init(this);

        for(int i = 0; i < 1000; i++) {
            Prefs.setString("key", "value");
        }
        Prefs.save();
    }


}