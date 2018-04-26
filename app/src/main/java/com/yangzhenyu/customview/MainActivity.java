package com.yangzhenyu.customview;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private TaijiView taiji_view;
    private CircleProgress progress;
    private int mProgress=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taiji_view = findViewById(R.id.taiji_view);
        progress = findViewById(R.id.progress);
        ScheduledExecutorService executors = Executors.newSingleThreadScheduledExecutor();
        MyRunable myRunable = new MyRunable();
        executors.scheduleAtFixedRate(myRunable,0,200,TimeUnit.MILLISECONDS);


        ValueAnimator animator = ValueAnimator.ofInt(0,360);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                taiji_view.setRotation(value);
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }
    class MyRunable implements Runnable{
        @Override
        public void run() {
            mProgress+=1;
            if(mProgress>100){
                mProgress=0;
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progress.setProgress(mProgress);
                }
            });
        }
    }
}