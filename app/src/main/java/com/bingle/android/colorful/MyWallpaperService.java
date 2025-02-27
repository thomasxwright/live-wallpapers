package com.bingle.android.colorful;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
        private final Paint paint = new Paint();
        private int width;
        private int height;
        private int ticker;
        private boolean visible = true;
        private final boolean touchEnabled;

        public MyWallpaperEngine() {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(MyWallpaperService.this);
            touchEnabled = prefs.getBoolean("touch", false);
            ticker = 0;
            paint.setAntiAlias(true);
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(10f);
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
                    advanceItems();
                    drawItems(canvas);
                }
            } finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
            handler.removeCallbacks(drawRunner);
            if (visible) {
                handler.postDelayed(drawRunner, 24);
            }
        }

        private void advanceItems() {
            ticker++;
        }

        // Surface view requires that all elements are drawn completely
        private void drawItems(Canvas canvas) {
            canvas.drawColor(Color.BLACK);
//            canvas.drawCircle(ticker, ticker, ticker, paint);
        }
    }
}