package com.ec.vone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.ec.vone.R;
import com.ec.vone.view.utils.DateUtils;

/**
 * Created by user on 2017/5/3.
 */

public class LinechartView extends View {
    private Paint mPaint;
    private int mGridLineColor;//格线颜色
    private int mLineColor;//折线颜色
    private int mBg;//背景色
    private int[] valuesZ;
    private int[] valuesY;
    private int[] valuesX;
    private String valueName;
    private int mToppadding = 200;

    public long[] getTimes() {
        return times;
    }

    public void setTimes(long[] times) {
        this.times = times;
    }

    private long[] times;
    private int mWidth;
    private int mHeight;

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public int[] getValuesY() {
        return valuesY;
    }

    public void setValuesY(int[] valuesY) {
        this.valuesY = valuesY;
    }

    public int[] getValuesX() {
        return valuesX;
    }

    public void setValuesX(int[] valuesX) {
        this.valuesX = valuesX;
    }

    public int[] getValuesZ() {
        return valuesZ;
    }

    public void setValuesZ(int[] valuesZ) {
        this.valuesZ = valuesZ;
    }


    public LinechartView(Context context) {
        this(context, null);
    }

    public LinechartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinechartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LinechartView, 0, defStyleAttr);

        for (int i = 0; i < typedArray.length(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.LinechartView_lineColor:
                    mLineColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.LinechartView_gridLineColor:
                    mGridLineColor = typedArray.getColor(attr, Color.GRAY);
                    break;
                case R.styleable.LinechartView_background:
                    mBg = typedArray.getColor(attr, Color.alpha(33333333));
                    break;
            }
        }
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            throw new IllegalArgumentException("width must be EXACTLY,you should set like android:width=\"200dp\"");
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else if (widthMeasureSpec == MeasureSpec.AT_MOST) {

            throw new IllegalArgumentException("height must be EXACTLY,you should set like android:height=\"200dp\"");
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Rect rectX = new Rect();
        rectX.left = 150 + (getWidth() - getPaddingRight() - 50) / 3 * 2;
        rectX.top = mToppadding + getPaddingTop();
        rectX.right = (getWidth() - getPaddingRight() - 50);
        rectX.bottom = getHeight() - getPaddingBottom() - 50;
        drawIng(canvas, "X", valuesX, rectX);

        Rect rectY = new Rect();
        rectY.left = 150 + (getWidth() - getPaddingRight() - 50) / 3;
        rectY.top = mToppadding + getPaddingTop();
        rectY.right = (getWidth() - getPaddingRight() - 50) / 3 * 2;
        rectY.bottom = getHeight() - getPaddingBottom() - 50;
        drawIng(canvas, "Y", valuesY, rectY);

        Rect rectZ = new Rect();
        rectZ.left = 150 + getPaddingRight();
        rectZ.top = mToppadding + getPaddingTop();
        rectZ.right = (getWidth() - getPaddingRight() - 50) / 3;
        rectZ.bottom = getHeight() - getPaddingBottom() - 50;
        drawIng(canvas, "Z", valuesZ, rectZ);

    }


    private void drawIng(Canvas canvas, String type, int[] values, Rect r) {
        mPaint.setColor(mGridLineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        canvas.drawRect(r, mPaint);
        int rWidth = r.right - r.left;
        int rHeight = r.bottom - r.top;

        int aW = rWidth / 9 * 2;

        int aH = rHeight / 11;
        int mgH = aH / 2 + mToppadding;

        //根据values的值，找出最大值和最小值，确定y轴范围

        for (int i = 0; i < 5; i++) {
            canvas.drawLine(i * aW + r.left + aW / 4, mToppadding - 30, i * aW + r.left + aW / 4, mToppadding, mPaint);
        }
        //时间轴的黑点
        mPaint.setStrokeWidth(20);
        for (int i = 0; i < 11; i++) {
            canvas.drawLine(r.left - 20, mgH + i * aH, r.left, mgH + i * aH, mPaint);
            if (i == 10) continue;
            canvas.drawLine(r.left - 20, mgH + i * aH + aH / 7 , r.left, mgH + i * aH + aH / 7 , mPaint);
            canvas.drawLine(r.left - 20, mgH + i * aH + aH / 7 * 2, r.left, mgH + i * aH + aH / 7 * 2, mPaint);
            canvas.drawLine(r.left - 20, mgH + i * aH + aH / 7 * 3, r.left, mgH + i * aH + aH / 7 * 3, mPaint);
            canvas.drawLine(r.left - 20, mgH + i * aH + aH / 7 * 4, r.left, mgH + i * aH + aH / 7 * 4, mPaint);
            canvas.drawLine(r.left - 20, mgH + i * aH + aH / 7 * 5, r.left, mgH + i * aH + aH / 7 * 5, mPaint);
            canvas.drawLine(r.left - 20, mgH + i * aH + aH / 7 * 6, r.left, mgH + i * aH + aH / 7 * 6, mPaint);
        }
        mPaint.setColor(mGridLineColor);
        mPaint.setTextSize(50);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
        int[] vL = getMinMaxValue(values);
        String vMin = String.valueOf(vL[0]);
        String vMid = String.valueOf(vL[1]);
        String vMax = String.valueOf(vL[2]);

        String title = valueName + "_" + type;
        canvas.drawText(title, r.left + (r.right - r.left) / 2 - mPaint.measureText(title) / 2, 50, mPaint);
        //画时间轴
        canvas.rotate(90, 0, 0);
        for (int i = 0; i < 11; i++) {
            String time = DateUtils.getDateToString(times[times.length / 11 * i] * 1000);
            time = time.substring(time.length() - 5, time.length());
            canvas.drawText(time, mgH + i * aH - mPaint.measureText(time) / 2, +100 - r.left, mPaint);
        }
        //数值轴
        canvas.rotate(-90, 0, 0);
        float y = mToppadding - 30 - Math.abs(mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top) / 2;
        canvas.drawText(vMin, r.left + aW / 4 - mPaint.measureText(vMin) / 2, y, mPaint);
        canvas.drawText(vMid, r.left + aW / 4 + aW * 2 - mPaint.measureText(vMid) / 2, y, mPaint);
        canvas.drawText(vMax, r.left + aW / 4 + aW * 4 - mPaint.measureText(vMax) / 2, y, mPaint);

        //画点和线
        //现在的y轴是时间，有规律，递增的  x轴是数值，

        mPaint.setColor(mLineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        //先求出x/y轴上每一个单位占多少dp
        float xx = aW * 4.0f / (vL[2] - vL[0]);
        float yy = aH * 10.0f / times.length;

        Path path = new Path();
        for (int i = 0; i < values.length; i++) {
            //画path
            if (i == 0) {
                path.moveTo((values[i] - vL[0]) * xx + r.left + aW / 4, mgH + yy * i);
            } else {
                path.lineTo((values[i] - vL[0]) * xx + r.left + aW / 4, mgH + yy * i);
            }
        }
        canvas.drawPath(path, mPaint);
    }


    private int[] getMinMaxValue(int[] values) {
        int min = values[0], max = values[0];
        for (int i = 0; i < values.length - 1; i++) {

            if (values[i] > max) max = values[i];
            if (values[i] < min) min = values[i];
        }
        max = Math.round(max * 1.0f / 100.0f) * 100;
        min = Math.round(min * 1.0f / 100.0f) * 100;
        if (max + min == 0)
            return new int[]{min, 0, max};
        else {
            if ((max + min) % 200 == 0)
                return new int[]{min, (min + max) / 2, max};
            else return new int[]{min, (min + max + 100) / 2, max + 100};
        }

    }


}
