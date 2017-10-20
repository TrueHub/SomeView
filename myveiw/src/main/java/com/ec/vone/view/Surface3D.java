package com.ec.vone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by longyang on 2017/10/20.
 */

public class Surface3D extends View {
    private final int ROTATE = 120;


    private Paint mAxesPaint;//坐标轴画笔
    private Paint mBrokenLinePaint;//虚线画笔

    public Surface3D(Context context) {
        this(context, null);
    }

    public Surface3D(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Surface3D(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mAxesPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAxesPaint.setColor(Color.BLUE);
        mAxesPaint.setStyle(Paint.Style.STROKE);
        mAxesPaint.setStrokeWidth(2);

        mBrokenLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBrokenLinePaint.setColor(Color.BLUE);
        mBrokenLinePaint.setStyle(Paint.Style.STROKE);
        mBrokenLinePaint.setStrokeWidth(2);
        mBrokenLinePaint.setPathEffect(new DashPathEffect(new float[]{8f, 8f}, 0));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        mBrokenLinePaint.setShader(new LinearGradient(0, 0, getWidth(), 0,
//                new int[]{Color.TRANSPARENT, Color.WHITE, Color.WHITE, Color.TRANSPARENT},
//                new float[]{0, 0.05f, 0.95f, 1f}, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float yDi = (float) (getMeasuredWidth() / 4);
        Path axes = new Path();
        axes.moveTo(0, yDi);
        axes.lineTo(0, getMeasuredHeight() - yDi);
        axes.lineTo(getMeasuredWidth() / 2, getMeasuredHeight());
        axes.lineTo(getMeasuredWidth(), getMeasuredHeight() - yDi);
        canvas.drawPath(axes, mAxesPaint);

        Path brokenPath = new Path();
        for (int i = 0; i < 3; i++) {
            brokenPath.reset();
            float yd = yDi + (getMeasuredHeight() - yDi * 2) / 2 * i;
            brokenPath.moveTo(0, yd);
            brokenPath.lineTo(getMeasuredWidth() / 2, (getMeasuredHeight() - yDi * 2) / 2 * i);
            brokenPath.lineTo(getMeasuredWidth(), yd);
            canvas.drawPath(brokenPath, mBrokenLinePaint);
        }
        Path path1 = new Path();
        for (int i = 0; i < 3; i++) {
            path1.reset();
            canvas.translate(getMeasuredWidth()/2 / 4 * (i + 1), yDi / 4 * ( i + 1));

            path1.moveTo(0, getMeasuredHeight() - yDi);
            path1.lineTo(getMeasuredWidth() / 2, getMeasuredHeight() - yDi * 2);
            canvas.drawPath(path1,mBrokenLinePaint);
            canvas.translate(-getMeasuredWidth()/2 / 4 * (i + 1), -yDi / 4 * ( i + 1));

        }


    }
}
