package com.example.administrator.game_mission.More.lh.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.game_mission.R;


public class MyDialog extends Dialog implements View.OnClickListener {
    private ImageView cancle,confirm;
    private TextView content;
    private String message;
    private IOnCancelListener cancelListener;
    private IOnConfirmListener confirmListener;


    public void setMessage(String message) {
        this.message = message;
    }

    public void setCancel( IOnCancelListener listener) {
        this.cancelListener = listener;
    }

    public void setConfirm( IOnConfirmListener listener) {
        this.confirmListener = listener;
    }


    public MyDialog(Context context) {
        super(context);

    }

    public MyDialog(Context context, int themId) {
        super(context,themId);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lh_dialog_my);
        //设置宽度
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int) (size.x * 0.8); //设置dialog的宽度为当前手机屏幕的宽度*0.8
        getWindow().setAttributes(p);
        getWindow().setBackgroundDrawableResource(R.color.transparent); //背景透明

        cancle=findViewById(R.id.cancle);
        confirm=findViewById(R.id.confirm);
        content=findViewById(R.id.content);
        cancle.setOnClickListener(this);
        confirm.setOnClickListener(this);
        if (!TextUtils.isEmpty(message)){
            content.setText(message);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancle:
                if (cancelListener != null) {
                    cancelListener.onCancel(this);
                }
                break;
            case R.id.confirm:
                if (confirmListener != null) {
                    confirmListener.onConfirm(this);
                }
                break;
        }
    }

    public interface IOnCancelListener {
        void onCancel(MyDialog Dialog);
    }

    public interface IOnConfirmListener {
        void onConfirm(MyDialog Dialog);
    }

}
