package com.yangzhenyu.customview;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import tools.DensityUtil;

public class GuideView extends View {

    private Paint paint;
    private int width;
    private int height;
    private Path path;
    public GuideView(Context context) {
        this(context,null);
    }

    public GuideView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setWillNotDraw(false);
        paint = new Paint();
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
//        paint.setColor(Color.RED);
        paint.setTextSize(40);
//        paint.setStyle(Paint.Style.STROKE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //这个方法已经被标注为过时的方法了，如果你的应用启用了硬件加速，你是看不到任何阴影效果的
        paint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.INNER));
        //关闭当前view的硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        path = new Path();
        path.moveTo(50,50);
        path.lineTo(50,200);
        path.lineTo(200,100);
        path.lineTo(200,50);
        path.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#80000000"));
        canvas.drawPath(path,paint);
        canvas.drawCircle(width/2,height/2, DensityUtil.dip2px(50),paint);
        canvas.drawCircle(width/2,DensityUtil.dip2px(80), DensityUtil.dip2px(50),paint);
        canvas.drawCircle(width/2,DensityUtil.dip2px(120), DensityUtil.dip2px(50),paint);
        canvas.drawText("阴影遮罩",500,1000,paint);
    }
}
