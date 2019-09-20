package com.theforgelabs.mazer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.theforgelabs.mazer.MainThread.canvas;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    
    private MainThread thread;
    private CharacterSprite characterSprite;
    private WallSprite wall1, wall2, wall3;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public static int gapHeight = 1500;
    public static float velocity;
    public int score = 0;




    public GameView(Context context) {
        super(context);
        
        getHolder().addCallback(this);
        
        thread = new MainThread(getHolder(),this);
        setFocusable(true);
        
    }
    
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        velocity = 18;

        characterSprite = new CharacterSprite(BitmapFactory.decodeResource(getResources(), R.drawable.character));

        Bitmap bmp = (BitmapFactory.decodeResource(getResources(), R.drawable.wall));
        Bitmap bmp2 = (BitmapFactory.decodeResource(getResources(), R.drawable.wall));
        wall1 = new WallSprite(bmp, bmp2, 2000, -150);
        wall2 = new WallSprite(bmp, bmp2, 3000, -850);
        wall3 = new WallSprite(bmp, bmp2, 4000, 450);



        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }

    }

    public void update() {


        characterSprite.update();

        wall1.update();
        wall2.update();
        wall3.update();

        logic();


    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {

            canvas.drawRGB(255, 255, 255);

            characterSprite.draw(canvas);

            wall1.draw(canvas);
            wall2.draw(canvas);
            wall3.draw(canvas);



            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(75);

            canvas.drawText(String.valueOf(score), screenWidth/2, 200, paint);


            //Levels
            if (score > 20) {
                velocity = 22;
                //Green
                canvas.drawRGB(59,226,0);


                characterSprite.draw(canvas);

                wall1.draw(canvas);
                wall2.draw(canvas);
                wall3.draw(canvas);

                canvas.drawText(String.valueOf(score), screenWidth/2, 200, paint);


            }

            if (score > 60) {
                velocity = 25;
                //Blue
                canvas.drawRGB(0,162,255);

                characterSprite.draw(canvas);

                wall1.draw(canvas);
                wall2.draw(canvas);
                wall3.draw(canvas);

                canvas.drawText(String.valueOf(score), screenWidth/2, 200, paint);


            }

            if (score > 150) {
                velocity = 30;
                //Yellow
                canvas.drawRGB(254,224,0);

                characterSprite.draw(canvas);

                wall1.draw(canvas);
                wall2.draw(canvas);
                wall3.draw(canvas);

                canvas.drawText(String.valueOf(score), screenWidth/2, 200, paint);

            }

            if (score > 210) {
                velocity = 35;
                //Orange
                canvas.drawRGB(248,95,0);

                characterSprite.draw(canvas);

                wall1.draw(canvas);
                wall2.draw(canvas);
                wall3.draw(canvas);

                canvas.drawText(String.valueOf(score), screenWidth/2, 200, paint);

            }

            if (score > 270) {
                velocity = 40;
                //Pink
                canvas.drawRGB(248,0,138);

                characterSprite.draw(canvas);

                wall1.draw(canvas);
                wall2.draw(canvas);
                wall3.draw(canvas);

                canvas.drawText(String.valueOf(score), screenWidth/2, 200, paint);

            }

            if (score > 330) {
                velocity = 45;
                //Red
                canvas.drawRGB(232,30,0);

                characterSprite.draw(canvas);

                wall1.draw(canvas);
                wall2.draw(canvas);
                wall3.draw(canvas);

                canvas.drawText(String.valueOf(score), screenWidth/2, 200, paint);

            }

            if (score > 390) {
                velocity = 50;
                canvas.drawRGB(37,37,37);

                characterSprite.draw(canvas);

                wall1.draw(canvas);
                wall2.draw(canvas);
                wall3.draw(canvas);

                canvas.drawText(String.valueOf(score), screenWidth/2, 200, paint);

            }


        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();
        float indexY = event.getY();
        int pointerY = Math.round(indexY);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                characterSprite.y = pointerY;
                break;

            case MotionEvent.ACTION_MOVE:
                characterSprite.y = pointerY;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;

        }

        return true;

    }

    public void logic() {
        List<WallSprite> walls = new ArrayList<>();
        walls.add(wall1);
        walls.add(wall2);
        walls.add(wall3);

        for (int i = 0; i < walls.size(); i++) {

            //Wall Collision Detection
            if (characterSprite.y - 100 < walls.get(i).yY + (screenHeight / 2) - (gapHeight / 2) + 650 && characterSprite.x + 10 > walls.get(i).xX && characterSprite.x < walls.get(i).xX + 10) {

                dead();

            } else if (characterSprite.y + 50 > (screenHeight / 2) + (gapHeight / 2) + walls.get(i).yY && characterSprite.x + 10 > walls.get(i).xX && characterSprite.x < walls.get(i).xX + 10) {

                dead();

            }

            //Generate Walls
            if (walls.get(i).xX + 500 < 0) {

                score++;

                Random random = new Random();
                int value1 = random.nextInt(500 - 250) + 250;
                int value2 = random.nextInt(1750);
                walls.get(i).xX = screenWidth + value1 + 500;
                walls.get(i).yY = value2 - 1000;
            }

        }

    }

    public void dead() {

        Context mContext = getContext();
        Intent intent = new Intent(mContext, Dead.class);
        intent.putExtra("score", score);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

    /*
    public void reset() {
        characterSprite.x = 225;
        characterSprite.y = screenHeight / 2;
        wall1.xX = 2000;
        wall1.yY = -150;
        wall2.xX = 3000;
        wall2.yY = -850;
        wall3.xX = 4000;
        wall3.yY = 450;
        score = 0;
        velocity = 18;
    }
    */
    
}
