package com.example.administrator.game_mission;

import java.util.List;

public class AllBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 53
         * content : 你的初吻是几岁在什么地方被什么人夺去的?
         * levels : 普通
         * types : 1
         */

        private int id;
        private String content;
        private String levels;
        private int types;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLevels() {
            return levels;
        }

        public void setLevels(String levels) {
            this.levels = levels;
        }

        public int getTypes() {
            return types;
        }

        public void setTypes(int types) {
            this.types = types;
        }
    }
}
