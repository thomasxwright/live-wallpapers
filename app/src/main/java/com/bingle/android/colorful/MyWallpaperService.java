package com.bingle.android.colorful;

import static android.graphics.Color.WHITE;

import android.content.SharedPreferences;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MyWallpaperService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new MyWallpaperEngine();
    }

    private class MyWallpaperEngine extends Engine {
        private final Handler handler = new Handler();
        private final Runnable drawRunner = new Runnable() {
            @Override
            public void run() {
                draw();
            }

        };
        private int width;
        private int height;
        private float tick;
        private final Paint fillPaint = new Paint();
        private boolean visible = true;
        private final boolean touchEnabled;

        public MyWallpaperEngine() {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(MyWallpaperService.this);
            touchEnabled = prefs.getBoolean("touch", false);
            tick = 0;
            fillPaint.setAntiAlias(true);
            fillPaint.setStyle(Paint.Style.FILL);
            fillPaint.setColor(Color.argb(230, 200, 80, 20));

            handler.post(drawRunner);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                handler.post(drawRunner);
            } else {
                handler.removeCallbacks(drawRunner);
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.visible = false;
            handler.removeCallbacks(drawRunner);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format,
                                     int width, int height) {
            this.width = width;
            this.height = height;
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
//            if (touchEnabled) {
//
//                float x = event.getX();
//                float y = event.getY();
//                SurfaceHolder holder = getSurfaceHolder();
//                Canvas canvas = null;
//                try {
//                    canvas = holder.lockCanvas();
//                    if (canvas != null) {
//                        canvas.drawColor(Color.BLACK);
//                        circles.clear();
//                        circles.add(new MyPoint(
//                                String.valueOf(circles.size() + 1), x, y));
//                        drawCircles(canvas, circles);
//
//                    }
//                } finally {
//                    if (canvas != null)
//                        holder.unlockCanvasAndPost(canvas);
//                }
//                super.onTouchEvent(event);
//            }
        }

        private void draw() {
            SurfaceHolder holder = getSurfaceHolder();
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    tick += 0.01f;
                    drawItems(canvas);
                }
            } finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
            handler.removeCallbacks(drawRunner);
            if (visible) {
                handler.postDelayed(drawRunner, 17);
            }
        }

        // Surface view requires that all elements are drawn completely
        private void drawItems(Canvas canvas) {
            canvas.drawColor(Color.rgb(17, 13, 23));
//            testOverlap(canvas, 400, 200, 4, 75f * (float) (Math.sin(tick)));
            testOverlap(canvas, 500, 1000, 5, 425f * (float) (Math.sin(tick)));
        }

        private void testOverlap(Canvas canvas, int cx, int cy, int numItems, float distance) {
            float[][] positions = new float[numItems][2];
            for (int i = 0; i < numItems; i++) {
                float multiplier = (float) (i) / numItems;
                float xPos = cx + (float) (distance * Math.cos(Math.PI * 2 * multiplier));
                float yPos = cy + (float) (distance * Math.sin(Math.PI * 2 * multiplier));
                positions[i] = new float[]{xPos, yPos};
            }

            for (float[] position : positions) {
                drawGlowOrb(canvas, position[0], position[1], 150);
            }
        }

        private void drawGlowOrb(Canvas canvas, float cx, float cy, int radius) {
            fillPaint.setBlendMode(BlendMode.PLUS);
            canvas.drawCircle(cx, cy, radius, fillPaint);
//            drawGlow(canvas, cx, cy, radius * 1.05f, .6f);
//            drawGlow(canvas, cx + radius * 0.2f, cy + radius * 0.2f, radius * 0.7f, .7f);
        }

        private void drawGlow(Canvas canvas, float cx, float cy, float radius, float opacity) {
            Shader shader = new RadialGradient(cx, cy, radius, new int[]{Color.argb((int) (178 * opacity), 255, 255, 255), Color.argb(0, 255, 255, 255)}, new float[]{0.15f, 1f}, Shader.TileMode.CLAMP);
            fillPaint.setShader(shader);
            fillPaint.setBlendMode(BlendMode.OVERLAY);
            canvas.drawRect(new RectF(0, 0, width, height), fillPaint);
            fillPaint.setShader(null);
        }

        private void drawGradient(Canvas canvas) {
//            Shader shader = new LinearGradient(300, 0, 400, height - 400, Color.argb(0, 232, 12, 130), Color.argb(255, 232, 12, 130), Shader.TileMode.CLAMP);
            Shader shader = new LinearGradient(300, 0, 400, 2280 - 400,
                    new int[]{Color.argb(0, 175, 25, 198), Color.argb(130, 200, 12, 189), Color.argb(255, 232, 12, 130), Color.argb(255, 232, 12, 130), Color.argb(120, 200, 12, 189), Color.argb(0, 0, 0, 0)},
                    new float[]{0, 0.5f, 0.75f, 0.8f, 0.81f, 0.82f},
                    Shader.TileMode.CLAMP);
            Paint paint = new Paint();
            paint.setShader(shader);
            canvas.drawRect(new RectF(0, 0, width, height), paint);
        }
    }
}