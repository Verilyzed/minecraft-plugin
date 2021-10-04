package de.verilyzed.persistence.repository;

import co.aikar.idb.DB;
import de.verilyzed.exceptions.MoneyFetchException;
import de.verilyzed.exceptions.UpdateFailedException;
import de.verilyzed.krassalla.KrassAlla;
import de.verilyzed.persistence.model.Bounty;
import de.verilyzed.service.UserService;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class BountyRepository {
    boolean stat;

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

    public void insertBounty(Bounty bounty, @NotNull String name) throws UpdateFailedException {
        DB.createTransactionAsync(stm -> {
                    if (!stm.inTransaction()) {
                        throw new IllegalStateException("Currency Operations require a transaction");
                    }
                    try {
                        stm.executeUpdateQuery("INSERT INTO bounty (uuid, money) " +
                                "VALUES (?, ?)", bounty.getUuid(), bounty.getMoney());
                        int money = 0;
                        try {
                            money = UserService.getMoney(name);
                        } catch (MoneyFetchException e) {
                            e.printStackTrace();
                            return false;
                        }
                        money = money - bounty.getMoney();
                        if (money < 0)
                            return false;
                        stm.executeUpdateQuery("UPDATE users SET money = ? WHERE name =?", money, name);
                        return true;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                }, () -> {
                    KrassAlla.log.log(new LogRecord(Level.INFO, "Bounty inserted."));
                    stat = true;
                },
                () -> {
                    KrassAlla.log.log(new LogRecord(Level.INFO, "Error inserting bounty."));
                    stat = false;
                });
        if (!stat)
            throw new UpdateFailedException("Bounty cannot be inserted");
    }

    public boolean updateMoneyForBounty(int money, UUID uuid) {
        DB.executeUpdateAsync("UPDATE bounty SET money = ? WHERE name = ?", money, uuid.toString());
        return false;
    }

    public boolean bountyExists(UUID uuid) {
        try {
            return ((long) DB.getFirstColumn("SELECT COUNT(*) FROM bounty WHERE uuid = ?", uuid.toString())) == 1;
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
