package com.ec.vone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import static android.R.attr.y;

/**
 * Created by user on 2017/5/22.
 */

public class BezierDemo extends View {
    public BezierDemo(Context context) {
        this(context , null);
    }

    public BezierDemo(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public BezierDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Ax = 100 ;
        Bx = 1200 ;
        Ay = 600 ;
        By = 600 ;

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);

        bezierPaint = new Paint();
        bezierPaint.setColor(Color.RED);
        bezierPaint.setAntiAlias(true);
        bezierPaint.setStrokeWidth(3);
        bezierPaint.setStyle(Paint.Style.STROKE);

        bezierPath = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(Ax , Ay, 8, mPaint);
        canvas.drawCircle(Bx ,By , 8 ,mPaint);

        canvas.drawLine(Ax,Ay, c1X, c1Y,mPaint);
        canvas.drawLine(c2X ,c2Y , c1X, c1Y,mPaint);
        canvas.drawLine(c2X , c2Y ,Bx, By ,mPaint);

        bezierPath.reset();
        bezierPath.moveTo(Ax ,Ay);
        bezierPath.cubicTo(c1X, c1Y ,c2X , c2Y ,Bx , By);

        canvas.drawPath(bezierPath,bezierPaint);
    }

    private float c1X = 100, c1Y = 100 , c2X = 3, c2Y = 3;//当前点击的点1和点2
    private Paint mPaint , bezierPaint;
    private float Ax , Ay ;//A点坐标，即起点
    private float Bx , By ;//B
    private Path bezierPath ;
    private boolean mIsSecondPoint = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_POINTER_DOWN:
                mIsSecondPoint = true;
                break;
            case MotionEvent.ACTION_MOVE:
                c1X = event.getX(0);
                c1Y = event.getY(0);

                if (mIsSecondPoint){
                    c2X = event.getX(1);
                    c2Y = event.getY(1);
                }
                invalidate();
                break;

            case MotionEvent.ACTION_POINTER_UP:
                mIsSecondPoint = false ;
                invalidate();
                break;
        }
        return true;
    }
}
