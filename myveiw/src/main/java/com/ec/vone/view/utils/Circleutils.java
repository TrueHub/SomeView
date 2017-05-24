package com.ec.vone.view.utils;

import android.graphics.PointF;
import android.util.Log;

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

        float x = a - m ;
        float y = n - b ;
        float x2 = (float) Math.pow(x ,2);
        float y2 = (float) Math.pow(y ,2);
        float r2 = (float) Math.pow(r ,2);
        float a2 = (float) Math.pow(a ,2);
        float b2 = (float) Math.pow(b ,2);


        k1 = (float) ( (y + Math.sqrt( Math.pow(r2 - y2 , 2) + y2)) / (x2 - r2));
        k2 = (float) ( (y - Math.sqrt( Math.pow(r2 - y2 , 2) + y2)) / (x2 - r2));

        float w1 = n - k1 * m ;
        float w2 = n - k2 * m;
        float s1 = w1 - b ;
        float s12 = (float) Math.pow(s1, 2);
        float s2 = w2 - b ;
        float s22 = (float) Math.pow(s2, 2);

        float k12 = (float) (Math.pow(k1, 2) + 1);
        float k22 = (float) (Math.pow(k2 ,2) + 1);



        Log.d("MSL", "getPoints: "  + k1 + " , "+ k2);

        PointF p1 = new PointF();

        p1.x = (float) ((  k1 * s1 - a + Math.pow( k12 * ( r2 - a2 - s12) ,2 ) + (Math.pow( k1 * s1 - a , 2)) ) / k12);

//        p1.x = (r * r * k1) / (k1 * ( m - a )  + b - n) ;
        p1.y = k1 * (p1.x - m) + n;

        PointF p2 = new PointF();
//        p2.x = (r * r * k2) / ( k2 * ( m - a ) + b -n) ;
        p2.x = (float) ((  k2 * s2 - a + Math.pow( k22 * ( r2 - a2 - s22) ,2 ) + (Math.pow( k2 * s2 - a , 2)) ) / k22);
        p2.y = k2 * ( p2.x - m) + n;

        pointFs[0] = p1;
        pointFs[1] = p2;

        return pointFs;
    }
}
