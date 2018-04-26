package com.yangzhenyu.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yangzhenyu on 2018/4/18.
 */

public class TaijiView extends View{
    private Paint whitePaint;
    private Paint blackPaint;
    private int bgWidth;
    private int bgHeight;
    private static final int OFFSET = 10;
    private int radius;
    private RectF rect;

    public TaijiView(Context context) {
        this(context,null);
    }

    public TaijiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TaijiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint(){
        whitePaint = new Paint();
        whitePaint.setAntiAlias(true);
        whitePaint.setColor(Color.WHITE);

        blackPaint = new Paint();
        blackPaint.setAntiAlias(true);
        blackPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(300,widthMeasureSpec);
        int height = getMySize(300,heightMeasureSpec);

        bgWidth = width;
        bgHeight = height;
        setMeasuredDimension(width,height);
    }

    private int getMySize(int defaultSize,int measureSpec){
        int mySize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode){
            case MeasureSpec.UNSPECIFIED:
                mySize = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                mySize = defaultSize;
                break;
            case MeasureSpec.EXACTLY:
                mySize = size;
                break;
            default:
                break;
        }
        return mySize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(bgWidth/2,bgHeight/2);
        if(radius == 0){
            radius = Math.min(bgWidth,bgHeight)/2-OFFSET;
            rect = new RectF(-radius,-radius,radius,radius);
        }
        canvas.drawArc(rect,90,180,true,blackPaint);
        canvas.drawArc(rect,-90,180,true,whitePaint);

        canvas.drawCircle(0,-radius/2,radius/2,blackPaint);
        canvas.drawCircle(0,radius/2,radius/2,whitePaint);

        canvas.drawCircle(0,-radius/2,radius/8,whitePaint);
        canvas.drawCircle(0,radius/2,radius/8,blackPaint);
    }
}
