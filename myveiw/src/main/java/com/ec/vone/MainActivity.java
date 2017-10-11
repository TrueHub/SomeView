package com.ec.vone;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }


    private void initView() {
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.MyDialogStyle)
                .setTitle("我是Title")
                .setMessage("我是message")
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", null)
                .create();
        dialog.show();
        /*//必须在调用show方法后才可修改样式
        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mController = mAlert.get(dialog);

            Field mMessage = mController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mController);
            mMessageView.setTextColor(Color.RED);//message样式修改成红色
            // title 同理
            Field mTitle = mController.getClass().getDeclaredField("mTitleView");
            mTitle.setAccessible(true);
            TextView mTitleView = (TextView) mTitle.get(mController);
            mTitleView.setTextColor(Color.MAGENTA);//title样式修改成彩色

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        //直接获取按钮并设置
        final Button pButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);//确认按键
        pButton.setTextColor(Color.GREEN);
        pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pButton.setText("点过了");
                pButton.setTextColor(Color.MAGENTA);
            }
        });
        Button nButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);//取消
        nButton.setTextColor(Color.BLUE);*/

    }
}