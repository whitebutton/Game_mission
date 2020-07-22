package com.example.administrator.game_mission.Random;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.game_mission.AdventureBean;
import com.example.administrator.game_mission.AllCtl;
import com.example.administrator.game_mission.HttpUtil;
import com.example.administrator.game_mission.More.lh.LHActivity;
import com.example.administrator.game_mission.R;
import com.example.administrator.game_mission.TruthBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BuiltInActivity extends AppCompatActivity {

    private ImageView back;
    private TextView TrueWords;
    private ListView list1;
    private TextView adventure;
    private ListView list2;
    private boolean flag1 = false;
    private boolean flag2 = false;

    private HttpUtil httpUtil;
    private JSONObject jsonObject;
    private TruthBean truthBean;
    private AdventureBean adventureBean;
    private TruthAdapater truthAdapater;
    private AdventureAdapater adventureAdapater;

    private List<String> lists1 = new ArrayList<>();
    private List<Integer> id = new ArrayList<>();
    private List<String> lists2 = new ArrayList<>();
    private List<Integer> id2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_built_in);
        initView();
//        initData();
//        initData2();
        truthAdapater = new TruthAdapater();
        list1.setAdapter(truthAdapater);

        adventureAdapater = new AdventureAdapater();
        list2.setAdapter(adventureAdapater);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        initData();
//        initData2();
        Log.i("TAG", "onResume: ");
    }

    // 真心话
//    private void initData() {
//        httpUtil = new HttpUtil(this);
//        jsonObject = new JSONObject();
//        String url = "getTruthOrAdvAll";
//        try {
//            jsonObject.put("types", 1);
//            jsonObject.put("defaults", 0);
//            httpUtil.httpPost(url, jsonObject, new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//                    super.handleMessage(msg);
//                    truthBean = (TruthBean) msg.obj;
//                    lists1.clear();
//                    id.clear();
//                    for (int i = 0; i < truthBean.getData().size(); i++) {
//                        if (truthBean.getData().get(i).getLevels() == AllCtl.level && truthBean.getData().get(i).getDefaults() == 0){
//                            lists1.add(truthBean.getData().get(i).getContent());
//                            id.add(truthBean.getData().get(i).getId());
//                        }
//                    }
//                    truthAdapater.notifyDataSetChanged();
//                }
//
//            }, TruthBean.class);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    // 大冒险
//    private void initData2() {
//        httpUtil = new HttpUtil(this);
//        jsonObject = new JSONObject();
//        String url = "getTruthOrAdvAll";
//        try {
//            jsonObject.put("types", 0);
//            httpUtil.httpPost(url, jsonObject, new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//                    super.handleMessage(msg);
//                    adventureBean = (AdventureBean) msg.obj;
//                    lists2.clear();
//                    id2.clear();
//                    for (int i = 0; i < adventureBean.getData().size(); i++) {
//                        if (adventureBean.getData().get(i).getLevels() == AllCtl.level && adventureBean.getData().get(i).getDefaults() == 0){
//                            lists2.add(adventureBean.getData().get(i).getContent());
//                            id2.add(adventureBean.getData().get(i).getId());
//                        }
//                    }
//                    adventureAdapater.notifyDataSetChanged();
//                }
//            }, AdventureBean.class);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    class TruthAdapater extends BaseAdapter {
        @Override
        public int getCount() {
            return lists1.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = View.inflate(BuiltInActivity.this, R.layout.list_item, null);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.text2.setText(lists1.get(i));
            viewHolder.text2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(BuiltInActivity.this, "内容不支持修改", Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AllCtl.content = lists1.get(i);
                    AllCtl.id = id.get(i);
                    startActivity(new Intent(BuiltInActivity.this,DetailActivity.class));
                }
            });
            return view;
        }

        class ViewHolder {
            public View rootView;
            public TextView text1;
            public TextView text2;
            public ImageView detail;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.text1 = (TextView) rootView.findViewById(R.id.text1);
                this.text2 = (TextView) rootView.findViewById(R.id.text2);
                this.detail = (ImageView) rootView.findViewById(R.id.detail);
            }

        }
    }

    class AdventureAdapater extends BaseAdapter {
        @Override
        public int getCount() {
            return lists2.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = View.inflate(BuiltInActivity.this, R.layout.list_item, null);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.text2.setText(lists2.get(i));
            viewHolder.text2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(BuiltInActivity.this, "内容不支持修改", Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AllCtl.content = lists2.get(i);
                    AllCtl.id = id2.get(i);
                    startActivity(new Intent(BuiltInActivity.this,Detail2Activity.class));
                }
            });
            return view;
        }

        class ViewHolder {
            public View rootView;
            public TextView text1;
            public TextView text2;
            public ImageView detail;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.text1 = (TextView) rootView.findViewById(R.id.text1);
                this.text2 = (TextView) rootView.findViewById(R.id.text2);
                this.detail = (ImageView) rootView.findViewById(R.id.detail);
            }

        }
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TrueWords = (TextView) findViewById(R.id.TrueWords);
        list1 = (ListView) findViewById(R.id.list1);
        list2 = (ListView) findViewById(R.id.list2);
        TrueWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag1 = !flag1;
                if (flag1) {
                    list1.setVisibility(View.VISIBLE);
                } else {
                    list1.setVisibility(View.GONE);
                }
            }
        });
        adventure = (TextView) findViewById(R.id.adventure);
        adventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag2 = !flag2;
                if (flag2) {
                    list2.setVisibility(View.VISIBLE);
                } else {
                    list2.setVisibility(View.GONE);
                }
            }
        });
    }
}
