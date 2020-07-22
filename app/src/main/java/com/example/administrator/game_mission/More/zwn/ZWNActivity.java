package com.example.administrator.game_mission.More.zwn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.game_mission.R;

public class ZWNActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private ImageView sub_img;
    private ImageView add_img;
    private ImageView sub_img2;
    private ImageView add_img2;
    private Button btn1;
    private TextView num1_tv;
    private TextView num2_tv;
    private int num1, num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zwn);
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sub_img = (ImageView) findViewById(R.id.sub_img);
        sub_img.setOnClickListener(this);
        add_img = (ImageView) findViewById(R.id.add_img);
        add_img.setOnClickListener(this);
        sub_img2 = (ImageView) findViewById(R.id.sub_img2);
        sub_img2.setOnClickListener(this);
        add_img2 = (ImageView) findViewById(R.id.add_img2);
        add_img2.setOnClickListener(this);
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        num1_tv = (TextView) findViewById(R.id.num1_tv);
        num2_tv = (TextView) findViewById(R.id.num2_tv);
    }

    @Override
    public void onClick(View v) {
        num1 = Integer.parseInt(num1_tv.getText().toString());
        num2 = Integer.parseInt(num2_tv.getText().toString());
        switch (v.getId()) {
            case R.id.add_img:
                if (num1 < num2) {
                    num1_tv.setText(String.valueOf(num1 + 1));
                } else {
                    Toast.makeText(this, "箱子放不下炸弹啦", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sub_img:
                if (num1 > 1) {
                    num1_tv.setText(String.valueOf(num1 - 1));
                }
                break;
            case R.id.add_img2:
                if (num2 < 6) {
                    num2_tv.setText(String.valueOf(num2 + 1));
                }
                break;
            case R.id.sub_img2:
                if (num2 > num1) {
                    num2_tv.setText(String.valueOf(num2 - 1));
                }
                break;
            case R.id.btn1:
                Intent intent = new Intent(ZWNActivity.this, Zwn2Activity.class);
                intent.putExtra("num1", num1);
                intent.putExtra("num2", num2);
                startActivity(intent);
                break;
        }
    }
}
