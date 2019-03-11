package com.chong.testdex;

import android.media.MediaPlayer;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    P p1 = new P(23);

    @Test
    public void testInt() {

        P a = new P(1);

        tes(a);
        System.out.println(a);
    }


    public P tes(P p) {
        p = p1;
        return p;
    }

    class P {
        int a;

        public P(int a) {
            this.a = a;
        }

        @Override
        public String toString() {
            return "P{" +
                    "a=" + a +
                    '}';
        }
    }
}