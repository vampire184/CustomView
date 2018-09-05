package com.yangzhenyu.customview;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tools.DensityUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TaijiView taiji_view;
    private CircleProgress progress;
    private PoetryTextView poetry_text;
    private LinearLayout mLeftLayout;
    private ImageView mIcon;
    private int mProgress=0;
    private Button mShowSecondView;
    private Button mShowGuideView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShowSecondView = (Button) findViewById(R.id.show_second_view);
        mShowSecondView.setOnClickListener(this);
        mShowGuideView = (Button) findViewById(R.id.show_guide_view);
        mShowGuideView.setOnClickListener(this);
        taiji_view = findViewById(R.id.taiji_view);
        mLeftLayout = (LinearLayout) findViewById(R.id.left_layout);
        mIcon = (ImageView) findViewById(R.id.icon);
        progress = findViewById(R.id.progress);
        poetry_text = findViewById(R.id.poetry_text);
        poetry_text.setTypeface(MyApplication.sSongTi18030);
        String text = "2018年4月24日下午，\n" +
                "正在湖北宜昌调研~~考察的\n" +
                "《习近平》总书记来到♥ 紧邻「三峡」\n" +
                "大坝的太平@￥%……&*（溪镇许家）冲村。";

//        String text = "正在湖北宜昌调研考察";
        poetry_text.setText(text);
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
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.icon_one);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrant = palette.getVibrantSwatch();//有活力的
                Palette.Swatch vibrantLight = palette.getLightVibrantSwatch();//有活力的，亮色
                addCard(vibrant,vibrantLight);
                Palette.Swatch vibrantDark = palette.getDarkVibrantSwatch();//有活力的，暗色
                Palette.Swatch muted = palette.getMutedSwatch();//柔和的
                addCard(vibrantDark,muted);
                Palette.Swatch mutedLight = palette.getLightMutedSwatch();//柔和的,亮色
                Palette.Swatch mutedDark = palette.getDarkMutedSwatch();//柔和的，暗色
                addCard(mutedDark,mutedLight);
            }
        });
    }

    private void addCard(final Palette.Swatch from,final Palette.Swatch to){
        mLeftLayout.post(new Runnable() {
            @Override
            public void run() {
                if(from!=null&&to!=null){
                    TextView view = new TextView(MainActivity.this);
                    RelativeLayout.LayoutParams viewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            DensityUtil.dip2px(50));
                    viewParams.topMargin = DensityUtil.dip2px(10);
                    viewParams.leftMargin = DensityUtil.dip2px(5);
                    view.setTextColor(from.getBodyTextColor());
                    view.setGravity(Gravity.CENTER);

                    GradientDrawable shape = new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{from.getRgb(),to.getRgb()});
                    shape.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                    shape.setCornerRadius(10);
                    view.setBackground(shape);
                    view.setTextSize(13);
                    view.setText("自动添加卡片");
                    view.setLayoutParams(viewParams);
                    mLeftLayout.addView(view);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_second_view:
                Intent intent = new Intent(this,LineActivity.class);
                startActivity(intent);
                break;
            case R.id.show_guide_view:
                Intent guideIntent = new Intent(this,GuideActivity.class);
                startActivity(guideIntent);
                break;
            default:
                break;
        }
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
