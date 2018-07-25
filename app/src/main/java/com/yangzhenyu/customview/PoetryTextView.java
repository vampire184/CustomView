package com.yangzhenyu.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import tools.DensityUtil;

/**
 *
 * @author yangzhenyu
 * @date 2018/4/26
 */

public class PoetryTextView extends View {
    private TextPaint textPaint;
    private float totalWidth;
    private float totalHeight;
    private float ROW_WIDTH = 5;
    private float LINE_WIDTH = 5;
    private String[] sText;
    private Rect rect;
    private boolean lastIsNumber = false;
    private boolean lastIsChinese = false;
    private float defaultWidth = 30;
    private float defaultHeight = 30;
    private Typeface realTypeface;
    private Rect charRect;

    public PoetryTextView(Context context) {
        this(context,null);
    }

    public PoetryTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PoetryTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context,attrs);
    }

    private void initPaint(Context context, AttributeSet attributeSet) {
        textPaint = new TextPaint();

        charRect = new Rect();
        if(attributeSet!=null){
            TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.PoetryTextView);

            int textColor = array.getColor(R.styleable.PoetryTextView_text_color, Color.BLACK);
            textPaint.setColor(textColor);
            float textSize = array.getDimensionPixelSize(R.styleable.PoetryTextView_text_size, DensityUtil.dip2px(getContext(),20));
            textPaint.setTextSize(textSize);

            ROW_WIDTH = array.getDimensionPixelSize(R.styleable.PoetryTextView_row_padding,DensityUtil.dip2px(getContext(),5));
            LINE_WIDTH = array.getDimensionPixelSize(R.styleable.PoetryTextView_line_padding,DensityUtil.dip2px(getContext(),5));
        }else{
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(DensityUtil.dip2px(getContext(),20));

            ROW_WIDTH = DensityUtil.dip2px(getContext(),5);
            LINE_WIDTH = DensityUtil.dip2px(getContext(),5);
        }
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStrokeWidth(DensityUtil.dip2px(getContext(),1));

        rect = new Rect();
        textPaint.getTextBounds("餐",0,1,rect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(defaultWidth,widthMeasureSpec);
        int height = getMySize(defaultHeight,heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    private int getMySize(float defaultSize,int measureSpec){
        float mySize = defaultSize;
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
        return (int)mySize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(sText!=null&&sText.length>0){
            canvas.translate(totalWidth,0);
            //稍微偏移一点，不然会有点歪
//            int numberLeft = (int)(-rect.width()+DensityUtil.dip2px(getContext(),3)/2.0);
            int numberLeft = (int)(-rect.width()+DensityUtil.dip2px(getContext(),3));
            int chineseLeft = (int)(-rect.width()/2.0);
            for(String s:sText){
                float mHeight = 0;
                lastIsNumber = false;
                lastIsChinese = false;
                for(int i=0;i<s.length();i++){
                    char cs = s.charAt(i);
                    int asc2 = (int)cs;
                    //非中文的字符全部倒转90
                    if(!(19968<=asc2&&asc2<=40869)){
                        textPaint.getTextBounds(s,i,i+1, charRect);
                        if(lastIsChinese){
                            if(i==0){
                                mHeight += charRect.width();
                            }else{
                                mHeight += (charRect.width()+DensityUtil.dip2px(getContext(),1.5f)+LINE_WIDTH);
                            }
                        }else{
                            if(i==0){
                                mHeight += charRect.width();
                            }else{
                                mHeight += (charRect.width()+DensityUtil.dip2px(getContext(),1.5f)+LINE_WIDTH);
                            }
                        }

                        textPaint.setTypeface(MyApplication.sApplySymBols);
                        canvas.rotate(90,numberLeft, mHeight);
                        canvas.drawText(String.valueOf(cs),numberLeft, mHeight,textPaint);
                        canvas.rotate(-90,numberLeft, mHeight);
                        lastIsNumber = true;
                        lastIsChinese = false;
                    }else{
                        if(lastIsNumber){
                            if(i==0){
                                mHeight += rect.height();
                            }else{
                                mHeight += (rect.height()+LINE_WIDTH);
                            }
                        }else {
                            if(i==0){
                                mHeight += rect.height();
                            }else{
                                mHeight += (rect.height()+LINE_WIDTH);
                            }
                        }

                        setTypeface(realTypeface);
                        canvas.drawText(String.valueOf(cs),chineseLeft, mHeight,textPaint);
                        lastIsNumber = false;
                        lastIsChinese = true;
                    }
                }
                float tempXPosition = (rect.width()+ROW_WIDTH);
                canvas.translate(-tempXPosition,0);
            }
        }
    }

    public void setText(String text){
        //设置文字，并且计算控件大小
        sText = text.split("\n");
        totalWidth = (rect.width()+ROW_WIDTH)*sText.length-ROW_WIDTH+
                DensityUtil.dip2px(getContext(),2);
        Rect mRect = new Rect();
        for(String s:sText){
            float tempHeight = 0;
            for(int i=0;i<s.length();i++){
                char cs = s.charAt(i);
                int asc2 = (int)cs;
                //非中文的字符全部倒转90
                if(!(19968<=asc2&&asc2<=40869)){
                    textPaint.getTextBounds(s,i,i+1,mRect);
                    tempHeight +=(mRect.width()+DensityUtil.dip2px(getContext(),1.5f));
                }else{
                    tempHeight +=rect.height();
                }
            }
            tempHeight += (s.length()-1)*LINE_WIDTH+
                    DensityUtil.dip2px(getContext(),5);
            if(tempHeight>totalHeight){
                totalHeight = tempHeight;
            }
        }
        defaultWidth = totalWidth;
        defaultHeight = totalHeight;
        invalidate();
    }

    public float getDefaultHeight(){
        return defaultHeight;
    }

    public float getDefaultWidth(){
        return defaultWidth;
    }


    public void setTypeface(Typeface typeface) {
        if (typeface == null) {
            typeface = Typeface.DEFAULT;
        }
        if (textPaint.getTypeface() != typeface) {
            textPaint.setTypeface(typeface);
        }
        realTypeface = typeface;
    }
}