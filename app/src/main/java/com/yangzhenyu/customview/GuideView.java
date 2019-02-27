package com.yangzhenyu.customview;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
        paint.setStrokeWidth(30);
        paint.setAntiAlias(true);
//        paint.setColor(Color.RED);
        paint.setTextSize(40);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
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
        path.quadTo(50,50,(300+50)/2,(600+50)/2);
        path.quadTo(300,600,(200+300)/2,(900+600)/2);
        path.quadTo(200,900,(500+200)/2,(1500+900)/2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#80000000"));
        canvas.drawPath(path,paint);
        canvas.drawCircle(width/2,DensityUtil.dip2px(100), DensityUtil.dip2px(50),paint);
    }
}
