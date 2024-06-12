package com.bingle.android.colorful;

public class Gradient {
    private Color[] myColors;
    private float[] myPositions;

    public Gradient(Color[] colors, float[] positions) {
        myColors = colors;
        myPositions = positions;
    }

    public Color[] getColors() {
        return myColors;
    }

    public int[] getColorsAsInts() {
        int[] colorInts = new int[myColors.length];
        for (int i = 0; i < myColors.length; i++) {
            colorInts[i] = myColors[i].getColor();
        }
            return colorInts;
    }

    public float[] getPositions() {
        return myPositions;
    }

}
