package com.example.administrator.game_mission.More.lh.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.game_mission.AllCtl;
import com.example.administrator.game_mission.More.lh.LHActivity;
import com.example.administrator.game_mission.More.lh.PanBean;
import com.example.administrator.game_mission.More.tyx.TYXActivity;
import com.example.administrator.game_mission.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class ChooseFragment extends Fragment implements View.OnClickListener {
    private Button addpan;
    private ListView lv1;
    private ImageView back;
    private ArrayList<PanBean> list = new ArrayList<>();
    private lvAdapter2 mLvAdapter2;
    private lvAdapter mLvAdapter;
    private lvAdapter3 mLvAdapter3;
    private TextView title;
    private Button btn1;
    private Button btn2;
    private EditText panname;
    private int i;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private boolean once;
    private EditText nowEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lh_fragment_choose, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        addpan = (Button) view.findViewById(R.id.addpan);
        back = (ImageView) view.findViewById(R.id.back);
        title = (TextView) view.findViewById(R.id.title);
        btn1 = (Button) view.findViewById(R.id.btn1);
        btn2 = (Button) view.findViewById(R.id.btn2);
        panname = (EditText) view.findViewById(R.id.panname);
        lv1 = (ListView) view.findViewById(R.id.lv1);
        btn2.setOnClickListener(this);
        btn1.setOnClickListener(this);
        addpan.setOnClickListener(this);
        back.setOnClickListener(this);
        mLvAdapter = new lvAdapter();
        mLvAdapter2 = new lvAdapter2();
        mLvAdapter3 = new lvAdapter3();
        sp = getActivity().getSharedPreferences("key", MODE_PRIVATE);
        editor = sp.edit();
        int count = sp.getInt("count", 0);

        //第一次加入默认转盘
        if (count == 0) {
            list.add(new PanBean("默认转盘1", new String[]{"真心话", "大冒险", "再来一次", "喝杯酒", "亲一口", "唱首歌", "跳支舞", "真心话大冒险", "大冒险", "真心话"}));
            list.add(new PanBean("默认转盘2", new String[]{"大冒险", "再来一次", "真心话", "真心话大冒险", "亲一口", "真心话", "跳支舞", "喝杯酒", "大冒险", "唱首歌"}));
            saveMessage();
        } else if (!once) {
            list.clear();
            for (int j = 0; j < sp.getInt("listSize", 0); j++) {
                String news[] = new String[10];
                for (int k = 0; k < 10; k++) {
                    news[k] = sp.getString(j + "item" + k, "");
                }
                list.add(new PanBean(sp.getString("panName" + j, ""), news));
            }
            once = true;
        }

        lv1.setAdapter(mLvAdapter);
        editor.putInt("count", ++count);
        editor.commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addpan:
                lv1.setAdapter(mLvAdapter3);
                break;
            //放回按钮
            case R.id.back:
                if (lv1.getAdapter() == mLvAdapter) {
                    getActivity().finish();
                }
                if (lv1.getAdapter() == mLvAdapter2) {
                    closeKeybord(nowEdit, getActivity());
                    lv1.setAdapter(mLvAdapter);
                }
                if (lv1.getAdapter() == mLvAdapter3) {
                    closeKeybord(nowEdit, getActivity());
                    lv1.setAdapter(mLvAdapter);
                    panname.setText("");
                }
                //删除指定转盘
            case R.id.btn1:
                if (lv1.getAdapter() == mLvAdapter2) {
                    if (list.size() == 1) {
                        Toast.makeText(getActivity(), "最后一个转盘不可删除", Toast.LENGTH_SHORT).show();
                    } else {
                        list.remove(mLvAdapter2.panNum);
                        lv1.setAdapter(mLvAdapter);
                        saveMessage();
                        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn2:
                //2开始
                if (lv1.getAdapter() == mLvAdapter2) {
                    for (int j = 0; j < mLvAdapter2.content.length; j++) {
                        if (mLvAdapter2.changes[j]) {
                            Toast.makeText(getActivity(), "存在未保存的挑战", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    saveMessage();
                    PanFragment panFragment = PanFragment.newInstance(list.get(mLvAdapter2.panNum).getItems());
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl1, panFragment).addToBackStack("").commit();
                }
                //3添加转盘
                if (lv1.getAdapter() == mLvAdapter3) {
                    if (!panname.getText().toString().trim().equals("")) {
                        for (int k = 0; k < list.size(); k++) {
                            if (panname.getText().toString().trim().equals(list.get(k).getPanName())) {
                                Toast.makeText(getActivity(), "转盘名称重复", Toast.LENGTH_SHORT).show();
                                panname.setText("");
                                return;
                            }
                        }
                        for (int j = 0; j < mLvAdapter3.content.length; j++) {
                            if (mLvAdapter3.changes[j]) {
                                Toast.makeText(getActivity(), "存在未保存的挑战", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        list.add(new PanBean(panname.getText().toString(), mLvAdapter3.content));
                        panname.setText("");
                        Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
                        saveMessage();
                        lv1.setAdapter(mLvAdapter);
                        return;
                    } else {
                        Toast.makeText(getActivity(), "请输入转盘名称", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    //保存list
    public void saveMessage() {
        editor.putInt("listSize", list.size());
        for (int j = 0; j < list.size(); j++) {
            editor.putString("panName" + j, list.get(j).getPanName());
            for (int k = 0; k < list.get(j).getItems().length; k++) {
                editor.putString(j + "item" + k, list.get(j).getItems()[k]);
            }
        }
        Log.d("TAG", "saveMessage: " + 1);
        editor.commit();
    }

    //所有转盘信息
    class lvAdapter extends BaseAdapter {
        private String titleName = "转盘选择";

        @Override
        public int getCount() {
            return list != null ? list.size() : 0;
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
            view = null;
            ViewHolder holder = null;
            if (view == null) {
                view = View.inflate(getActivity(), R.layout.lh_adapter_list, null);
                holder = new ViewHolder(view);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            title.setText(titleName);
            btn1.setVisibility(View.INVISIBLE);
            btn2.setVisibility(View.INVISIBLE);
            addpan.setVisibility(View.VISIBLE);
            panname.setVisibility(View.INVISIBLE);
            holder.tv2.setText(list.get(i).getPanName());
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mLvAdapter2.getPanNum(i);
                    lv1.setAdapter(mLvAdapter2);
                }
            });
            return view;
        }


    }

    //指定转盘内容
    class lvAdapter2 extends BaseAdapter {
        private String titleName = "转盘内容设置";
        private int panNum;
        private String content[] = new String[10];
        private boolean[] changes = new boolean[10];

        private void getPanNum(int panNum) {
            this.panNum = panNum;
        }

        @Override
        public int getCount() {
            return list.get(panNum).getItems().length;
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
            view = null;
            ViewHolder holder = null;
            if (view == null) {
                view = View.inflate(getActivity(), R.layout.lh_adapter_list, null);
                holder = new ViewHolder(view);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            title.setText(titleName);
            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            addpan.setVisibility(View.GONE);
            btn1.setText("删除转盘");
            btn2.setText("开始");
            holder.tv1.setText("挑战内容");
            content[i] = list.get(panNum).getItems()[i];
            holder.tv2.setText(list.get(panNum).getItems()[i]);
            holder.btn.setVisibility(View.VISIBLE);
            final ViewHolder finalHolder = holder;
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalHolder.btn.getText().toString().equals("改")) {
                        for (int j = 0; j < mLvAdapter2.content.length; j++) {
                            if (mLvAdapter2.changes[j]) {
                                Toast.makeText(getActivity(), "请先保存先前要修改的挑战内容", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        finalHolder.tv2.setVisibility(View.INVISIBLE);
                        finalHolder.ed1.setVisibility(View.VISIBLE);
                        finalHolder.ed1.setText(finalHolder.tv2.getText().toString());
                        showSoftInputFromWindow(getActivity(), finalHolder.ed1);
                        finalHolder.btn.setText("存");
                        finalHolder.btn.setBackgroundResource(R.drawable.lh_bg_btn3);
                        changes[i] = true;
                    } else {
                        if (finalHolder.ed1.getText().toString().trim().equals("")) {
                            Toast.makeText(getActivity(), "挑战内容不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        finalHolder.tv2.setText(finalHolder.ed1.getText().toString());
                        content[i] = finalHolder.ed1.getText().toString();
                        list.get(panNum).setItems(content);
                        finalHolder.tv2.setVisibility(View.VISIBLE);
                        finalHolder.ed1.setVisibility(View.INVISIBLE);
                        finalHolder.btn.setText("改");
                        finalHolder.btn.setBackgroundResource(R.drawable.lh_bg_btn2);
                        changes[i] = false;
                        saveMessage();
                    }

                }
            });
            setListViewRequestFocus(finalHolder.rootView, finalHolder.ed1, finalHolder.btn);

            return view;
        }


    }

    //添加新转盘
    class lvAdapter3 extends BaseAdapter {
        private String titleName = "转盘内容设置";
        private String[] defaultPan = new String[]{"真心话", "大冒险", "再来一次", "喝杯酒", "亲一口", "唱首歌", "跳支舞", "真心话大冒险", "大冒险", "真心话"};
        private String[] content = new String[10];
        private boolean[] changes = new boolean[10];

        @Override
        public int getCount() {
            return defaultPan.length;
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
            view = null;
            ViewHolder holder = null;
            if (view == null) {
                view = View.inflate(getActivity(), R.layout.lh_adapter_list, null);
                holder = new ViewHolder(view);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            title.setText(titleName);
            btn1.setVisibility(View.INVISIBLE);
            btn2.setVisibility(View.VISIBLE);
            addpan.setVisibility(View.GONE);
            panname.setVisibility(View.VISIBLE);
            btn2.setText("保存");
            holder.tv1.setText("挑战内容");
            holder.tv2.setText(defaultPan[i]);
            content[i] = defaultPan[i];
            holder.btn.setVisibility(View.VISIBLE);
            final ViewHolder finalHolder = holder;
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalHolder.btn.getText().toString().equals("改")) {
                        for (int j = 0; j < mLvAdapter3.content.length; j++) {
                            if (mLvAdapter3.changes[j]) {
                                Toast.makeText(getActivity(), "请先保存先前要修改的挑战内容", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        finalHolder.tv2.setVisibility(View.INVISIBLE);
                        finalHolder.ed1.setVisibility(View.VISIBLE);
                        finalHolder.ed1.setText(finalHolder.tv2.getText().toString());
                        showSoftInputFromWindow(getActivity(), finalHolder.ed1);
                        finalHolder.btn.setText("存");
                        finalHolder.btn.setBackgroundResource(R.drawable.lh_bg_btn3);
                        changes[i] = true;
                    } else {
                        if (finalHolder.ed1.getText().toString().trim().equals("")) {
                            Toast.makeText(getActivity(), "挑战内容不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        finalHolder.tv2.setText(finalHolder.ed1.getText().toString());
                        content[i] = finalHolder.ed1.getText().toString();
                        finalHolder.tv2.setVisibility(View.VISIBLE);
                        finalHolder.ed1.setVisibility(View.INVISIBLE);
                        finalHolder.btn.setText("改");
                        finalHolder.btn.setBackgroundResource(R.drawable.lh_bg_btn2);
                        changes[i] = false;
                    }
                }
            });
            setListViewRequestFocus(finalHolder.rootView, finalHolder.ed1, finalHolder.btn);

            return view;
        }
    }
    public class ViewHolder {
        public View rootView;
        public TextView tv1;
        public TextView tv2;
        public EditText ed1;
        public Button btn;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv1 = (TextView) rootView.findViewById(R.id.tv1);
            this.tv2 = (TextView) rootView.findViewById(R.id.tv2);
            this.ed1 = (EditText) rootView.findViewById(R.id.ed1);
            this.btn = (Button) rootView.findViewById(R.id.btn);
        }
    }
    //listview聚焦edit
    private void setListViewRequestFocus(View view, final EditText editText, final Button button) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().toString().equals("存")) {
                    showSoftInputFromWindow(getActivity(), editText);
                }
            }
        });
    }

    //弹出软键盘
    public void showSoftInputFromWindow(Activity activity, EditText mEditText) {
        nowEdit = mEditText;
        mEditText.requestFocus();
        mEditText.setFocusable(true);
            mEditText.setFocusableInTouchMode(true);
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    //关闭软键盘
    public void closeKeybord(EditText mEditText, Context mContext) {
        if (mEditText != null) {
            InputMethodManager imm = (InputMethodManager) mContext
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        }
    }
}
