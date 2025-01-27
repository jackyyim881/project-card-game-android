package com.example.cardgame2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Random;

// NoiseTextureDrawable.java
public class NoiseTextureDrawable extends Drawable {
    private final Paint paint = new Paint();
    private final Random random = new Random();
    private final int density = 4; // Noise density

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        int width = bounds.width();
        int height = bounds.height();

        // Create noise pattern
        for (int x = 0; x < width; x += density) {
            for (int y = 0; y < height; y += density) {
                int alpha = random.nextInt(15) + 10; // 10-25 alpha
                paint.setColor(Color.argb(alpha, 0, 0, 0));
                canvas.drawRect(x, y, x + density, y + density, paint);
            }
        }
    }

    @Override
    public void setAlpha(int alpha) { /* Required override */ }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) { /* Required override */ }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}