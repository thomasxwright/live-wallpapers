package com.bingle.android.colorful;

public class Color {
    private final int myRed, myGreen, myBlue;
    public Color(int red, int green, int blue) {
        myRed = red;
        myGreen = green;
        myBlue = blue;
    }
    public int getColor() {
        return android.graphics.Color.rgb(myRed, myGreen, myBlue);
    }
}
