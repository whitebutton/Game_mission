package com.example.administrator.game_mission;

public class CodeBean {
    private Integer code;

    @Override
    public String toString() {
        return "CodeBean{" +
                "code=" + code +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
