package com.ec.vone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.ec.vone.R;

import static android.R.attr.max;

/**
 * Created by user on 2017/5/15.
 * 心率target值UI
 */

public class LineView extends View {
    private Paint mPaint;
    private int linecolor ;//数轴的背景颜色
    private int tolerantColor;//默认的正常区间的颜色
    private int currentColor;//当前的数的标记颜色
    private int lowValue, highValue;//数轴上默认的正常区间
    private int minValue = 30, maxValue = 220;//数轴的两端
    private int currentValue;
    private int lineHeight;//数轴的高度
    String minText = String.valueOf(minValue), maxText = String.valueOf(maxValue);


    public LineView(Context context) {
       this(context, null);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LineView,0,defStyleAttr);

        for (int i = 0; i < typedArray.length(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.LineView_currentColor:
                    currentColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.LineView_lineColor:
                    linecolor = typedArray.getColor(attr,Color.GRAY);
                    break;
                case R.styleable.LineView_tolerantColor:
                    tolerantColor = typedArray.getColor(attr,Color.BLUE);
                    break;
                case R.styleable.LineView_highValue:
                    highValue = typedArray.getInt(attr,maxValue);
                    break;
                case R.styleable.LineView_lowValue:
                    lowValue = typedArray.getInt(attr,minValue);
                    break;
                case R.styleable.LineView_currentValue:
                    currentValue = typedArray.getInt(attr,maxValue /2 ) ;
                    break;
                case R.styleable.LineView_lineHeight:
                    lineHeight = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics()));
                    break;
            }
        }

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float strokeWidth = lineHeight / 2.0f;//绘制数轴的画笔粗细
        float cursorWidth = strokeWidth * 1.5f;//当前数值游标的高度
        float lineY = getPaddingTop() + cursorWidth;

        //数轴上每一个单位所占的width
        float cWidth = (getWidth() - getPaddingLeft() - getPaddingRight()) / (maxValue - minValue);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(linecolor);

        canvas.drawLine(getPaddingLeft() + strokeWidth,lineY,
                getWidth() - getPaddingRight() - strokeWidth,lineY,mPaint );

        //绘制默认的正常区间
        mPaint.setColor(tolerantColor);
        canvas.drawLine(getPaddingLeft() + (lowValue - minValue) * cWidth,
                lineY, getPaddingLeft() + (highValue - minValue) * cWidth,lineY,mPaint);

        //绘制数值游标位置
        mPaint.setColor(currentColor);
        mPaint.setStrokeWidth(cursorWidth);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
        canvas.drawRect(getPaddingLeft() + (currentValue - minValue) * cWidth - cWidth /4,
                lineY - cursorWidth  / 2,
                getPaddingLeft() + (currentValue - minValue) * cWidth + cWidth /4,
                lineY + cursorWidth / 2 ,mPaint);

        //渲染文字
        mPaint.setColor(Color.GRAY);
        mPaint.setTextSize(lineHeight);
        canvas.drawText(minText,getPaddingLeft(),getPaddingTop() + lineHeight * 2,mPaint);
        canvas.drawText(maxText,getWidth() - getPaddingRight() - mPaint.measureText(maxText),
                getPaddingTop() + lineHeight * 2,mPaint);
        canvas.drawText(String.valueOf(lowValue),
                getPaddingLeft() + (lowValue - minValue) * cWidth - mPaint.measureText(String.valueOf(lowValue)) /2,
                getPaddingTop() + lineHeight * 2,mPaint);
        canvas.drawText(String.valueOf(highValue),
                getPaddingLeft() + (highValue - minValue) * cWidth - mPaint.measureText(String.valueOf(highValue)) /2,
                getPaddingTop() + lineHeight * 2,mPaint);
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
        postInvalidate();
    }
}
