package com.bingle.android.colorful;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.SurfaceHolder;

import java.util.Objects;

public class GradientBarWallpaper extends AnimationWallpaper {


    @Override
    public Engine onCreateEngine() {
        return new GradientBarEngine();
    }

    class GradientBarEngine extends AnimationEngine {
        private int numberOfCircles;
        private float radiusOfCircles;
        private float travelRadius;
        private int alpha;
        private int red;
        private int green;
        private int blue;
        private String sinOrCos;
        private Boolean glow;
        private float horizontalOffset;
        private float verticalOffset;
        private int width;
        private int height;
        private float speedMultiplier;
        private float rotationMultiplier;
        private float glowOpacityMultiplier;
        private float tick;
        private final Paint fillPaint = new Paint();
        private boolean visible = true;
        private float valueForAnimation;
        private ValueAnimator valueAnimator;
        BitmapShader bitmapShader;
        Matrix matrix;
        Bitmap bitmap;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            valueAnimator = ValueAnimator.ofFloat(0, 800);
            valueAnimator.setDuration(20000);
            valueAnimator.start();
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    valueAnimator.start();
                }
            });

            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(GradientBarWallpaper.this);

            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gray_noise);
            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            matrix = new Matrix();

            numberOfCircles = Integer
                    .valueOf(prefs.getString("numberOfCircles", "9"));
            radiusOfCircles = Float.valueOf(prefs.getString("radiusOfCircles", "1f"));
            travelRadius = Float.valueOf(prefs.getString("travelRadius", "1f"));
            sinOrCos = prefs.getString("sinOrCos", "neither");
            glow = prefs.getBoolean("glow", false);
            speedMultiplier = Float.valueOf(prefs.getString("speedMultiplier", "1f"));
            horizontalOffset = Float.valueOf(prefs.getString("horizontalOffset", "1f"));
            verticalOffset = Float.valueOf(prefs.getString("verticalOffset", "1f"));
            alpha = Integer.valueOf(prefs.getString("alpha", "255"));
            red = Integer.valueOf(prefs.getString("red", "217"));
            blue = Integer.valueOf(prefs.getString("blue", "72"));
            green = Integer.valueOf(prefs.getString("green", "48"));
            rotationMultiplier = Float.valueOf(prefs.getString("rotationMultiplier", "0f"));
            glowOpacityMultiplier = Float.valueOf(prefs.getString("glowOpacityMultiplier", "1f"));
            tick = 0;
            fillPaint.setAntiAlias(true);
            fillPaint.setStyle(Paint.Style.FILL);
            fillPaint.setColor(Color.argb(alpha, red, green, blue));
//            Log.d("tag1", "onCreate()");
            // By default we don't get touch events, so enable them.
//            setTouchEventsEnabled(true);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            this.height = height;
            this.width = width;
//            Log.d("tag1", "onSurfaceChanged()");

            super.onSurfaceChanged(holder, format, width, height);
        }


        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
