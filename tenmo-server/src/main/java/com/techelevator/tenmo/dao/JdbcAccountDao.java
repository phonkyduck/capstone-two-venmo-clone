package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    private JdbcTransferDao transferDao;


    @Override
    public Account getAccount(int id) {
        String sql = "SELECT * FROM account WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        List<Account> user = new ArrayList<>();
        while (results.next()) {
            user.add(mapRowToUser(results));
        }
        for (Account account:user
        ) {
            if (account.getUser() == id){
                return account;
            }
        }
        return null;
    }

    @Override
    public BigDecimal addTE(BigDecimal amount, int id) {
        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class,  id);
        BigDecimal add = amount.add(balance);
        String sqlUpdate = "UPDATE account SET balance = ? WHERE user_id = ?;";
        jdbcTemplate.update(sqlUpdate, add, id);
        return add;
    }

    @Override
    public BigDecimal transferTE(BigDecimal amount, int to, int from) {
        int accountToId = getAccount(to).getAccountId();
        int accountFromId = getAccount(from).getAccountId();
        transferDao.addTransfer(amount,accountToId,accountFromId,2,2);

        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        BigDecimal toUser = jdbcTemplate.queryForObject(sql, BigDecimal.class,  to);
        BigDecimal fromUser = jdbcTemplate.queryForObject(sql, BigDecimal.class, from);
        String sqlUpdate = "BEGIN TRANSACTION; "
                + "UPDATE account SET balance = ? " +
                "WHERE user_id = ?; " +
                "UPDATE account SET balance = ? " +
                "WHERE user_id = ?; " +
                "COMMIT;";


        jdbcTemplate.update(sqlUpdate, toUser.add(amount), to, fromUser.subtract(amount), from);
        return fromUser;
    }
    private Account mapRowToUser(SqlRowSet rs) {
        Account user = new Account();
        user.setAccountId(rs.getInt("account_id"));
        user.setUser(rs.getInt("user_id"));
        user.setBalance(rs.getBigDecimal("balance"));

        return user;
    }
}
