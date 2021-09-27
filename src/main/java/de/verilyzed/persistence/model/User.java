package de.verilyzed.persistence.model;

import org.json.simple.JSONObject;

public class User {
    private String uuid;
    private String username;
    private int money;
    private JSONObject backpack;
    public User(String name, String uuid, int money, JSONObject backpack) {
        this.username = name;
        this.uuid = uuid;
        this.money = money;
        this.backpack = backpack;
    }
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public JSONObject getBackpack() {
        return backpack;
    }

    public void setBackpack(JSONObject backpack) {
        this.backpack = backpack;
    }
}
