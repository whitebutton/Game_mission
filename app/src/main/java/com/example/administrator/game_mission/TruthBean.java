package com.example.administrator.game_mission;

import java.util.List;

public class TruthBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * content : 你的初吻是几岁在什么地方被什么人夺去的?
         * levels : 1
         * defaults : 0
         * types : 1
         */

        private int id;
        private String content;
        private int levels;
        private int defaults;
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

        public int getLevels() {
            return levels;
        }

        public void setLevels(int levels) {
            this.levels = levels;
        }

        public int getDefaults() {
            return defaults;
        }

        public void setDefaults(int defaults) {
            this.defaults = defaults;
        }

        public int getTypes() {
            return types;
        }

        public void setTypes(int types) {
            this.types = types;
        }
    }
}
