package de.verilyzed.persistence.model;
import co.aikar.idb.DbRow;

import java.util.UUID;

public class Bounty {
    String uuid;
    int money;

    public Bounty(String uuid, int money) {
        this.uuid = uuid;
        this.money = money;
    }

    public Bounty(DbRow firstRow) {
        this.uuid = firstRow.getString("uuid");
        this.money = firstRow.getInt("money");
    }

    public String getUuid() {
        return uuid;
    }

    public int getMoney() {
        return money;
    }
}
