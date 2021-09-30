package de.verilyzed.persistence.model;
import co.aikar.idb.DbRow;

import java.util.UUID;

public class Bounty {
    UUID uuid;
    int money;

    public Bounty(UUID uuid, int money) {
        this.uuid = uuid;
        this.money = money;
    }

    public Bounty(DbRow firstRow) {
        this.uuid = UUID.fromString(firstRow.getString("uuid"));
        this.money = firstRow.getInt("money");
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getMoney() {
        return money;
    }
}
