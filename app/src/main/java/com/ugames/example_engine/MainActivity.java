package com.ugames.example_engine;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.ugames.engine.coroutine.Coroutine;
import com.ugames.engine.coroutine.instructions.WaitForSeconds;
import com.ugames.engine.refs.Out;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Coroutine.startCoroutine(coroutine, this);
    }

    void mainFunction() {
        Out<Integer> integer = new Out<>();
        toChange(integer);
        Log.e("tag", "Log: " + integer.value.toString());    //Log: 100
    }

    void toChange(Out<Integer> integer) {
        integer.value = 100;
    }

    Coroutine coroutine = new Coroutine(c -> {
        Out<String> out = new Out<>();
        c.yield(() -> new WaitForSeconds(1, c));
        c.yield(() -> {
            out.value = "newString";
            c.complete();
        });
        c.main(() -> Log.e("tag", out.value));
    });
}