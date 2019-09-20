package com.theforgelabs.mazer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

public class Dead extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceSaved) {
        super.onCreate(savedInstanceSaved);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dead);


        GameView.velocity = 0;


        TextView scoreView = findViewById(R.id.scoreView);
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        scoreView.setText(String.valueOf(score));


        Button resetButton = findViewById(R.id.resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetGame();

            }
        });


        Button menuButton = findViewById(R.id.menuButton);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainMenu();

            }
        });

    }

    public void resetGame() {
        Intent intent = new Intent(this, GameViewStart.class);
        startActivity(intent);
        finish();
    }

    public void mainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }


}
