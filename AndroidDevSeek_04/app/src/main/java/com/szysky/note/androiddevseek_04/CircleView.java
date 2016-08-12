package com.szysky.note.androiddevseek_04;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by suzeyu on 16/8/11.\\
 *
 * 继承View 练习
 *
 *  主要掌握处理wrap_content和padding场景
 */

public class CircleView extends View {

    private Paint mPaint;
    private int mColor;
    private int mDeafault = 200;

    public CircleView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获得一个自定义的对应属性值集合
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        //取出属性集合中的某个属性值
        mColor = typedArray.getColor(R.styleable.CircleView_circle_color, Color.GREEN);
        //释放资源
        typedArray.recycle();
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //**************要特殊处理*******************************
        int paddingBottom = getPaddingBottom();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingLeft = getPaddingLeft();
        //**************要特殊处理*******************************


        int width = getWidth()-paddingLeft-getPaddingRight();
        int height = getHeight()-paddingBottom-paddingTop;
        int radius = Math.min(width,height)/2;

        canvas.drawCircle(getWidth()/2, getHeight()/2, radius, mPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode ==MeasureSpec.AT_MOST){
            setMeasuredDimension(mDeafault,mDeafault);
        }else if (widthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(mDeafault, heightSize);
        }else if (heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize, mDeafault);
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
