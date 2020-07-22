package com.example.administrator.game_mission.More.lh;

public class PanBean {
    private  String panName;
    private  String[] items;

    public PanBean(String panName, String[] items) {
        this.panName = panName;
        this.items = items;
    }

    public String getPanName() {
        return panName;
    }

    public void setPanName(String panName) {
        this.panName = panName;
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }
}
