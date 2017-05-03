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
 * 仪表盘控件
 */

public class MyDialView extends View {
    public MyDialView(Context context) {
        this(context, null);
    }

    public MyDialView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private int mColor;
    private int mProgress;
    private int mGraduationCount, mSecondGraduationCount;//大刻度数量和每个大刻度内的二级刻度数量
    private String mCenterText;

    private Paint mPaint;

    public MyDialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyDialView, 0, defStyleAttr);

        for (int i = 0; i < typedArray.length(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.MyDialView_centerText:
                    mCenterText = typedArray.getString(attr);
                    break;
                case R.styleable.MyDialView_color:
                    mColor = typedArray.getColor(attr, Color.BLUE);
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
        float mStrokeWidth = 3;
        float mOutRadius;//外圈半径
//        if (width > height) {
        if (width - height * 3 / 2 + (getPaddingBottom() + getPaddingTop() + mStrokeWidth) / 2 >= 0) {
            mOutRadius = (height - getPaddingBottom() - getPaddingTop() - mStrokeWidth) / 2 + (height + getPaddingTop() + getPaddingBottom() + mStrokeWidth) / 8;
        } else {
            mOutRadius = (width - getPaddingLeft() - getPaddingRight() - mStrokeWidth) / 2;
        }
        float mInsideRadius = mOutRadius / 14 * 11;//内圈外弧半径
        float mThirdRadius = mOutRadius / 2;//内圈内弧半径

        //下方空白部分，圆心根据这个值向下偏移
        float a = mOutRadius - mInsideRadius / 2;
        canvas.translate(0, a / 2);//画布向下偏移


        RectF rectF = new RectF();
        rectF.left = width / 2 - mOutRadius;
        rectF.right = rectF.left + mOutRadius * 2;
        rectF.top = height / 2 - mOutRadius;
        rectF.bottom = rectF.top + mOutRadius * 2;

        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        canvas.drawArc(rectF, 165, 210, false, mPaint);

        //画内圈的外弧线
        rectF.left = width / 2 - mInsideRadius;
        rectF.right = rectF.left + mInsideRadius * 2;
        rectF.top = height / 2 - mInsideRadius;
        rectF.bottom = rectF.top + mInsideRadius * 2;
        canvas.drawArc(rectF, 150, 240, false, mPaint);

        //圆心坐标：
        float Ox = rectF.centerX();
        float Oy = rectF.centerY();

        //画个伪圆心
        canvas.drawCircle(Ox, Oy, 5, mPaint);

        //内圈的内弧线
        rectF.left = width / 2 - mThirdRadius;
        rectF.right = rectF.left + mThirdRadius * 2;
        rectF.top = height / 2 - mThirdRadius;
        rectF.bottom = rectF.top + mThirdRadius * 2;
        canvas.drawArc(rectF, 150, 240, false, mPaint);


        //封口
        canvas.drawLine((float) (width / 2 - Math.sqrt(3) / 2 * mInsideRadius),
                Oy + (mInsideRadius - mStrokeWidth) / 2, (float) (width / 2 - Math.sqrt(3) / 2 * mThirdRadius),
                Oy + (mThirdRadius - mStrokeWidth) / 2, mPaint);

        canvas.drawLine((float) (width / 2 + Math.sqrt(3) / 2 * mInsideRadius),
                Oy + (mInsideRadius - mStrokeWidth) / 2, (float) (width / 2 + Math.sqrt(3) / 2 * mThirdRadius),
                Oy + (mThirdRadius - mStrokeWidth) / 2, mPaint);

        //画填充部分
        mStrokeWidth = mInsideRadius - mThirdRadius;
        rectF.left = (width + mStrokeWidth) / 2 - mInsideRadius;
        rectF.right = (width + mStrokeWidth) / 2 + mThirdRadius;
        rectF.top = Oy - mThirdRadius - mStrokeWidth / 2;
        rectF.bottom = Oy + mThirdRadius + mStrokeWidth / 2;
        mPaint.setStrokeWidth(mStrokeWidth);

        float needleAngle = 2.1f * mProgress;//指针偏移的角度
        float strokeAngle = 15 + 2.1f * mProgress;
        if (mProgress == 0) strokeAngle = 0;
        if (mProgress == 100) strokeAngle = 240;

        canvas.drawArc(rectF, 150, strokeAngle, false, mPaint);

        //画仪表的指针
        float needleRadius = (mOutRadius + mInsideRadius) / 2;//指针长度。定义为外圆内圆的外圈之差的二分之一
        float needleStopX = (float) (Ox + needleRadius * Math.cos((needleAngle + 165) * Math.PI / 180));
        float needleStopY = (float) (Oy + needleRadius * Math.sin((needleAngle + 165) * Math.PI / 180));

        mPaint.setStrokeWidth(5);
        canvas.drawLine(Ox, Oy, needleStopX, needleStopY, mPaint);

        //画表盘中心
        rectF.left = Ox - mOutRadius / 14 * 3;
        rectF.right = Ox + mOutRadius / 14 * 3;
        rectF.top = Oy - mOutRadius / 7;
        rectF.bottom = Oy + mOutRadius / 7;
        canvas.drawArc(rectF,0,360,true,mPaint);//中心的椭圆

        //画刻度
        mPaint.setStrokeWidth(2);
        canvas.rotate(-105, Ox, Oy);//先旋转到最左边，准备绘制
        for (int i = 0; i <= mSecondGraduationCount * mGraduationCount; i++) {
            if (i % mSecondGraduationCount == 0) {//根据定义的值绘制出大刻度线
                canvas.drawLine(width / 2 , height / 2 - mOutRadius, width / 2, height / 2 - mOutRadius + (mOutRadius - mInsideRadius) / 3 * 2, mPaint);
            }
            canvas.drawLine(width / 2, height / 2 - mOutRadius, width / 2, height / 2 - mOutRadius + (mOutRadius - mInsideRadius) / 3 , mPaint);//小刻度
            canvas.rotate(210 / (mSecondGraduationCount * mGraduationCount * 1.0f), Ox, Oy);//根据大小刻度总量，计算每个小刻度之间的距离，每次循环末尾旋转画布
        }
        canvas.rotate( -105,Ox, Oy );//画布归位
    }

    public void setProgress(int progress){
        this.mProgress = progress;
        postInvalidate();
    }
}
