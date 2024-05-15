package com.bingle.android.colorful;

import android.content.SharedPreferences;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
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
        private final Paint strokePaint = new Paint();
        private final Paint fillPaint = new Paint();
        private int width;
        private int height;
        private int ticker;
        private ThreadGroup threadGroup;
        private boolean visible = true;
        private final boolean touchEnabled;

        public MyWallpaperEngine() {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(MyWallpaperService.this);
            touchEnabled = prefs.getBoolean("touch", false);
            ticker = 0;

            fillPaint.setAntiAlias(true);
            fillPaint.setStyle(Paint.Style.FILL);
            fillPaint.setBlendMode(BlendMode.PLUS);

            strokePaint.setAntiAlias(true);
            strokePaint.setColor(Color.argb(130, 255, 255, 255));
            strokePaint.setStyle(Paint.Style.STROKE);
            strokePaint.setStrokeJoin(Paint.Join.ROUND);
            strokePaint.setStrokeWidth(1f);
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
            threadGroup = new ThreadGroup(width);
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
                    advanceItems();
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

        private void advanceItems() {
            threadGroup.advance();
        }

        // Surface view requires that all elements are drawn completely
        private void drawItems(Canvas canvas) {
            canvas.drawColor(Color.BLACK);
            threadGroup.draw(canvas, fillPaint);
            drawGradient(canvas);
//                canvas.drawLine(threadXPos, 0, threadXPos + 23, height, paint);

//            canvas.drawCircle(ticker, ticker, ticker, paint);
        }

        private void drawGradient(Canvas canvas) {
//            Shader shader = new LinearGradient(300, 0, 400, height - 400, Color.argb(0, 232, 12, 130), Color.argb(255, 232, 12, 130), Shader.TileMode.CLAMP);
            Shader shader = new LinearGradient(300, 0, 400, height - 400,
                    new int[]{Color.argb(0, 175, 25, 198 ), Color.argb(130, 200, 12, 189), Color.argb(255, 232, 12, 130), Color.argb(255, 232, 12, 130), Color.argb(120, 200, 12, 189), Color.argb(0, 0, 0, 0)},
                    new float[]{0, 0.5f, 0.75f, 0.8f, 0.81f, 0.82f},
                    Shader.TileMode.CLAMP);
            Paint paint = new Paint();
            paint.setShader(shader);
            canvas.drawRect(new RectF(0, 0, width, height), paint);
        }
    }
}