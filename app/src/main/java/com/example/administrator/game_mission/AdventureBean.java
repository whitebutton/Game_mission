package com.example.administrator.game_mission;

import java.util.List;

public class AdventureBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * content : 与从你右手边数起第三位异性十指相扣握住直到下一轮真心话大冒险结束
         * levels : 1
         * defaults : 0
         * types : 0
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
