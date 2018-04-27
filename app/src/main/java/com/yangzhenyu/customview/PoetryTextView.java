package com.yangzhenyu.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import tools.DensityUtil;

/**
 * Created by yangzhenyu on 2018/4/26.
 */

public class PoetryTextView extends View {
    private TextPaint textPaint;
    private float totalWidth;
    private float totalHeight;
    private static final float OFFSET_WIDTH = 3;
    private static final float OFFSET_HEIGHT = 3;
    private String[] sText;
    private Rect rect;
    private boolean lastIsNumber = false;
    private boolean lastIsChinese = false;

    public PoetryTextView(Context context) {
        this(context,null);
    }

    public PoetryTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PoetryTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        textPaint = new TextPaint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(DensityUtil.dip2px(getContext(),20));
        textPaint.setAntiAlias(true);
        textPaint.setStrokeWidth(3);

        rect = new Rect();
        textPaint.getTextBounds("国",0,1,rect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(30,widthMeasureSpec);
        int height = getMySize(30,heightMeasureSpec);
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
        if(sText.length>0){
            canvas.translate(DensityUtil.dip2px(getContext(),totalWidth),0);
            for(String s:sText){
                float mHeight = 0;
                lastIsNumber = false;
                lastIsChinese = false;
                for(int i=0;i<s.length();i++){
                    char cs = s.charAt(i);
                    int asc2 = (int)cs;
                    Log.d("circle","cs is:"+cs+"   asc2 is :"+asc2);
                    //非中文的字符全部倒转90
                    if(!(19968<=asc2&&asc2<=40869)){
                        if(lastIsChinese){
                            if(i==0){
                                mHeight +=DensityUtil.dip2px(getContext(),OFFSET_HEIGHT)-rect.height()/8.0;
                            }else{
                                mHeight +=(-rect.height()/8.0+DensityUtil.dip2px(getContext(),OFFSET_HEIGHT)*2);
                            }
                        }else{
                            if(i==0){
                                mHeight +=DensityUtil.dip2px(getContext(),OFFSET_HEIGHT);
                            }else{
                                mHeight +=rect.height()/2.0+DensityUtil.dip2px(getContext(),OFFSET_HEIGHT)*2;
                            }
                        }
                        canvas.rotate(90,-rect.width()+DensityUtil.dip2px(getContext(),OFFSET_WIDTH), mHeight);
                        canvas.drawText(String.valueOf(cs),-rect.width()+DensityUtil.dip2px(getContext(),OFFSET_WIDTH), mHeight,textPaint);
                        canvas.rotate(-90,-rect.width()+DensityUtil.dip2px(getContext(),OFFSET_WIDTH), mHeight);
                        lastIsNumber = true;
                        lastIsChinese = false;
                    }else{
                        if(lastIsNumber){
                            if(i==0){
                                mHeight +=rect.height()*1.5;
                            }else{
                                mHeight +=rect.height()*1.5+DensityUtil.dip2px(getContext(),OFFSET_HEIGHT);
                            }
                        }else{
                            if(i==0){
                                mHeight +=rect.height();
                            }else{
                                mHeight +=rect.height()+DensityUtil.dip2px(getContext(),OFFSET_HEIGHT);
                            }
                        }
                        canvas.drawText(String.valueOf(cs),-rect.width(), mHeight,textPaint);
                        lastIsNumber = false;
                        lastIsChinese = true;
                    }
                }
                float tempXPosition = (rect.width()+DensityUtil.dip2px(getContext(),OFFSET_WIDTH));
                canvas.translate(-tempXPosition,0);
            }
        }
    }

    public void setText(String text){

        sText = text.split("\n");
        totalWidth = (rect.width()+OFFSET_WIDTH)*sText.length;
        for(String s:sText){
            Rect mRect = new Rect();
            textPaint.getTextBounds(s,0,s.length(),mRect);
            float tempWidth = mRect.width()+(s.length()-1)*OFFSET_HEIGHT;
            if(tempWidth>totalHeight){
                totalHeight = tempWidth;
            }
        }
        setMeasuredDimension((int)totalWidth,(int)totalHeight);
        invalidate();
    }
}
