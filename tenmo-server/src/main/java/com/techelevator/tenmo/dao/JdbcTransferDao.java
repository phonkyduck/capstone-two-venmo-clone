package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao implements TransferDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Transfer> findAll(int currentUserId) {
        String sql = "SELECT t.transfer_id, ts.transfer_status_desc, tt.transfer_type_desc, uf.username, ut.username, t.amount " +
                "FROM Transfer t " +
                "JOIN transfer_status ts on ts.transfer_status_id = t.transfer_status_id " +
                "JOIN transfer_type tt on tt.transfer_type_id = t.transfer_type_id " +
                "JOIN account af on af.account_id = t.account_from " +
                "JOIN account at on at.account_id = t.account_to" +
                "JOIN tenmo_user uf on uf.user_id = af.user_id " +
                "JOIN tenmo_user ut on ut.user_id = at.user_id " +
                "WHERE t.account_from = ? OR t.account_to = ?;";

        List<Transfer> list = new ArrayList<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,currentUserId,currentUserId);
        while (results.next()){


        }
        return null;
    }

    @Override
    public List<Transfer> findTransferToUser(int id, int currentUserId) {
        return null;
    }

    @Override
    public List<Transfer> findTransferToUser(String username, int currentUserId) {
        return null;
    }

    @Override
    public List<Transfer> findTransferFromUser(int id, int currentUserId) {
        return null;
    }

    @Override
    public List<Transfer> findTransferFromUser(String username, int currentUserId) {
        return null;
    }

    @Override
    public Transfer findById(int transferId, int currentId) {
        return null;
    }

    private Transfer mapRowToUser(SqlRowSet rs) {
        Transfer transfer = new Transfer();;
        transfer.setAccountId(rs.getInt("account_id"));
        transfer.setToUser (rs.getInt("user_id"));
        transfer.setAmount(rs.getBigDecimal("balance"));

        return user;
    }
}
