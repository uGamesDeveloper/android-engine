package com.ugames.example_engine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.ugames.engine.Prefs;
import com.ugames.engine.coroutine.Coroutine;
import com.ugames.engine.coroutine.ICoroutine;
import com.ugames.engine.coroutine.WaitForSeconds;
import com.ugames.engine.coroutine.YieldInstruction;

public class MainActivity extends AppCompatActivity {

    //72 94 50
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Prefs.init(this);

        for (int i = 0; i < 1000; i++) {
            Prefs.setString("key", "value");
        }
        Prefs.save();

        /*Coroutine.startCoroutine(coroutine);
        Coroutine.startCoroutine(coroutine2);*/
    }

    Coroutine coroutine = new Coroutine(coroutine -> {
        for (int i = 0; i < 100000; i++) {
            coroutine.yield((c) -> {
                new WaitForSeconds(0.02f, c);
            });

            int finalI = i;
            coroutine.main(() -> Log.e("TEST111", finalI + " "));
        }
    });

    Coroutine coroutine2 = new Coroutine(coroutine -> {
        coroutine.main(() -> Log.e("Test111", "1"));

        coroutine.yield((с) -> {
            Runnable runnable = () -> {
                Log.e("TEST111", "2");
                с.complete();
            };
            new Handler().postDelayed(runnable, 2000);
        });
        coroutine.yield((с) -> {
            Runnable runnable = () -> {
                Log.e("TEST111", "3");
                с.complete();
            };
            new Handler().postDelayed(runnable, 2000);
        });

        coroutine.main(() -> {
            Log.e("Test111", "4");
        });
    });

}