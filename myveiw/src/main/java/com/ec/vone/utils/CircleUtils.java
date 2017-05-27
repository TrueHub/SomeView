package com.ec.vone.utils;

import android.graphics.PointF;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.x;
import static android.R.attr.y;
import static android.R.id.list;

/**
 * Created by vone on 2017/5/23.
 */

public class CircleUtils {
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

        float a_2_BigD = mul(a,a);
        float b_2_BigD = mul(b,b);
        float m_2_BigD = mul(m,m);
        float n_2_BigD = mul(n,n);
        float r_2_BigD = mul(r,r);
        float n_b_BigD = mul(b,n);
        float m_a_BigD = mul(m,a);

//        float sq = (float) java.lang.Math.sqrt(a_2_BigD - 2 * m_a_BigD + b_2_BigD - 2 * n_b_BigD + m_2_BigD + n_2_BigD - r_2_BigD);
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


        Log.d("MSL", "getPointTangency: " + x1 + "," + y1 + "\n" + x2 + "," + y2);
        Log.d("MSL", "getPointTangency: " + sq + ", " + a_pow_2 + "," + b_pow_2 + "," + m_pow_2 + "," + n_pow_2 + ",," + b_pow_3);

        points[0] = x1;
        points[1] = y1;
        points[2] = x2;
        points[3] = y2;

        return points;
    }

    public static void main(String[] a) {

        float[] pointFs = getPointTangency(2, 0, 0, 3, 2);

        System.out.println(pointFs[0] + "," + pointFs[1] + "\n" + pointFs[2] + "," + pointFs[3]);

    }

    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();

    }

    public static double sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.subtract(b2).doubleValue();

    }

    public static float mul(float d1, float d2) {
        BigDecimal b1 = new BigDecimal(Float.toString(d1));
        BigDecimal b2 = new BigDecimal(Float.toString(d2));
        return b1.multiply(b2).floatValue();

    }

    public static double div(double d1, double d2) {

        return div(d1, d2, 3);

    }

    public static double div(double d1, double d2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

}
