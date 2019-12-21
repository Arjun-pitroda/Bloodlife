package com.example.bloodylife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    ImageView ivSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ivSplash=findViewById(R.id.ivSplash);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Animation animation= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.fadin);
                    ivSplash.startAnimation(animation);
                    Thread.sleep(3000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                Intent i=new Intent(SplashActivity.this,SignupActivity.class);
                startActivity(i);
                finish();
            }
        }).start();


    }
}
