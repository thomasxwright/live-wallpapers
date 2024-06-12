package com.bingle.android.colorful;

public class GradientInterpolator {

    private Gradient gradient;
    private float[] samplePositions;
    private Color[] interpolatedColors;

    public GradientInterpolator(Gradient gradient, int numPoints) {
        this.gradient = gradient;
        this.interpolatedColors = new Color[numPoints];
        float[] positions = gradient.getPositions();
        Color[] colors = gradient.getColors();

        for (int i = 0; i < numPoints; i++){
            float point = (float) i / numPoints;

            int index = 0;
            float lowerBound = 0, upperBound = 1f;
            Color lowerBoundColor = colors[0], upperBoundColor = colors[colors.length - 1];
            //identify its nearest gradient boundaries
            for (int j = 0; j < positions.length && positions[j] <= point; j++) {
                lowerBound = positions[j];
                lowerBoundColor = colors[j];
                index = j;
            }
            if (point > lowerBound && positions.length > index + 1) {
                upperBound = positions[index + 1];
                upperBoundColor = colors[index + 1];
            }
            else if (point == lowerBound) {
                upperBound = lowerBound;
                upperBoundColor = lowerBoundColor;
            }

            float percentageBetweenValues = ValueInterpolator.identifyLocalPosition(lowerBound, upperBound, point);

            this.interpolatedColors[i] = ValueInterpolator.interpolateColors(lowerBoundColor, upperBoundColor, percentageBetweenValues);
        }
    }

    public Color[] getInterpolatedColors() {
        return this.interpolatedColors;
    }

    //for each point in the list, identify its nearest gradient boundaries.
    //calculate how far from one boundary it is to the next boundary.
    //interpolate the values accordingly.
}
