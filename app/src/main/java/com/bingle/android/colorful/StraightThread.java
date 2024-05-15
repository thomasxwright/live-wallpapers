package com.bingle.android.colorful;

import android.graphics.Canvas;
import android.graphics.Paint;

public class StraightThread {

    private Threadbulb[] bulbs;
    private int xPos;

    public StraightThread(int xPos) {
        int numBulbs = (int) (Math.random() * 5) + 3;
        bulbs = new Threadbulb[numBulbs];

        for (int i = 0; i < numBulbs; i++) {
            bulbs[i] = new Threadbulb();
        }
        this.xPos = xPos;
    }

    public void advance() {
        for (Threadbulb bulb : bulbs) {
            bulb.advanceBulb();
        }
    }

    public Threadbulb[] getBulbs() {
        return bulbs;
    }

    public void drawBulbs(Canvas canvas, Paint paint) {
        for (Threadbulb bulb : bulbs) {
            bulb.drawBulb(canvas, paint, xPos);
        }
    }
}