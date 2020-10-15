package com.ugames.example_engine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.ugames.engine.Prefs;
import com.ugames.engine.coroutine.Coroutine;
import com.ugames.engine.coroutine.ICoroutine;
import com.ugames.engine.coroutine.WaitForSeconds;
import com.ugames.engine.coroutine.YieldInstruction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Prefs.init(this);

        for (int i = 0; i < 1000; i++) {
            Prefs.setString("key", "value");
        }
        Prefs.save();

        Coroutine.startCoroutine(coroutine, this);
    }

    Coroutine coroutine = new Coroutine(coroutine -> {
        final int[] sum = {0};
        coroutine.yield((c) -> {
            new Thread(() -> {
                for(int i = 0; i < 100000; i++) {
                    sum[0] += i;
                }
                c.complete();
            }).start();
        });

        coroutine.main(() -> Toast.makeText(this, sum[0] + " ", Toast.LENGTH_SHORT).show());

    });

}