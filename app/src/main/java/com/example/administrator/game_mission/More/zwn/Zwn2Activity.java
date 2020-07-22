package com.example.administrator.game_mission.More.zwn;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.administrator.game_mission.AllCtl;
import com.example.administrator.game_mission.More.lh.ResultActivity;
import com.example.administrator.game_mission.R;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Zwn2Activity extends AppCompatActivity {

    private ImageView back;
    private GridView grid_zwn2;
    private int num1, num2;
    private Set<Integer> box = new HashSet<>();
    private ImageView grid_anim;
    private AnimationDrawable drawable;
    private GridAdapter adapter = new GridAdapter();
    private Boolean onecStart=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zwn2);
        Intent intent = getIntent();
        num1 = intent.getIntExtra("num1", 1);
        num2 = intent.getIntExtra("num2", 6);
        Log.i("TAG", "onCreate: " + num1);
        initView();
        initData();
    }

    private void initData() {
        box.clear();
        while (box.size() < num1) {
            int boom = new Random().nextInt(num2);
            box.add(boom);
            Log.i("TAG", "initData: " + boom);
        }
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        grid_zwn2 = (GridView) findViewById(R.id.grid_zwn2);
        grid_zwn2.setAdapter(adapter);

        grid_anim = (ImageView) findViewById(R.id.grid_anim);
        drawable= (AnimationDrawable) grid_anim.getDrawable();
    }

    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return num2;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(Zwn2Activity.this).inflate(R.layout.item_grid_zwn2, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.item_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!onecStart) return;
                    viewHolder.item_img.setImageResource(R.drawable.box_open);
                    Iterator iterator = box.iterator();
                    while (iterator.hasNext()) {
                        if (position == (Integer) iterator.next()) {
                            onecStart=false;
                            viewHolder.item_img.setImageResource(R.drawable.box_open2);
                            grid_anim.setVisibility(View.VISIBLE);
                            drawable.start();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Zwn2Activity.this, ResultActivity.class);
                                    intent.putExtra("types",2);
                                    startActivity(intent);
                                }
                            },1000);
                            //震动
                            if (AllCtl.shock){
                                Vibrator vibrator= (Vibrator) Zwn2Activity.this.getSystemService(VIBRATOR_SERVICE);
                                vibrator.vibrate(800);
                            }
                        }
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {
            public View rootView;
            public ImageView item_img;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.item_img = (ImageView) rootView.findViewById(R.id.item_img);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        onecStart=true;
        adapter.notifyDataSetChanged();
        grid_zwn2.setAdapter(adapter);
        grid_anim.setVisibility(View.GONE);
    }
}
