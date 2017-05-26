package com.ec.vone.utils;

import android.graphics.PointF;
import android.util.Log;

import static android.R.attr.x;
import static android.R.attr.y;

/**
 * Created by vone on 2017/5/23.
 */

public class CircleUtils {
    /**
     * 已知半径的圆的圆心和圆外一点坐标，求该点到圆的两个切点
     * @param r 半径
     * @param a 圆心x
     * @param b 圆心y
     * @param m 点x
     * @param n 点y
     * @return 两个切点
     */
    public static PointF[] getPointTangency(float r , float a , float b , float m , float n) {
        PointF[] pointFs = new PointF[2];
        PointF p1 = new PointF();
        PointF p2 = new PointF();

        float m_pow_2 = (float) Math.pow(m ,2);
        float n_pow_2 = (float) Math.pow(n ,2);
        float r_pow_2 = (float) Math.pow(r ,2);
        float a_pow_2 = (float) Math.pow(a ,2);
        float b_pow_2 = (float) Math.pow(b ,2);





        Log.i("MSL", "getPointTangency: " + p1.x + "," + p1.y +"," + p2.x +"," + p2.y);

        pointFs[0] = p1;
        pointFs[1] = p2;

        return pointFs;
    }
}
