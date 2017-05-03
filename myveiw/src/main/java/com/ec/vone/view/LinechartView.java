package com.ec.vone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.view.View;

import com.ec.vone.R;

/**
 * Created by user on 2017/5/3.
 */

public class LinechartView extends View {
    private Paint mPaint;
    private int mGridLineColor;//格线颜色
    private int mLineColor;//折线颜色
    private int mBg;//背景色
    private int[] values ;
    private int[] times;

    public int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }

    public int[] getTimes() {
        return times;
    }

    public void setTimes(int[] times) {
        this.times = times;
    }

    public LinechartView(Context context) {
        this(context,null);
    }

    public LinechartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LinechartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LinechartView,0,defStyleAttr);

        for (int i = 0; i < typedArray.length(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.LinechartView_lineColor:
                    mLineColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.LinechartView_gridLineColor:
                    mGridLineColor = typedArray.getColor(attr,Color.GRAY);
                    break;
                case R.styleable.LinechartView_background:
                    mBg = typedArray.getColor(attr,Color.alpha(33333333));
                    break;
            }
        }
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        Rect r = new Rect();
        r.left = 20 + getPaddingLeft();
        r.top = 20 + getPaddingTop();
        r.right = getWidth() - getPaddingRight() - 20 ;
        r.bottom = getHeight() - getPaddingBottom() - 20;
        mPaint.setColor(mGridLineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);


    }
}
