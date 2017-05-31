package com.ec.vone.utils;

import android.graphics.PointF;
/**
 * Created by vone on 2017/5/25.
 */

public class CircleUtils {

    /**
     * 求两圆之间最短线段的中点
     * @param r_1st 第一个圆的半径
     * @param x_1st 第一个圆的x
     * @param y_1st 第一个圆的y
     * @param r_2nd 第二个圆半径
     * @param x_2nd 第二个圆的x
     * @param y_2nd 第二个圆的y
     * @return 中点坐标
     */
    public static PointF getCtrlPoint(float r_1st , float x_1st , float y_1st, float r_2nd , float x_2nd , float y_2nd){
        PointF pointF = new PointF();

        float l = (float) ((Math.sqrt( Math.pow(x_1st - x_2nd , 2) + Math.pow(y_1st - y_2nd ,2) ) - r_2nd - r_1st) /2);
        float w = (l + r_1st) / (l + r_2nd);//比例
        //由以下公式直接拿到中点坐标：
        /*在直角坐标系内,已知两点P1(x1,y1),P2(x2,y2);在两点连线上有一点P,
        设它的坐标为(x,y),且线段P1P比线段PP2的比值为 λ ，那么可以求出P的坐标为
        x=(x1 + λ · x2) / (1 + λ)
        y=(y1 + λ · y2) / (1 + λ)*/

        pointF.x = (x_1st + w * x_2nd) / (1 + w);
        pointF.y = (y_1st + w * y_2nd) / (1 + w);

        return pointF;
    }

    /**
     * 已知半径的圆的圆心和圆外一点坐标，求该点到圆的两个切点
     *
     * @param r 半径
     * @param a 圆心x
     * @param b 圆心y
     * @param m 点x
     * @param n 点y
     * @return 两个切点
     */
    public static float[] getPointTangency(float r, float a, float b, float m, float n) {
        float[] points = new float[4];

        float m_pow_2 = (float) Math.pow(m, 2);
        float n_pow_2 = (float) Math.pow(n, 2);
        float r_pow_2 = (float) Math.pow(r, 2);
        float a_pow_2 = (float) Math.pow(a, 2);
        float b_pow_2 = (float) Math.pow(b, 2);
        float b_pow_3 = (float) Math.pow(b, 3);


        float sq = (float) java.lang.Math.sqrt(a_pow_2 - 2 * m * a + b_pow_2 - 2 * n * b + m_pow_2 + n_pow_2 - r_pow_2);

        float x1 = -(-a_pow_2 + m * a - b_pow_2 + n * b + r_pow_2) / (a - m)
                - ((b - n) * (a_pow_2 * b + b * m_pow_2 + b * n_pow_2
                - 2 * b_pow_2 * n - b * r_pow_2 + n * r_pow_2 + b_pow_3
                - 2 * a * b * m + a * r * sq - m * r * sq))
                / ((a - m) * (a_pow_2 - 2 * a * m + b_pow_2 - 2 * b * n + m_pow_2 + n_pow_2));

        float x2 = -(-a_pow_2 + m * a - b_pow_2 + n * b + r_pow_2) / (a - m)
                - ((b - n) * (a_pow_2 * b + b * m_pow_2 + b * n_pow_2
                - 2 * b_pow_2 * n - b * r_pow_2 + n * r_pow_2 + b_pow_3
                - 2 * a * b * m - a * r * sq + m * r * sq))
                / ((a - m) * (a_pow_2 - 2 * a * m + b_pow_2 - 2 * b * n + m_pow_2 + n_pow_2));

        float y1 = (a_pow_2 * b + b * m_pow_2 + b * n_pow_2 - 2 * b_pow_2 * n
                - b * r_pow_2 + n * r_pow_2 + b_pow_3
                - 2 * a * b * m + a * r * sq - m * r * sq) /
                (a_pow_2 - 2 * a * m + b_pow_2 - 2 * b * n + m_pow_2 + n_pow_2);
        float y2 = (a_pow_2 * b + b * m_pow_2 + b * n_pow_2 - 2 * b_pow_2 * n
                - b * r_pow_2 + n * r_pow_2 + b_pow_3
                - 2 * a * b * m - a * r * sq + m * r * sq) /
                (a_pow_2 - 2 * a * m + b_pow_2 - 2 * b * n + m_pow_2 + n_pow_2);


//        Log.d("MSL", "getPointTangency: " + x1 + "," + y1 + "\n" + x2 + "," + y2);
//        Log.d("MSL", "getPointTangency: " + sq + ", " + a_pow_2 + "," + b_pow_2 + "," + m_pow_2 + "," + n_pow_2 + ",," + b_pow_3);

        points[0] = x1;
        points[2] = y1;
        points[1] = x2;
        points[3] = y2;

        return points;
    }

    public static void main(String[] a) {

        float[] pointFs = getPointTangency(2, 0, 0, -3, -2);

        System.out.println(pointFs[0] + "," + pointFs[2] + "\n" + pointFs[1] + "," + pointFs[3]);

    }

}
