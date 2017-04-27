package com.ec.vone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ec.vone.R;

/**
 * Created by vone on 2017/4/27.
 */

public class MyDialView extends View {
    public MyDialView(Context context) {
        this(context, null);
    }

    public MyDialView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private int mFirstColor, mSecondColor;
    private int mProgress;
    private int mGraduationCount, mSecondGraduationCount;//大刻度数量和二级刻度数量
    private String mCenterText;

    private Paint mPaint;

    //分别为：外圆半径，内圆半径，内圆的内层圆半径，大刻度尺寸，二级刻度尺寸
    private float mOutRadius, mInsideRadius,mStrokeWidth, mThirdRadius, mDuationSize, mGraduationSecondSize;

    public MyDialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyDialView, 0, defStyleAttr);

        for (int i = 0; i < typedArray.length(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.MyDialView_centerText:
                    mCenterText = typedArray.getString(attr);
                    break;
                case R.styleable.MyDialView_firstColor:
                    mFirstColor = typedArray.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.MyDialView_secondColor:
                    mSecondColor = typedArray.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.MyDialView_graduationCount:
                    mGraduationCount = typedArray.getInteger(attr, 5);
                    break;
                case R.styleable.MyDialView_secondGraduationCount:
                    mSecondGraduationCount = typedArray.getInteger(attr, 3);
                    break;
                case R.styleable.MyDialView_progress:
                    mProgress = typedArray.getInteger(attr, 40);
                    break;
            }
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth(), height = getHeight();
        mStrokeWidth = 5;//线条粗细
        if (width > height) {
            mOutRadius = (height - getPaddingBottom() - getPaddingTop() - mStrokeWidth) / 2;
        } else {
            mOutRadius = (width - getPaddingLeft() - getPaddingRight() - mStrokeWidth) / 2;
        }
        mInsideRadius = mOutRadius / 7 * 5;//这里将内圆半径设为外圆的5/6
        mThirdRadius = mOutRadius / 2; //内圆的画笔粗为外圆半径的1/3


        float a = (float) ((getHeight()  -Math.sqrt(2) * mInsideRadius) /2);//下方空白部分，圆心根据这个值向下偏移

        RectF rectF = new RectF();
        rectF.left = width / 2 - mOutRadius ;
        rectF.right = rectF.left + mOutRadius * 2 ;
        rectF.top = height / 2 - mOutRadius + a /2;
        rectF.bottom = rectF.top + mOutRadius * 2 ;

        mPaint.setColor(mFirstColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        canvas.drawArc(rectF,165,210,false,mPaint);

        //画内圈的外弧线
        rectF.left = width / 2 - mInsideRadius ;
        rectF.right = rectF.left + mInsideRadius * 2 ;
        rectF.top = height / 2 - mInsideRadius + a /2;
        rectF.bottom = rectF.top + mInsideRadius * 2 ;
        canvas.drawArc(rectF,150,240,true,mPaint);

        //内圈的内弧线
/*        mPaint.setColor(Color.WHITE);
        rectF.left = width / 2 - mThirdRadius ;
        rectF.right = rectF.left + mThirdRadius * 2 ;
        rectF.top = height / 2 - mThirdRadius + a /2;
        rectF.bottom = rectF.top + mThirdRadius * 2 ;
        canvas.drawArc(rectF,150,240,true,mPaint);

        mPaint.setColor(mFirstColor);*/
        rectF.left = width / 2 - mThirdRadius ;
        rectF.right = rectF.left + mThirdRadius * 2 ;
        rectF.top = height / 2 - mThirdRadius + a /2;
        rectF.bottom = rectF.top + mThirdRadius * 2 ;
        canvas.drawArc(rectF,150,240,false,mPaint);

    }
}
