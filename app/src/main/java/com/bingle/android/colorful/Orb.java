package com.bingle.android.colorful;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Orb {

    private int alpha, red, green, blue;
    private float rotation;

    public Orb(int alpha, int red, int green, int blue, float rotation) {
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.rotation = rotation;
    }
    public Orb(int alpha, int red, int green, int blue) {
        this(alpha, red, green, blue, (float)(Math.random() * 360));
    }

    public Orb(int red, int green, int blue) {
        this(255, red, green, blue);
    }
    public Orb() {
        this(255, 232, 12, 130);
    }

    public int getColor() {
        return Color.argb(this.alpha, this.red, this.green, this.blue);
    }
    public float getRotation() { return this.rotation;}
}
