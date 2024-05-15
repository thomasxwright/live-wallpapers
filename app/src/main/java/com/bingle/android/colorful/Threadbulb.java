package com.bingle.android.colorful;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Threadbulb {

    private int alpha, red, green, blue;
    private float pos;
    private float speed;

    public Threadbulb(int alpha, int red, int green, int blue, float pos) {
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.pos = pos;
        this.speed = (float)(Math.random()* 0.1);
    }
    public Threadbulb(int alpha, int red, int green, int blue) {
        this(alpha, red, green, blue, (float)Math.random());
    }
    public Threadbulb() {
        this(255, 232, 12, 130);
    }

    public void advanceBulb() {
        pos += (0.01 * speed);
        if (pos > 1.05) {
            pos = -0.05f;
        }
    }

    public void drawBulb(Canvas canvas, Paint paint, int xPos) {
        float yPos = 2280 * pos;
        paint.setColor(this.getColor());
        canvas.drawCircle(xPos, yPos, 20f, paint);
    }

    public int getColor() {
        return Color.argb(this.alpha, this.red, this.green, this.blue);
    }
    public float getPos() {
        return this.pos;
    }
}
