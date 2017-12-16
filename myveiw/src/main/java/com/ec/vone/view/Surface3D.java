package com.ec.vone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by longyang on 2017/10/20.
 *
 */

public class Surface3D extends View {
    private final int ROTATE = 120;


    private Paint mAxesPaint;//坐标轴画笔
    private Paint mBrokenLinePaint;//虚线画笔
    private Paint mLinePaint;//曲线画笔
    private float xGrid;
    private float yGrid;
    private float zGrid;
    private float xLineGrid;
    private float yLineGrid;
    private float bottomPadding;
    private float topPadding;
    private float rightPadding;
    private float leftPadding;
    private float xCenterTop;
    private float xCenterBottom;
    private float yCenterLeft;
    private float yCenterRight;
    private float halfAxesY;
    private float halfAxesX;

    private float[][] bodyData;
    private float yPointGrid;

    public Surface3D(Context context) {
        this(context, null);
    }

    public Surface3D(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Surface3D(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
        initPaint();
    }

    private void initData() {

        bodyData = new float[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, -.4f, 0, -.3f, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, -.5f, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, -1, -.5f, -1, 0, 0, 0, 0, 0, 1.5f, 0, 0, 0},
                {0, 0, -.8f, -.8f, -.5f, 0, 0, 0, 0, .5f, 1, .5f, 0, 0},
                {0, 0, -.5f, -1, 0, 1, 0, 0, 0, 0, .5f, 0, 0, 0},
                {0, 0, -.3f, 0, .2f, 0, 0, 0, 0, 0, 0, -.5f, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},

//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

//        bodyData = new float[10][14];
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 14; j++) {
//                bodyData[i][j] = 0;
//                if (i > 0 && i < 9) {
//
//                    if (j == 1) bodyData[i][j] = -1;
//                    if (j == 3) bodyData[i][j] = 1;
//                    if (j == 4) bodyData[i][j] = 2;
//                    if (j == 5) bodyData[i][j] = 3;
//                    if (j == 6) bodyData[i][j] = 2;
//                    if (j == 7) bodyData[i][j] = 1;
//                    if (j == 9) bodyData[i][j] = -1;
//                    if (j == 10) bodyData[i][j] = -2;
//                }
//            }
//        }
    }

    private void initPaint() {
        mAxesPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAxesPaint.setColor(Color.BLUE);
        mAxesPaint.setStyle(Paint.Style.STROKE);
        mAxesPaint.setStrokeWidth(3);

        mBrokenLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBrokenLinePaint.setColor(Color.GRAY);
        mBrokenLinePaint.setStyle(Paint.Style.STROKE);
        mBrokenLinePaint.setStrokeWidth(2);
        mBrokenLinePaint.setPathEffect(new DashPathEffect(new float[]{10f, 10f}, 0));

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.GREEN);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(3);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        mBrokenLinePaint.setShader(new LinearGradient(0, 0, getWidth(), 0,
//                new int[]{Color.TRANSPARENT, Color.WHITE, Color.WHITE, Color.TRANSPARENT},
//                new float[]{0, 0.05f, 0.95f, 1f}, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        leftPadding = getPaddingLeft();
        rightPadding = getPaddingRight();
        topPadding = getPaddingTop();
        bottomPadding = getPaddingBottom();
        halfAxesX = (getMeasuredWidth() - leftPadding - rightPadding) / 2;
        halfAxesY = (getMeasuredHeight() - topPadding - bottomPadding) / 2;
        xCenterTop = halfAxesX + leftPadding;
        xCenterBottom = getMeasuredWidth() - halfAxesX - rightPadding;
        yCenterLeft = halfAxesY + topPadding;
        yCenterRight = getMeasuredHeight() - halfAxesY - bottomPadding;
        xGrid = halfAxesX / 14; //14等分 ，15个点
        yGrid = halfAxesY / 10; //10等分 ，单条曲线的上下振动
        zGrid = halfAxesY / 11; //12等分 ，12列曲线
        xLineGrid = halfAxesX / 11; // 12等分，12列曲线的x轴间隔距离
        yLineGrid = halfAxesY / 11 / 2; // 12等分，12列曲线的y轴间隔距离
        yPointGrid = halfAxesY / 2 / 14;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAxex(canvas);//绘制坐标轴

        mLinePaint.setColor(Color.CYAN);
        ArrayList<ArrayList<PointF>> dirData = new ArrayList<>();
        for (int i = 0; i < bodyData.length; i++) {
            float xInit;
            float yInit;
            float[] lineData = bodyData[i];

            xInit = leftPadding + (i + 1) * xLineGrid;
            yInit = yCenterLeft + (i + 1) * yLineGrid;
            drawSingleLine(canvas ,lineData,xInit,yInit,dirData);
            if (i == 0 ) {
                float[] d = new float[lineData.length];
                xInit = leftPadding;
                yInit = yCenterLeft;
                drawSingleLine(canvas,d,xInit,yInit,dirData);
            }else if (i == bodyData.length - 1){
                float[] d = new float[lineData.length];
                xInit = leftPadding + halfAxesX;
                yInit = yCenterLeft + halfAxesY / 2;
                drawSingleLine(canvas,d,xInit,yInit,dirData);
            }


        }
        mLinePaint.setColor(Color.GREEN);
//        for (ArrayList<PointF> aDirLineData : dirData) {
//            ArrayList<PointF> a = new ArrayList<>();
//            a.addAll(aDirLineData);
//            drawLine(canvas, a);
//        }
        for (int i = 0; i < dirData.size(); i++) {
            ArrayList<PointF> aDirLineData = dirData.get(i);
            ArrayList<PointF> a = new ArrayList<>();
            a.addAll(aDirLineData);
            drawLine(canvas, a, mLinePaint);
        }
    }

    private void drawSingleLine(Canvas canvas , float[] lineData , float xInit ,float yInit , ArrayList<ArrayList<PointF>> dirData){
        ArrayList<PointF> bodyPointFs = new ArrayList<>();
        for (int j = 0; j < lineData.length; j++) {
            if (dirData.size() < j + 1){
                dirData.add(new ArrayList<PointF>());
            }
            if (j == 0) {
                PointF zeroPoint = new PointF(xInit, yInit);
                bodyPointFs.add(zeroPoint);
                bodyPointFs.add(zeroPoint);

//                dirData.add(new ArrayList<PointF>());
                dirData.get(j).add(zeroPoint);
                dirData.get(j).add(zeroPoint);
            }
            PointF dataPoint = new PointF(xInit + (j) * xGrid,
                    yInit - yGrid * lineData[j] - (j) * yPointGrid);
            bodyPointFs.add(dataPoint);
                dirData.get(j).add(dataPoint);
            if (j == lineData.length - 1) {
                dirData.add(new ArrayList<PointF>());
                PointF endPoint = new PointF(xInit + lineData.length * xGrid,
                        yInit - lineData.length * yPointGrid);
                bodyPointFs.add(endPoint);
                bodyPointFs.add(endPoint);

                dirData.get(j + 1).add(endPoint);
                dirData.get(j + 1).add(endPoint);
            }

            drawLine(canvas, bodyPointFs, mLinePaint);
        }
    }

    private void drawLine(Canvas canvas, ArrayList<PointF> bodyPointFs, Paint mLinePaint) {
        Path linePath = new Path();
        ArrayList<PointF> bedSave = new ArrayList<>();
        function_Catmull_Rom(bodyPointFs, bodyPointFs.size(), bedSave, linePath);
        canvas.drawPath(linePath, mLinePaint);
    }

    /**
     * 绘制坐标轴
     *
     * @param canvas
     */
    private void drawAxex(Canvas canvas) {

        Path axes = new Path();
        axes.moveTo(leftPadding, yCenterLeft - halfAxesY / 2);
        axes.lineTo(leftPadding, yCenterLeft + halfAxesY / 2);
        axes.lineTo(xCenterBottom, getMeasuredHeight() - bottomPadding);
        axes.lineTo(getMeasuredWidth() - rightPadding, yCenterRight + halfAxesY / 2);
        canvas.drawPath(axes, mAxesPaint);

        Path brokenPath = new Path();
        for (int i = 0; i < 3; i++) {
            brokenPath.reset();
            float yl = yCenterLeft - halfAxesY / 2 + i * halfAxesY / 2;
            float yr = yCenterRight - halfAxesY / 2 + i * halfAxesY / 2;
            brokenPath.moveTo(leftPadding, yl);
            brokenPath.lineTo(xCenterTop, topPadding + i * halfAxesY / 2);
            brokenPath.lineTo(getMeasuredWidth() - rightPadding, yr);
            canvas.drawPath(brokenPath, mBrokenLinePaint);
        }
        Path path1 = new Path();
        Path path2 = new Path();
        for (int i = 0; i < 3; i++) {
            canvas.translate(halfAxesX / 4 * (i + 1), halfAxesY / 8 * (i + 1));

            path1.reset();
            path1.moveTo(leftPadding, yCenterLeft + halfAxesY / 2);
            path1.lineTo(xCenterTop, topPadding + halfAxesY);
            path1.lineTo(xCenterTop, topPadding);
            canvas.drawPath(path1, mBrokenLinePaint);

            path2.reset();
            path2.moveTo(xCenterTop, topPadding);
            path2.lineTo(xCenterTop, topPadding + halfAxesY);
            canvas.drawPath(path2, mBrokenLinePaint);

            canvas.translate(-halfAxesX / 4 * (i + 1), -halfAxesY / 8 * (i + 1));
        }
        Path path3 = new Path();
        for (int i = 0; i < 4; i++) {
            canvas.translate(-halfAxesX / 4 * i, halfAxesY / 8 * i);
            path3.reset();
            path3.moveTo(xCenterTop, topPadding);
            path3.lineTo(xCenterTop, topPadding + halfAxesY);
            path3.lineTo(getMeasuredWidth() - rightPadding, yCenterRight + halfAxesY / 2);
            if (i == 0)
                path3.lineTo(getMeasuredWidth() - rightPadding, yCenterRight - halfAxesY / 2);
            canvas.drawPath(path3, mBrokenLinePaint);

            canvas.translate(halfAxesX / 4 * i, -halfAxesY / 8 * i + 1);
        }

    }

    /**
     * 1、每次插值需要四个基础点（暂假设为A、B、C、D）。
     * 2、根据已知的四个基础点，插值算法每次只能实现在中间两个点间画出光滑的曲线（此处就是B点和C点）。
     *
     * @param pointFs 前面后面各插两个点，与原数据首尾相同
     */
    private void function_Catmull_Rom(ArrayList<PointF> pointFs, int cha, ArrayList<PointF> save, Path path) {
        if (pointFs.size() < 4) {
            return;
        }
        path.moveTo(pointFs.get(0).x, pointFs.get(0).y);
        save.add(pointFs.get(0));
        for (int index = 1; index < pointFs.size() - 2; index++) {
            PointF p0 = pointFs.get(index - 1);
            PointF p1 = pointFs.get(index);
            PointF p2 = pointFs.get(index + 1);
            PointF p3 = pointFs.get(index + 2);

            for (int i = 1; i <= cha; i++) {
                float t = i * (1.0f / cha);
                float tt = t * t;
                float ttt = tt * t;

                PointF pi = new PointF(); // intermediate pointFs
                pi.x = (float) (0.5 * (2 * p1.x + (p2.x - p0.x) * t + (2 * p0.x - 5 * p1.x + 4 * p2.x - p3.x) * tt + (3 * p1.x - p0.x - 3 * p2.x + p3.x)
                        * ttt));
                pi.y = (float) (0.5 * (2 * p1.y + (p2.y - p0.y) * t + (2 * p0.y - 5 * p1.y + 4 * p2.y - p3.y) * tt + (3 * p1.y - p0.y - 3 * p2.y + p3.y)
                        * ttt));
                path.lineTo(pi.x, pi.y);
                save.add(pi);
                pi = null;
            }
        }
        path.lineTo(pointFs.get(pointFs.size() - 1).x, pointFs.get(pointFs.size() - 1).y);
        save.add(pointFs.get(pointFs.size() - 1));
    }

}
