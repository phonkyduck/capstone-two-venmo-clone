package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserAccount;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;

public class JdbcAccountDao implements AccountDao {

    @Override
    public UserAccount getAccount(int id) {

    }

    @Override
    public BigDecimal addTE() {
        return null;
    }

    @Override
    public BigDecimal transferTE() {
        return null;
    }
    private UserAccount mapRowToUser(SqlRowSet rs) {
        UserAccount user = new UserAccount();
        user.setAccountId(rs.getInt("account_id"));
        user.setUser(rs.getInt("user_id"));

        return user;
    }
}
