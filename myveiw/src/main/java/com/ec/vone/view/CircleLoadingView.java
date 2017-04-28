package com.ec.vone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.ec.vone.R;

/**
 * Created by vone on 2017/4/26.
 */

public class CircleLoadingView extends View {
    public CircleLoadingView(Context context) {
        this(context, null);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private int mFirstColor;
    private int mSecondColor;
    private int mProgress;
    private int mCircleWidth;
    private Paint mPaint;


    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleLoading, 0, defStyleAttr);

        for (int i = 0; i < typedArray.length(); i++) {
            int attr = typedArray.getIndex(i);

            switch (attr) {
                case R.styleable.CircleLoading_circleWidth:
                    mCircleWidth = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 10, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CircleLoading_firstColor:
                    mFirstColor = typedArray.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.CircleLoading_secondColor:
                    mSecondColor = typedArray.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.CircleLoading_progress:
                    mProgress = typedArray.getInteger(attr, 0);
                    break;
            }
        }
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2;
        float r2 = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2;
        radius = radius > r2 ? r2 : radius;
        mPaint.setColor(mFirstColor);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);//画出了作为背景底色的圆

        float circleRadius = radius - mCircleWidth / 2;
        float diffWandH = Math.abs(getWidth() - getHeight());

        RectF oval = new RectF();
        if (getHeight() > getWidth()) {
            oval.left = getPaddingLeft() + mCircleWidth / 2;
            oval.right = getPaddingRight() + mCircleWidth / 2 + circleRadius * 2;
            oval.top = getPaddingTop() + mCircleWidth / 2 +diffWandH /2;
            oval.bottom = getPaddingBottom() + mCircleWidth / 2 + circleRadius * 2 +diffWandH /2;
        }else {
            oval.left = getPaddingLeft() + mCircleWidth / 2+diffWandH /2;
            oval.right = getPaddingRight() + mCircleWidth / 2 + circleRadius * 2+diffWandH /2;
            oval.top = getPaddingTop() + mCircleWidth / 2 ;
            oval.bottom = getPaddingBottom() + mCircleWidth / 2 + circleRadius * 2 ;
        }

        mPaint.setColor(mSecondColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        canvas.drawArc(oval, 270, mProgress * 3.6f, false, mPaint);

    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

}
