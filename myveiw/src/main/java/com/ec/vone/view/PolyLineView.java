package com.ec.vone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static android.R.attr.width;

/**
 * Created by vone on 2017/5/1.
 */

public class PolyLineView extends View {
    private int mWidth , mHeight;
    private String[] mXAxis, mYAxis ;//xy轴的刻度

    public PolyLineView(Context context) {
        this(context,null);
    }

    public PolyLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PolyLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {

        }else  if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize ;
        }

        if (heightMode == MeasureSpec.AT_MOST) {


        }else if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if ( mXAxis.length ==0 || mYAxis.length ==0) {
            throw new IllegalArgumentException("X & Y Axis must have a enough length.");
        }

    }
}
