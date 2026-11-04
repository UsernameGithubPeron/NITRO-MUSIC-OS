package com.nitro.launcher;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends Activity {
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(16, 19, 27));
        getWindow().setNavigationBarColor(Color.rgb(16, 19, 27));
        setContentView(new HomeSurface());
    }

    private void launch(String target) {
        Intent intent = null;
        if ("Camera".equals(target)) {
            intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        } else if ("Music".equals(target)) {
            intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_MUSIC);
        } else if ("Settings".equals(target)) {
            intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        } else if ("Files".equals(target)) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        } else if ("Photos".equals(target)) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("content://media/external/images/media"), "image/*");
        } else if ("Phone".equals(target)) {
            intent = new Intent(Intent.ACTION_DIAL);
        }
        if (intent != null) {
            try {
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, target + " isn't available yet", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private final class HomeSurface extends View {
        private final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final String[] names = {"Music", "Camera", "Photos", "Files", "Settings", "Phone"};
        private final int[] glyphs = {0x266B, 0x25C9, 0x25A7, 0x25A4, 0x2699, 0x260E};
        private final int[] colors = {
            Color.rgb(206, 153, 93), Color.rgb(108, 151, 190),
            Color.rgb(151, 132, 181), Color.rgb(119, 157, 150),
            Color.rgb(128, 139, 158), Color.rgb(105, 165, 137)
        };
        private final RectF[] hit = new RectF[6];
        private final RectF dock = new RectF();
        private long lastDraw = 0;

        HomeSurface() {
            super(HomeActivity.this);
            setLayerType(View.LAYER_TYPE_HARDWARE, null);
            for (int i = 0; i < hit.length; i++) hit[i] = new RectF();
            setFocusable(true);
        }

        @Override protected void onDraw(Canvas c) {
            super.onDraw(c);
            float w = getWidth(), h = getHeight();
            c.drawColor(Color.rgb(16, 19, 27));

            // Restrained atmospheric wallpaper: gradients instead of live blur.
            p.setShader(new android.graphics.RadialGradient(w * .78f, h * .22f,
                    w * .82f, new int[]{0x553E658A, 0x003E658A},
                    null, android.graphics.Shader.TileMode.CLAMP));
            c.drawRect(0, 0, w, h, p);
            p.setShader(new android.graphics.RadialGradient(w * .12f, h * .72f,
                    w * .70f, new int[]{0x443C536E, 0x003C536E},
                    null, android.graphics.Shader.TileMode.CLAMP));
            c.drawRect(0, 0, w, h, p);
            p.setShader(null);

            p.setColor(0xFFE8ECF4);
            p.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
            p.setTextSize(sp(16));
            p.setTextAlign(Paint.Align.LEFT);
            c.drawText(new SimpleDateFormat("EEEE, MMM d", Locale.getDefault()).format(new Date()),
                    w * .08f, h * .105f, p);

            p.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
            p.setTextSize(sp(62));
            c.drawText(new SimpleDateFormat("h:mm", Locale.getDefault()).format(new Date()),
                    w * .075f, h * .205f, p);

            p.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            p.setTextSize(sp(14));
            p.setColor(0xBFE8ECF4);
            c.drawText("NITRO", w * .08f, h * .265f, p);

            float left = w * .075f, gap = w * .065f;
            float cell = (w - 2 * left - gap) / 2f;
            float top = h * .36f, rowGap = h * .19f;
            float icon = Math.min(w * .17f, h * .095f);

            for (int i = 0; i < names.length; i++) {
                int row = i / 2, col = i % 2;
                float cx = left + col * (cell + gap) + cell / 2f;
                float cy = top + row * rowGap;
                RectF r = new RectF(cx - icon/2, cy - icon/2, cx + icon/2, cy + icon/2);
                hit[i].set(r.left - cell*.18f, r.top - h*.025f,
                           r.right + cell*.18f, r.bottom + h*.075f);
                drawIcon(c, r, colors[i], glyphs[i]);
                p.setColor(0xFFF3F5FA);
                p.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
                p.setTextSize(sp(13));
                p.setTextAlign(Paint.Align.CENTER);
                c.drawText(names[i], cx, r.bottom + h*.045f, p);
            }

            // Floating translucent dock: one cheap alpha surface, no real-time blur.
            float dockH = h * .105f, dockW = w * .86f;
            dock.set((w-dockW)/2f, h-dockH-h*.035f, (w+dockW)/2f, h-h*.035f);
            p.setColor(0x553B4658);
            c.drawRoundRect(dock, dockH*.38f, dockH*.38f, p);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(sp(1));
            p.setColor(0x44FFFFFF);
            c.drawRoundRect(dock, dockH*.38f, dockH*.38f, p);
            p.setStyle(Paint.Style.FILL);
            String[] dockNames = {"Phone", "Music", "Camera"};
            int[] dockGlyphs = {0x260E, 0x266B, 0x25C9};
            int[] dockColors = {colors[5], colors[0], colors[1]};
            for (int i=0;i<3;i++) {
                float cx = dock.left + dock.width()*(i+0.5f)/3f;
                float size = dockH*.64f;
                RectF r = new RectF(cx-size/2, dock.centerY()-size/2, cx+size/2, dock.centerY()+size/2);
                drawIcon(c, r, dockColors[i], dockGlyphs[i]);
            }
            if (System.currentTimeMillis() - lastDraw > 30000) {
                lastDraw = System.currentTimeMillis();
                postInvalidateDelayed(30000);
            }
        }

        private float sp(float v) { return v * getResources().getDisplayMetrics().scaledDensity; }

        private void drawIcon(Canvas c, RectF r, int color, int glyph) {
            p.setStyle(Paint.Style.FILL);
            p.setColor(color);
            c.drawRoundRect(r, r.width()*.28f, r.width()*.28f, p);
            p.setColor(0x22FFFFFF);
            c.drawRoundRect(r.left, r.top, r.right, r.top+r.height()*.42f,
                    r.width()*.28f, r.width()*.28f, p);
            p.setColor(Color.WHITE);
            p.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
            p.setTextAlign(Paint.Align.CENTER);
            p.setTextSize(r.width()*.48f);
            c.drawText(new String(Character.toChars(glyph)), r.centerX(),
                    r.centerY()+r.height()*.17f, p);
        }

        @Override public boolean onTouchEvent(MotionEvent e) {
            if (e.getAction() == MotionEvent.ACTION_UP) {
                float x=e.getX(), y=e.getY();
                if (dock.contains(x,y)) {
                    int i = Math.min(2, Math.max(0, (int)((x-dock.left)/dock.width()*3)));
                    launch(new String[]{"Phone","Music","Camera"}[i]);
                    return true;
                }
                for (int i=0;i<hit.length;i++) {
                    if (hit[i].contains(x,y)) { launch(names[i]); return true; }
                }
                performClick();
                return true;
            }
            return true;
        }

        @Override public boolean performClick() {
            super.performClick();
            return true;
        }
    }
}
