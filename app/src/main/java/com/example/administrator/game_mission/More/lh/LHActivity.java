package com.example.administrator.game_mission.More.lh;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.administrator.game_mission.More.lh.fragment.ChooseFragment;
import com.example.administrator.game_mission.R;

public class LHActivity extends AppCompatActivity {

    private FrameLayout fl1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lh);
        initView();

    }

    private void initView() {
        fl1 = (FrameLayout) findViewById(R.id.fl1);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl1,new ChooseFragment()).commit();
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldHideInput(view, ev)) {
                InputMethodManager Object = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (Object != null) {
                    Object.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    //判断是否隐藏键盘
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
//获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
// 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
