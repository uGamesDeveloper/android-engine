package com.ugames.engine;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.ugames.engine.prefs.Prefs;
import com.ugames.engine.prefs.PrefsLibrary;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestPrefs {

    private enum Lib implements PrefsLibrary {
        Settings,
        Other
    }

    @Test
    public void useAppContext() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.ugames.engine.test", context.getPackageName());
    }

    @Test
    public void testReadAndWrite() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Prefs.init(context);
        String keyInt = "testInt1";
        String keyFloat = "testFloat1";
        String keyLong = "testLong1";
        String keyBool = "testBool1";
        String keyString = "testString1";

        Prefs.setInt(keyInt, 1);
        Prefs.setFloat(keyFloat, 5.5f);
        Prefs.setLong(keyLong, 1L);
        Prefs.setBool(keyBool, true);
        Prefs.setString(keyString, "1");

        Prefs.save();

        assertEquals(1, Prefs.getInt(keyInt, 0));
        assertEquals(5.5f, Prefs.getFloat(keyFloat, -1.0f), 0.0001f);
        assertEquals(1L, Prefs.getLong(keyLong, 0L));
        assertTrue(Prefs.getBool(keyBool, false));
        assertEquals("1", Prefs.getString(keyString, "0"));
    }

    @Test
    public void testOtherLib() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Prefs.initWithOtherLibrary(context, Lib.values());

        Prefs.setInt("testInt1", 20, Lib.Settings);
        Prefs.setInt("testInt1", 10, Lib.Other);
        Prefs.setInt("testInt1", 100);

        Prefs.save(Lib.Settings);
        Prefs.save(Lib.Other);
        Prefs.save();

        assertEquals(Prefs.getInt("testInt1", 0, Lib.Settings), 20);
        assertEquals(Prefs.getInt("testInt1", 0, Lib.Other), 10);
        assertEquals(Prefs.getInt("testInt1", 0), 100);

        Prefs.deleteKey("testInt1", Lib.Settings);
        Prefs.deleteAll(Lib.Other);

        Prefs.save(Lib.Settings);
        Prefs.save(Lib.Other);



        assertEquals(Prefs.getInt("testInt1", -1, Lib.Settings), -1);
        assertEquals(Prefs.getInt("testInt1", -1, Lib.Other), -1);

    }

    @Test
    public void testDeleteKey() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Prefs.init(context);

        String testString2 = "testString2";

        Prefs.setString(testString2, "test");
        Prefs.save();

        assertNotEquals(Prefs.getString(testString2, "defaultValue"), testString2);

        Prefs.deleteKey(testString2);
        Prefs.save();

        assertEquals(Prefs.getString(testString2, "defaultValue"), "defaultValue");
    }

    @Test
    public void testDeleteAll() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Prefs.init(context);


        for (int i = 0; i < 1000; i++) {
            Prefs.setString("testStringDelete" + i, "testString:" + i);
        }
        Prefs.save();
        for (int i = 0; i < 1000; i++) {
            assertEquals(Prefs.getString("testStringDelete" + i, "null"), "testString:" + i);
        }
        Prefs.showAll();

        Prefs.deleteAll();
        Prefs.save();

        for (int i = 0; i < 1000; i++) {
            assertFalse(Prefs.hasKey("testStringDelete" + i));
        }
    }




}