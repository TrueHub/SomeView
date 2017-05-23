package com.ec.vone.view.utils;

import android.graphics.PointF;

/**
 * Created by vone on 2017/5/23.
 */

public class Circleutils {
    /**
     * 已知半径的圆的圆心和圆外一点坐标，求该点到圆的两个切点
     * @parm r 半径
     * @param a 圆心x
     * @param b 圆心y
     * @param m 点x
     * @param n 点y
     * @return
     */
    public static PointF[] getPoints(float r , float a , float b , float m , float n){
        PointF[] pointFs = new PointF[2];

        //两个切线的斜率
        float k1 , k2;

        k1 = (float) Math.sqrt(2 * r * r +(Math.pow((a - m) * (n - b) /( a - m + 1) ,2)));
        k2 =  - (float) Math.sqrt(2 * r * r +(Math.pow((a - m) * (n - b) /( a - m + 1) ,2)));


        return null;


    }
}
