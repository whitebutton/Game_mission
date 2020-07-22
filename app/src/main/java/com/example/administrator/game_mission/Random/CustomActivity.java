package com.example.administrator.game_mission.Random;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.game_mission.AdventureBean;
import com.example.administrator.game_mission.HttpUtil;
import com.example.administrator.game_mission.R;
import com.example.administrator.game_mission.TruthBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustomActivity extends AppCompatActivity {

    private ImageView back;
    private TextView TrueWords;
    private TextView adventure;
    private ImageView add;
    private ListView list1;
    private ListView list2;

    private boolean flag1 = false;
    private boolean flag2 = false;

    private int num = 1;
    private int num2 = 1;
    private HttpUtil httpUtil;
    private JSONObject jsonObject = new JSONObject();
    private TruthBean truthBean;
    private AdventureBean adventureBean;

    private List<String> lists1 = new ArrayList<>();
    private List<Integer> levels1 = new ArrayList<>();
    private List<Integer> id_truth = new ArrayList<>();
    private List<Integer> id_advneture = new ArrayList<>();
    private List<String> lists2 = new ArrayList<>();
    private List<Integer> levels2 = new ArrayList<>();

    private MyAdapater1 myAdapater1;
    private MyAdapater2 myAdapater2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        initView();
        httpUtil = new HttpUtil(this);
        Data_truth();
        Data_adventure();
        myAdapater1 = new MyAdapater1();
        myAdapater2 = new MyAdapater2();
        list1.setAdapter(myAdapater1);
        list2.setAdapter(myAdapater2);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new AlertDialog.Builder(CustomActivity.this).setTitle("请选择要添加的类别").setIcon(R.drawable.game).setItems(R.array.name, new DialogInterface.OnClickListener() {
                    class ViewHolder {
                        public View rootView;
                        public ImageView down;
                        public TextView level;
                        public ImageView add;
                        public EditText text;
                        public TextView save;

                        public ViewHolder(View rootView) {
                            this.rootView = rootView;
                            this.down = (ImageView) rootView.findViewById(R.id.down);
                            this.level = (TextView) rootView.findViewById(R.id.level);
                            this.add = (ImageView) rootView.findViewById(R.id.add);
                            this.text = (EditText) rootView.findViewById(R.id.text);
                            this.save = (TextView) rootView.findViewById(R.id.save);
                        }
                    }

                    class ViewHolder22 {
                        public View rootView;
                        public ImageView down;
                        public TextView level;
                        public ImageView add;
                        public EditText text;
                        public TextView save;

                        public ViewHolder22(View rootView) {
                            this.rootView = rootView;
                            this.down = (ImageView) rootView.findViewById(R.id.down);
                            this.level = (TextView) rootView.findViewById(R.id.level);
                            this.add = (ImageView) rootView.findViewById(R.id.add);
                            this.text = (EditText) rootView.findViewById(R.id.text);
                            this.save = (TextView) rootView.findViewById(R.id.save);
                        }

                    }

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                AlertDialog.Builder builder = new AlertDialog.Builder(CustomActivity.this);
                                View view1 = View.inflate(CustomActivity.this, R.layout.alert_truthoravdenture, null);
                                builder.setIcon(R.drawable.game).setTitle("自定义真心话").setView(view1);
                                final AlertDialog alertDialog = builder.create();
                                final ViewHolder viewHolder = new ViewHolder(view1);
                                viewHolder.level.setText(num + "");
                                viewHolder.add.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (num == 5) {
                                            Toast.makeText(CustomActivity.this, "已达到最高等级", Toast.LENGTH_SHORT).show();
                                        } else {
                                            num = num + 1;
                                            viewHolder.level.setText(num + "");
                                        }
                                    }
                                });
                                viewHolder.down.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (num == 1) {
                                            Toast.makeText(CustomActivity.this, "已达到最低等级", Toast.LENGTH_SHORT).show();
                                        } else {
                                            num = num - 1;
                                            viewHolder.level.setText(num + "");
                                        }
                                    }
                                });
                                viewHolder.save.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (viewHolder.text.getText().toString().equals("")) {
                                            Toast.makeText(CustomActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                                        } else {
                                            initdata(viewHolder.text.getText().toString(), num);
                                            alertDialog.dismiss();
                                        }
                                    }
                                });
                                alertDialog.show();
                                break;
                            case 1:
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(CustomActivity.this);
                                View view2 = View.inflate(CustomActivity.this, R.layout.alert_truthoravdenture, null);
                                builder2.setIcon(R.drawable.game).setTitle("自定义真心话").setView(view2);
                                final AlertDialog alertDialog2 = builder2.create();
                                final ViewHolder22 viewHolder22 = new ViewHolder22(view2);
                                viewHolder22.level.setText(num2 + "");
                                viewHolder22.add.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (num2 == 5) {
                                            Toast.makeText(CustomActivity.this, "已达到最高等级", Toast.LENGTH_SHORT).show();
                                        } else {
                                            num2 = num2 + 1;
                                            viewHolder22.level.setText(num2 + "");
                                        }
                                    }
                                });
                                viewHolder22.down.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (num2 == 1) {
                                            Toast.makeText(CustomActivity.this, "已达到最低等级", Toast.LENGTH_SHORT).show();
                                        } else {
                                            num2 = num2 - 1;
                                            viewHolder22.level.setText(num2 + "");
                                        }
                                    }
                                });
                                viewHolder22.save.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (viewHolder22.text.getText().toString().equals("")) {
                                            Toast.makeText(CustomActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                                        } else {
                                            initdata2(viewHolder22.text.getText().toString(), num2);
                                            alertDialog2.dismiss();
                                        }
                                    }
                                });
                                alertDialog2.show();
                                break;
                        }
                    }
                }).create().show();
            }
        });
    }

    // 查自定义真心话
    private void Data_truth() {
        try {
            jsonObject.put("types", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpUtil.httpPost("getTruthOrAdvAll", jsonObject, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                truthBean = (TruthBean) msg.obj;
                lists1.clear();
                levels1.clear();
                id_truth.clear();
                for (int i = 0; i < truthBean.getData().size(); i++) {
                    if (truthBean.getData().get(i).getDefaults() == 1) {
                        lists1.add(truthBean.getData().get(i).getContent());
                        levels1.add(truthBean.getData().get(i).getLevels());
                        id_truth.add(truthBean.getData().get(i).getId());
                    }
                }
                myAdapater1.notifyDataSetChanged();
            }
        }, TruthBean.class);
    }
    // 查自定义大冒险
    private void Data_adventure() {
        try {
            jsonObject.put("types", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpUtil.httpPost("getTruthOrAdvAll", jsonObject, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                adventureBean = (AdventureBean) msg.obj;
                lists2.clear();
                levels2.clear();
                id_advneture.clear();
                for (int i = 0; i < adventureBean.getData().size(); i++) {
                    if (adventureBean.getData().get(i).getDefaults() == 1) {
                        lists2.add(adventureBean.getData().get(i).getContent());
                        levels2.add(adventureBean.getData().get(i).getLevels());
                        id_advneture.add(adventureBean.getData().get(i).getId());
                    }
                }
                myAdapater2.notifyDataSetChanged();
            }
        }, AdventureBean.class);
    }

    // 删自定义真心话
    private void del_truth(int id){
        String url = "del";
        try {
            jsonObject.put("id",id);
            httpUtil.httpPost(url,jsonObject,new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    truthBean = (TruthBean) msg.obj;
                    Data_truth();
                    Toast.makeText(CustomActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                }
            },TruthBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 删自定义大冒险
    private void del_adventure(int id){
        String url = "del";
        try {
            jsonObject.put("id",id);
            httpUtil.httpPost(url,jsonObject,new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    adventureBean = (AdventureBean) msg.obj;
                    Data_adventure();
                    Toast.makeText(CustomActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                }
            },AdventureBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initdata(String content, int num) {
        try {
            jsonObject.put("content", content);
            jsonObject.put("defaults", 1);
            jsonObject.put("levels", num);
            jsonObject.put("types", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "Add";
        httpUtil.httpPost(url, jsonObject, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Data_truth();
                Toast.makeText(CustomActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            }
        }, TruthBean.class);
    }

    public void initdata2(String content, int num) {
        try {
            jsonObject.put("content", content);
            jsonObject.put("defaults", 1);
            jsonObject.put("levels", num);
            jsonObject.put("types", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "Add";
        httpUtil.httpPost(url, jsonObject, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Data_adventure();
                Toast.makeText(CustomActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            }
        }, TruthBean.class);
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
        adventure = (TextView) findViewById(R.id.adventure);
        add = (ImageView) findViewById(R.id.add);
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

    // 真心话
    class MyAdapater1 extends BaseAdapter {
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
            view = View.inflate(CustomActivity.this, R.layout.list_item, null);
            ViewHolder1 viewHolder1 = new ViewHolder1(view);
            viewHolder1.text1.setText("级别：" + levels1.get(i));
            viewHolder1.text2.setText(lists1.get(i));
            viewHolder1.rootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CustomActivity.this);
                    builder.setIcon(R.drawable.danger).setTitle("提示").setMessage("您确定要删除此项吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i1) {
                                del_truth(id_truth.get(i));
                            myAdapater1.notifyDataSetChanged();
                        }
                    }).setNegativeButton("取消",null);
                    builder.create().show();
                    return true;
                }
            });
            return view;
        }

        class ViewHolder1 {
            public View rootView;
            public TextView text1;
            public TextView text2;
            public ImageView detail;

            public ViewHolder1(View rootView) {
                this.rootView = rootView;
                this.text1 = (TextView) rootView.findViewById(R.id.text1);
                this.text2 = (TextView) rootView.findViewById(R.id.text2);
                this.detail = (ImageView) rootView.findViewById(R.id.detail);
            }

        }
    }

    // 大冒险
    class MyAdapater2 extends BaseAdapter {
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
            view = View.inflate(CustomActivity.this, R.layout.list_item, null);
            ViewHolder2 viewHolder2 = new ViewHolder2(view);
            viewHolder2.text1.setText("级别：" + levels2.get(i));
            viewHolder2.text2.setText(lists2.get(i));
            viewHolder2.rootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CustomActivity.this);
                    builder.setIcon(R.drawable.danger).setTitle("提示").setMessage("您确定要删除此项吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i1) {
                            del_adventure(id_advneture.get(i));
                            myAdapater2.notifyDataSetChanged();
                        }
                    }).setNegativeButton("取消",null);
                    builder.create().show();
                    return true;
                }
            });
            return view;
        }

        class ViewHolder2 {
            public View rootView;
            public TextView text1;
            public TextView text2;
            public ImageView detail;

            public ViewHolder2(View rootView) {
                this.rootView = rootView;
                this.text1 = (TextView) rootView.findViewById(R.id.text1);
                this.text2 = (TextView) rootView.findViewById(R.id.text2);
                this.detail = (ImageView) rootView.findViewById(R.id.detail);
            }

        }
    }
}
