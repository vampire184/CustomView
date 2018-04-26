package com.yangzhenyu.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yangzhenyu on 2018/4/26.
 */

public class CircleProgress extends View {
    private static final String TAG = "circle";
    private Paint grayPaint;
    private Paint greenPaint;
    private Paint textPaint;
    private int bgWidth;
    private int bgHeight;
    private int offset = 6;
    private String mText="";
    private Rect rect;
    private RectF rectF;
    private int progress;

    public CircleProgress(Context context) {
        this(context,null);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        grayPaint = new Paint();
        grayPaint.setAntiAlias(true);
        grayPaint.setStrokeWidth(offset);
        grayPaint.setColor(Color.parseColor("#ffffff"));
        grayPaint.setStyle(Paint.Style.STROKE);


        greenPaint = new Paint();
        greenPaint.setAntiAlias(true);
        greenPaint.setStrokeWidth(offset);
        greenPaint.setStyle(Paint.Style.STROKE);
        greenPaint.setStrokeCap(Paint.Cap.ROUND);
        greenPaint.setColor(Color.parseColor("#00da4e"));

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(30);
        textPaint.setColor(Color.parseColor("#00da4e"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(100,widthMeasureSpec);
        int height = getMySize(100,heightMeasureSpec);
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
        float halfWidth = (float)(bgWidth*1.0/2);
        float halfHeight = (float)(bgHeight*1.0/2);
        if(rectF==null){
            rectF = new RectF((-halfWidth+offset),(-halfHeight+offset),(halfWidth-offset),(halfHeight-offset));
        }
        canvas.translate(halfWidth,halfHeight);
        canvas.drawCircle(0,0,halfWidth-offset,grayPaint);
        if(rect!=null&&rectF!=null){
            float xPosition = (float)(rect.width()/2.0);
            float yPosition = (float)(rect.height()/2.0);
            canvas.drawText(mText,-xPosition,yPosition,textPaint);
            float sweepAngle = (float) (progress*1.0/100*360);
            canvas.drawArc(rectF,-90,sweepAngle,false,greenPaint);
        }
    }

    public void setProgress(int progress){
        this.progress = progress;
        mText = progress+"%";
        rect = new Rect();
        textPaint.getTextBounds(mText,0,mText.length(),rect);
        invalidate();
    }
}
