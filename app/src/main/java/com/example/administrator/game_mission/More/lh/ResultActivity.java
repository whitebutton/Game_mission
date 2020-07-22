package com.example.administrator.game_mission.More.lh;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.game_mission.AllCtl;
import com.example.administrator.game_mission.R;

import java.util.Random;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView type;
    private TextView level;
    private ConstraintLayout center;
    private Button close;
    private int types;
    private Button countine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lh_activity_result);
        Intent intent = getIntent();
        types = intent.getIntExtra("types", 0);
        initView();
        setQuestion(types);
        if (AllCtl.continueGame){
            countine.setVisibility(View.VISIBLE);
        }else {
            countine.setVisibility(View.GONE);
        }
    }

    private void setQuestion(int num) {
        if (num == 2) {
            Random random = new Random();
            Log.i("aaafa", "setQuestion: " + AllCtl.alllQuestion.size());
            type.setText(AllCtl.alllQuestion.get(random.nextInt(AllCtl.alllQuestion.size())).getContent());
        }
        if (num == 1) {
            Random random = new Random();
            Log.i("aaafa", "setQuestion: " + AllCtl.trueWords.size());
            type.setText(AllCtl.trueWords.get(random.nextInt(AllCtl.trueWords.size())).getContent());
        }
        if (num == 0) {
            Random random = new Random();
            Log.i("aaafa", "setQuestion: " + AllCtl.bigAdventure.size());
            type.setText(AllCtl.bigAdventure.get(random.nextInt(AllCtl.bigAdventure.size())).getContent());
        }
    }

    private void initView() {
        //类型
        type = (TextView) findViewById(R.id.type);
        //等级
        level = (TextView) findViewById(R.id.level);
        //内容
        center = (ConstraintLayout) findViewById(R.id.center);
        close = (Button) findViewById(R.id.close);
        close.setOnClickListener(this);
        level.setText("当前题库：" + AllCtl.currType.getString("curr", "普通"));
        countine = (Button) findViewById(R.id.countine);
        countine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
//                MyDialog myDialog = new MyDialog(this, R.style.defaultDialogStyle);
//                myDialog.setCancel(new MyDialog.IOnCancelListener() {
//                    @Override
//                    public void onCancel(MyDialog Dialog) {
//                        Dialog.dismiss();
//                    }
//                });
//                myDialog.setConfirm(new MyDialog.IOnConfirmListener() {
//                    @Override
//                    public void onConfirm(MyDialog Dialog) {
//                        Dialog.dismiss();
//                        finish();
//                    }
//                });
//                myDialog.show();
                finish();
                break;
            case R.id.countine:
                setQuestion(types);
                break;
        }
    }
}
