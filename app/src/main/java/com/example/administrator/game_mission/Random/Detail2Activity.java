package com.example.administrator.game_mission.Random;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.game_mission.AdventureBean;
import com.example.administrator.game_mission.AllCtl;
import com.example.administrator.game_mission.HttpUtil;
import com.example.administrator.game_mission.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Detail2Activity extends AppCompatActivity {

    private ImageView back;
    private TextView save;
    private ImageView down;
    private TextView level;
    private ImageView add;
    private TextView text;

//    private int num = AllCtl.level;
    private HttpUtil httpUtil;
    private JSONObject jsonObject = new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);
        initView();
        httpUtil = new HttpUtil(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "update";
                try {
                    jsonObject.put("id",AllCtl.id);
                    jsonObject.put("content",AllCtl.content);
                    jsonObject.put("defaults",0);
                    jsonObject.put("types",0);
//                    jsonObject.put("levels",num);
                    httpUtil.httpPost(url,jsonObject,new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            Toast.makeText(Detail2Activity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        }
                    },AdventureBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        save = (TextView) findViewById(R.id.save);
        down = (ImageView) findViewById(R.id.down);
        level = (TextView) findViewById(R.id.level);
//        level.setText(num + "");
        add = (ImageView) findViewById(R.id.add);
        text = (TextView) findViewById(R.id.text);
        text.setText(AllCtl.content);
//        down.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (num == 1) {
//                    Toast.makeText(Detail2Activity.this, "已达到最低级别", Toast.LENGTH_SHORT).show();
//                } else {
//                    num = num - 1;
//                    level.setText(num + "");
//                }
//            }
//        });
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (num == 5) {
//                    Toast.makeText(Detail2Activity.this, "已达到最高级别", Toast.LENGTH_SHORT).show();
//                } else {
//                    num = num + 1;
//                    level.setText(num + "");
//                }
//            }
//        });
    }
}
