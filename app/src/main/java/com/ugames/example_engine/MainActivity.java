package com.ugames.example_engine;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ugames.engine.Prefs;
import com.ugames.engine.coroutine.Coroutine;
import com.ugames.engine.coroutine.instructions.WaitForSeconds;
import com.ugames.engine.refs.Out;
import com.ugames.engine.refs.Ref;

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
        Out<String> out = new Out<>();

        update(out);
        Log.e("TEST111", out.value);
    }

    void update(Out<String> out) {
        out.value = "2221";
    }

    Coroutine coroutine = new Coroutine(c -> {
        final int[] sum = {0};
        c.yield(() -> {
            /*new Thread(() -> {
                for(int i = 0; i < 10000000; i++) {
                    sum[0] += i;
                }
                c.complete();
            }).start();*/
            new WaitForSeconds(3, c);
        });

        c.main(() -> Toast.makeText(this, sum[0] + " ", Toast.LENGTH_SHORT).show());

    });

}