//            Log.d("tag1", "onSurfaceCreated()");
        }


        @Override
        public Bundle onCommand(String action, int x, int y, int z, Bundle extras, boolean resultRequested) {
            return super.onCommand(action, x, y, z, extras, resultRequested);
        }

        @Override
        protected void drawFrame() {
            SurfaceHolder holder = getSurfaceHolder();

            Canvas c = null;
            try {
                c = holder.lockCanvas();
                if (c != null) {
                    draw(c);
                }
            } finally {
                if (c != null)
                    holder.unlockCanvasAndPost(c);
            }
        }

        void draw(Canvas c) {
            c.drawColor(Color.rgb(17, 13, 23));
            drawGradient(c);
            testOverlap(c, (float) width / 2 * horizontalOffset, (float) (height * 1000) / 2200 * verticalOffset, numberOfCircles, 555f * travelRadius * (float) (Math.sin(tick)));
            fillPaint.setBlendMode(BlendMode.SRC_OVER);
        }

        @Override
        protected void iteration() {
            valueForAnimation = (float) valueAnimator.getAnimatedValue();
            tick += 0.003f * speedMultiplier;
            super.iteration();
        }

        private void drawGradient(Canvas canvas) {
            Gradient[] gradients = {SampleGradients.OceanGreen, SampleGradients.desolateGraveyard, SampleGradients.burntOrange, SampleGradients.lakeMurk, SampleGradients.pearlRaspberry, SampleGradients.cynthia, SampleGradients.neonWarm, SampleGradients.spicyLavender};
            Shader shader;
            for (int i = 0; i < gradients.length; i++) {
                shader = new LinearGradient(50, 0, width - 50, 0, gradients[i].getColors(), gradients[i].getPositions(), Shader.TileMode.CLAMP);
                fillPaint.setShader(shader);
                fillPaint.setBlendMode(BlendMode.SRC_OVER);
                canvas.drawRect(new RectF(50, 200 + 300 * i + valueForAnimation, width - 50, 400 + 300 * i + valueForAnimation), fillPaint);
                fillPaint.setBlendMode(BlendMode.OVERLAY);
                fillPaint.setShader(bitmapShader);
                matrix.reset();
                matrix.setScale(1,1);
                matrix.preTranslate(50, 200 + 300 * i + valueForAnimation);
                bitmapShader.setLocalMatrix(matrix);
                canvas.drawRect(new RectF(50, 200 + 300 * i + valueForAnimation, width - 50, 400 + 300 * i + valueForAnimation), fillPaint);

            }
            fillPaint.setShader(null);
        }

        private void testOverlap(Canvas canvas, float cx, float cy, int numItems, float distance) {
            float[][] positions = new float[numItems][2];
            for (int i = 0; i < numItems; i++) {
                float multiplier = (float) (i) / numItems;
                float offset = multiplier;
                if (Objects.equals(sinOrCos, "sin")) {
                    offset = (float) Math.sin(multiplier + tick * speedMultiplier);
                }
                if (Objects.equals(sinOrCos, "cos")) {
                    offset = (float) Math.cos(multiplier + tick * speedMultiplier);
                }
                float rotationOffset = (float) (Math.sin(tick * rotationMultiplier));
                offset += (float) (Math.cos(tick * rotationMultiplier));
                float xPos = cx + (float) (distance * Math.cos(Math.PI * 2 * offset));
                float yPos = cy + (float) (distance * Math.sin(Math.PI * 2 * offset));
                positions[i] = new float[]{xPos, yPos};
            }

            for (float[] position : positions) {
                drawGlowOrb(canvas, position[0], position[1], (int) (radiusOfCircles * 150));
            }
        }

        private void drawGlowOrb(Canvas canvas, float cx, float cy, int radius) {
            fillPaint.setBlendMode(BlendMode.PLUS);
            canvas.drawCircle(cx, cy, radius, fillPaint);
            if (glow) {
                drawGlow(canvas, cx, cy, radius * 1.05f, .8f * glowOpacityMultiplier);
            }
//            drawGlow(canvas, cx + radius * 0.2f, cy + radius * 0.2f, radius * 0.7f, .7f);
        }

        private void drawGlow(Canvas canvas, float cx, float cy, float radius, float opacity) {
            Shader shader = new RadialGradient(cx, cy, radius, new int[]{Color.argb((int) ((178 * opacity) > 255 ? 255 : (178 * opacity)), 255, 255, 255), Color.argb(0, 255, 255, 255)}, new float[]{0.15f, 1f}, Shader.TileMode.CLAMP);
            fillPaint.setShader(shader);
            fillPaint.setBlendMode(BlendMode.OVERLAY);
            canvas.drawRect(new RectF(cx - radius, cy - radius, cx + radius, cy + radius), fillPaint);
            fillPaint.setShader(null);
        }

    }

}
