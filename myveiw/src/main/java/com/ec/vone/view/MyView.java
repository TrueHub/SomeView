package com.ec.vone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.ec.vone.R;

import java.util.Random;

/**
 * Created by user on 2017/4/19.
 * 自定义View：基础篇：实现验证码效果。
 */

public class MyView extends View {

    private Paint mPaint;
    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /**获取自定义的样式*/
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyleAttr, 0);

        for (int i = 0; i < typedArray.length(); i++) {

            int attr = typedArray.getIndex(i);

            switch (attr) {
                case R.styleable.CustomTitleView_titleText:
                    mTitleText = typedArray.getString(attr);
                    break;

                case R.styleable.CustomTitleView_titleTextColor:
                    mTitleTextColor = typedArray.getColor(attr, Color.BLACK);//默认颜色为BLUE
                    break;

                case R.styleable.CustomTitleView_titleTextSize:
                    //参数2要求px，使用TypedValued可以将sp转换为px， 这里默认size为18sp，
                    mTitleTextSize = typedArray.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();

        mPaint = new Paint();

        mPaint.setTextSize(mTitleTextSize);
        mPaint.setAntiAlias(true);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleText = randomText();
                postInvalidate();
            }
        });

    }

    private String randomText() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10) + "");
        }
        return sb.toString();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        int width = 0, height = 0;

        switch (specMode) {
            case MeasureSpec.EXACTLY:
                width = specSize + getPaddingRight() + getPaddingLeft();
                break;

            case MeasureSpec.AT_MOST:
                width = (int) (mPaint.measureText(mTitleText) + getPaddingLeft() + getPaddingRight());
                break;
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                height = specSize + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.AT_MOST:
                height = (int) (Math.abs(mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top) + getPaddingTop() + getPaddingBottom());
                break;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.setColor(getResources().getColor(R.color.color_bg));
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText, (getWidth() - mPaint.measureText(mTitleText)) / 2, getY(), mPaint);//mPaint.measureText(mTitleText)测量text的长度

        //设置文字大小
        String[] str = new String[4];
        for (int i = 0; i < 4; i++) {
            float x, y;
            str[i] = mTitleText.substring(i, i + 1);

            if (i > 0) {
                x = (getWidth() - mPaint.measureText(mTitleText)) / 2 + mPaint.measureText(str[i]);
            }else {
                x = (getWidth() - mPaint.measureText(mTitleText)) / 2;
            }
            y = (float) (Math.random() * getY() / 3);
            mPaint.setTextSize((float) (Math.random() * (mTitleTextSize - 40)) + 40);
            canvas.drawText(str[i],x,y,mPaint);
        }


        //画噪点
        int x, y;
        for (int i = 0; i < 400; i++) {
            x = (int) (Math.random() * getWidth());
            y = (int) (Math.random() * getHeight());
            mPaint.setColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
            canvas.drawCircle(x, y, 3, mPaint);
        }
    }

    public float getY() {
        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();
        return (getHeight() + fm.bottom - fm.top) / 2 - fm.descent;
    }

}
