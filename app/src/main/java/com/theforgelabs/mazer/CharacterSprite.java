package com.theforgelabs.mazer;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CharacterSprite {

    private Bitmap image;
    public int x, y;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public CharacterSprite(Bitmap bmp) {
        image = bmp;
        x = 225;
        y = screenHeight / 2;

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void update() {


    }

}
