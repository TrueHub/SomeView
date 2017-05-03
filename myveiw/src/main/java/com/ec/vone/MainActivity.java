package com.ec.vone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ec.vone.view.LinechartView;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity {

    private LinechartView linechartView;
    private long[] times = new long[300];
    private int[] valueZ = new int[300];
    private int[] valueY = new int[300];
    private int[] valueX = new int[300];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        long now = (int) (System.currentTimeMillis() /1000);
        for (int i = 0; i < 300; i++) {
            valueZ[i] = (int) (Math.random() * 6000 - 3000);
            valueX[i] = (int) (Math.random() * 1000) - 500;
            valueY[i] = (int) (Math.random() * -200) - 200;
            times[i] = now;
            now++;
        }
        linechartView.setTimes(times);
        linechartView.setValuesZ(valueZ);
        linechartView.setValuesY(valueY);
        linechartView.setValuesX(valueX);
        linechartView.setValueName("Mag");
    }

    private void initView() {
        linechartView = (LinechartView) findViewById(R.id.linechartView);
    }


}
