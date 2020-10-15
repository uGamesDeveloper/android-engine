package com.ugames.example_engine;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.ugames.engine.prefs.MigrationPrefsEnum;
import com.ugames.engine.prefs.Prefs;
import com.ugames.engine.coroutine.Coroutine;
import com.ugames.engine.coroutine.instructions.WaitForSeconds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Prefs.init(this);
        Prefs.migration(Migration.values());

        for (int i = 0; i < 1000; i++) {
            Prefs.setString("key", "value");
        }
        Prefs.save();

        Coroutine.startCoroutine(coroutine, this);
    }

    public enum Migration implements MigrationPrefsEnum {
        FirstElement,
        SecondElement,
        Settings
    }

    Coroutine coroutine = new Coroutine(c -> {
        for (int i = 0; i < 20; i++) {
            c.yield(() -> new WaitForSeconds(1, c));
            c.main(() -> Log.e("TEST111", "log"));
        }
    });

    Coroutine coroutine2 = new Coroutine(c -> {
        c.yield(() -> new WaitForSeconds(5.2f, c));
        c.main(this::stopTimeCoroutine);
    });


    private void stopTimeCoroutine() {
        Coroutine.stopCoroutine(coroutine);
    }

}