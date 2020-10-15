package com.ugames.engine.coroutine.instructions;

import android.os.Handler;

import com.ugames.engine.coroutine.Coroutine;

public class WaitForSeconds {

    public WaitForSeconds(float seconds, final Coroutine coroutine) {

        long milliseconds = (long) (seconds * 1000);

        Runnable runnable = coroutine::complete;

        new Handler().postDelayed(runnable, milliseconds);
    }
}
