package com.ugames.engine;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.ugames.engine.refs.Out;
import com.ugames.engine.refs.Ref;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TestRefs {

    private String editTestString = "editTestString";

    @Test
    public void testSimpleObject() {
        String testString = "testString";
        updateObject(testString);
        assertEquals(testString, "testString");
    }

    @Test
    public void testRef() {
        String testString = "testString";
        Ref<String> ref = new Ref<>(testString) ;
        updateObjectRef(ref);
        assertEquals(ref.value, editTestString);
    }

    @Test
    public void testOut() {
        String testString = "testString";
        Out<String> out = new Out<>() ;
        updateObjectOut(out);
        assertEquals(out.value, editTestString);
    }

    private void updateObjectRef(Ref <String> ref) {
        ref.value = editTestString;
    }

    private void updateObject(String object) {
        object = editTestString;
    }

    private void updateObjectOut(Out <String> out) {
        out.value = editTestString;
    }
}
