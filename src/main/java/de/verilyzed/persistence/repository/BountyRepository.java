package de.verilyzed.persistence.repository;

import co.aikar.idb.DB;
import de.verilyzed.persistence.model.Bounty;

import java.sql.SQLException;
import java.util.UUID;

public class BountyRepository {

    public int getMoneyFromBounty(UUID uuid) {
        int ret = -1;
        try {
            ret = DB.getFirstColumn("SELECT money FROM bounty WHERE uuid= ?", uuid.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public Bounty getBounty(UUID uuid) {
        Bounty bounty = null;
        try {
            bounty = new Bounty(DB.getFirstRow("SELECT * FROM bounty WHERE uuid=?", uuid.toString()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bounty;
    }
    public boolean insertBounty(Bounty bounty) {
        DB.createTransactionAsync(stm -> {
            if (!stm.inTransaction()) {
                throw new IllegalStateException("Currency Operations require a transaction");
            }
            try {
                return stm.executeUpdateQuery("INSERT INTO bounty (uuid, money) " +
                        "VALUES (?, ?)", bounty.getUuid(), bounty.getMoney()) > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        });
        return true;
    }

    public boolean updateMoneyForBounty(int money, UUID uuid) {
        DB.executeUpdateAsync("UPDATE bounty SET money = ? WHERE name = ?", money, uuid.toString());
        return false;
    }

    public boolean bountyExists(UUID uuid) {
        try {
            return ((long)DB.getFirstColumn("SELECT COUNT(*) FROM bounty WHERE uuid = ?", uuid.toString()))==1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeBounty(String name) {
        DB.executeUpdateAsync("DELETE FROM bounty WHERE name = ?", name);
        return false;
    }
}
