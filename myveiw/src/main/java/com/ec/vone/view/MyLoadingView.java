package com.ec.vone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.ec.vone.R;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by user on 2017/4/25.
 * 三层循环loading
 */

public class MyLoadingView extends View {
    public MyLoadingView(Context context) {
        this(context, null);
    }

    public MyLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private int mFirstColor;//首层颜色
    private int mSecondColor;//另一层颜色
    private int mThirdColor;//第三层颜色
    private int mCircleWidth;//圆圈width熟悉
    private Paint mPaint;//paint画笔
    private int mSpeed;//覆盖速度
    private int mProgress;//当前进度
    private int isNext;//是否进行下一圈


    public MyLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyLoadingView, 0, defStyleAttr);

        for (int i = 0; i < typedArray.length(); i++) {
            int attr = typedArray.getIndex(i);

            switch (attr) {
                case R.styleable.MyLoadingView_firstColor:
                    mFirstColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyLoadingView_secondColor:
                    mSecondColor = typedArray.getColor(attr, Color.WHITE);
                    break;
                case R.styleable.MyLoadingView_thirdColor:
                    mThirdColor = typedArray.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.MyLoadingView_circleWidth:
                    mCircleWidth = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics())
                    );
                    break;
                case R.styleable.MyLoadingView_speed:
                    mSpeed = typedArray.getInt(attr, 30);
                    break;
            }
        }
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mProgress ++ ;
                    if (mProgress == 360) {
                        mProgress = 0 ;
                        isNext ++;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }).start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth() /2 ;
        int radius = center - mCircleWidth /2;
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mCircleWidth);

        //圆弧的形状和大小的界限
        RectF rectF = new RectF(center - radius,center - radius,center + radius , center + radius );

        switch (isNext % 3 ) {
            case 0 :
                mPaint.setColor(mFirstColor);//圆环的颜色
                canvas.drawCircle(center,center,radius,mPaint);

                mPaint.setColor(mSecondColor);//圆弧的颜色
                canvas.drawArc(rectF,-90,mProgress,false,mPaint);
                break;
            case 1 :
                mPaint.setColor(mSecondColor);//圆环的颜色,为上一圈的圆弧的颜色
                canvas.drawCircle(center,center,radius,mPaint);

                mPaint.setColor(mThirdColor);//圆弧的颜色，为上一圈的圆环的颜色
                canvas.drawArc(rectF,-90,mProgress,false,mPaint);
                break;
            case 2 :
                mPaint.setColor(mThirdColor);//圆环的颜色,为上一圈的圆弧的颜色
                canvas.drawCircle(center,center,radius,mPaint);

                mPaint.setColor(mFirstColor);//圆弧的颜色，为上一圈的圆环的颜色
                canvas.drawArc(rectF,-90,mProgress,false,mPaint);
                break;

        }

    }


}
