package de.verilyzed.persistence.model;

import co.aikar.idb.DbRow;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class User {
    private String uuid;
    private String username;
    private int money;
    private JSONArray backpack;
    public User(@NotNull String name, @NotNull String uuid, int money, JSONArray backpack) {
        this.username = name;
        this.uuid = uuid;
        this.money = money;
        this.backpack = backpack;
    }
    public User(@NotNull String name, @NotNull String uuid) {
        this.username = name;
        this.uuid = uuid;
        this.money = 100;
        this.backpack = new JSONArray();
    }

    public User(DbRow firstRow) {
        this.username = firstRow.getString("name");
        this.uuid = firstRow.getString("uuid");
        this.money = firstRow.getInt("money");
        try {
            JSONParser parser = new JSONParser();
            this.backpack = new JSONArray();
            backpack.add(parser.parse(firstRow.getString("backpack")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    public JSONArray getBackpack() {
        return backpack;
    }

    public void setBackpack(JSONArray backpack) {
        this.backpack = backpack;
    }
}
