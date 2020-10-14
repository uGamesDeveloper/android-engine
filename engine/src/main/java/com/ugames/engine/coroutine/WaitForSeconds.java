package com.ugames.engine.coroutine;


import android.os.Handler;

public class WaitForSeconds {

    public WaitForSeconds(float seconds, final Coroutine coroutine) {
        long milliseconds = (long) (seconds * 100);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                coroutine.complete();
            }
        };

        new Handler().postDelayed(runnable, milliseconds);
    }


}
