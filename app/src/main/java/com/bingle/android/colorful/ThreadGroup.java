package com.bingle.android.colorful;

import android.graphics.Canvas;
import android.graphics.Paint;

public class ThreadGroup {
    private StraightThread[] straightThreads;
    public ThreadGroup(int width) {
        int numThreads = (int)(Math.random() * 4) + 8;
        int possibleRange = (int) (width * 0.8);
        int centerOfThreads = (int)(Math.random() * possibleRange + width * 0.1);
        straightThreads = new StraightThread[numThreads];

        for (int i = 0; i < straightThreads.length; i++) {
            int offset = (int)((Math.random() - 0.5) * width * 0.17);
            straightThreads[i] = new StraightThread(centerOfThreads + offset);
        }
    }

    public void advance() {
        for (StraightThread thread : straightThreads) {
            thread.advance();
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        for (StraightThread thread: straightThreads) {
            thread.drawBulbs(canvas, paint);
        }
    }

    public StraightThread[] getStraightThreads() {
        return straightThreads;
    }
}
