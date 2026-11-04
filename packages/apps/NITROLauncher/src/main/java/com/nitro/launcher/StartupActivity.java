package com.nitro.launcher;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;

import java.io.InputStream;

/**
 * Short black startup screen for the NITRO launcher.
 * Add the animation at src/main/assets/nitro_boot.gif.
 * This is a launcher startup screen, not the manufacturer's pre-Android boot animation.
 */
public class StartupActivity extends Activity {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable openHome = new Runnable() {
        @Override public void run() {
            startActivity(new Intent(StartupActivity.this, HomeActivity.class));
            finish();
            overridePendingTransition(0, 0);
        }
    };

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().setNavigationBarColor(Color.BLACK);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        setContentView(new StartupView());
        handler.postDelayed(openHome, 2600L);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(openHome);
        super.onDestroy();
    }

    private final class StartupView extends View {
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        private final Movie movie;
        private final long startedAt = SystemClock.uptimeMillis();

        StartupView() {
            super(StartupActivity.this);
            Movie loaded = null;
            try (InputStream input = getAssets().open("nitro_boot.gif")) {
                loaded = Movie.decodeStream(input);
            } catch (Exception ignored) {
                // The GIF can be added later; show a built-in fallback.
            }
            movie = loaded;
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.BLACK);
            if (movie != null && movie.width() > 0 && movie.height() > 0) {
                int duration = Math.max(1, movie.duration());
                movie.setTime((int) ((SystemClock.uptimeMillis() - startedAt) % duration));
                float scale = Math.min((getWidth() * 0.72f) / movie.width(),
                        (getHeight() * 0.42f) / movie.height());
                float dw = movie.width() * scale;
                float dh = movie.height() * scale;
                canvas.save();
                canvas.translate((getWidth() - dw) / 2f, (getHeight() - dh) / 2f);
                canvas.scale(scale, scale);
                movie.draw(canvas, 0, 0);
                canvas.restore();
                postInvalidateDelayed(33L);
            } else {
                float density = getResources().getDisplayMetrics().scaledDensity;
                paint.setColor(Color.WHITE);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
                paint.setTextSize(34f * density);
                canvas.drawText("NITRO", getWidth() / 2f, getHeight() / 2f, paint);
                paint.setColor(0xFF9CA3AF);
                paint.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
                paint.setTextSize(11f * density);
                canvas.drawText("MUSIC OS", getWidth() / 2f,
                        getHeight() / 2f + 28f * density, paint);
            }
        }
    }
}
