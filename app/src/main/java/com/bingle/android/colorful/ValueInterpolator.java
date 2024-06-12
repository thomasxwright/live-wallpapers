package com.bingle.android.colorful;

public class ValueInterpolator {
    public static float identifyLocalPosition(float lowerBound, float upperBound, float middlePoint) {
        if (upperBound == lowerBound) return 1f;
        return (middlePoint - lowerBound) / (upperBound - lowerBound);
    }

    public static Color interpolateColors(Color lowerBoundColor, Color upperBoundColor, float percentageBetweenValues) {
        int red = (int) identifyAbsolutePosition(lowerBoundColor.getRed(), upperBoundColor.getRed(), percentageBetweenValues);
        int green = (int) identifyAbsolutePosition(lowerBoundColor.getGreen(), upperBoundColor.getGreen(), percentageBetweenValues);
        int blue = (int) identifyAbsolutePosition(lowerBoundColor.getBlue(), upperBoundColor.getBlue(), percentageBetweenValues);
        return new Color(red, green, blue);
    }

    public static float identifyAbsolutePosition(float lowerBound, float upperBound, float middlePoint) {
        float diff = upperBound - lowerBound;
        return lowerBound + middlePoint * diff;
    }
}