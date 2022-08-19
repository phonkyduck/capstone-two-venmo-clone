package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.UserAccount;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;

public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public UserAccount getAccount(int id) {
        String sql = "SELECT * FROM accounts WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        UserAccount user = mapRowToUser(results);
        return user;
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
        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        BigDecimal toUser = jdbcTemplate.queryForObject(sql, BigDecimal.class,  to);
        BigDecimal fromUser = jdbcTemplate.queryForObject(sql, BigDecimal.class, from);
        String sqlUpdate = "BEGIN TRANSACTION; "
                + "UPDATE account SET balance = ? " +
                "WHERE user_id = ?; " +
                "UPDATE account SET balance = ? " +
                "WHERE user_id = ?; " +
                "COMMIT;";

        jdbcTemplate.update(sql, toUser.add(amount), to, fromUser.subtract(amount), from);
        return fromUser;
    }
    private UserAccount mapRowToUser(SqlRowSet rs) {
        UserAccount user = new UserAccount();;
        user.setAccountId(rs.getInt("account_id"));
        user.setUser(rs.getInt("user_id"));
        user.setBalance(rs.getBigDecimal("balance"));

        return user;
    }
}
