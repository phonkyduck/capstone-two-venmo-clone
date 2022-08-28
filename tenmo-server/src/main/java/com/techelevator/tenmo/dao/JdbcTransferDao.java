package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    private final String sqlStatement = "SELECT t.transfer_id, ts.transfer_status_desc, tt.transfer_type_desc, uf.username, ut.username, t.amount, at.user_id, af.user_id " +
            "FROM transfer t " +
            "JOIN transfer_status ts on ts.transfer_status_id = t.transfer_status_id " +
            "JOIN transfer_type tt on tt.transfer_type_id = t.transfer_type_id " +
            "JOIN account af on af.account_id = t.account_from " +
            "JOIN account at on at.account_id = t.account_to " +
            "JOIN tenmo_user uf on uf.user_id = af.user_id " +
            "JOIN tenmo_user ut on ut.user_id = at.user_id ";



    @Override
    public List<Transfer> findAll(User currentUser) {
        String sql = sqlStatement + "WHERE t.account_from = ? OR t.account_to = ?;";

        List<Transfer> list = new ArrayList<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, currentUser.getId(), currentUser.getId());
        while (results.next()){
            list.add(mapRowToTransfer(results));
        }
        return list;
    }

//    @Override
//    public List<Transfer> findTransferToUser(int id, User currentUser) {
//        SqlRowSet results;
//        if(id != currentUser.getId()) {
//            String sql = sqlStatement + "WHERE at.user_id = ? AND af.user_id = ?";
//            results = jdbcTemplate.queryForRowSet(sql, id, currentUser.getId());
//        } else {
//            String sql = sqlStatement + "WHERE at.user_id = ?";
//            results = jdbcTemplate.queryForRowSet(sql, id);
//        }
//        List<Transfer> list = new ArrayList<>();
//        while(results.next()){
//            list.add(mapRowToTransfer(results));
//        }
//        return list;
//    }
//
//    @Override
//    public List<Transfer> findTransferToUser(String username, User currentUser) {
//        SqlRowSet results;
//        if(username != currentUser.getUsername()) {
//            String sql = sqlStatement + "WHERE ut.username = ? AND af.user_id = ?";
//            results = jdbcTemplate.queryForRowSet(sql, username, currentUser.getId());
//        } else {
//            String sql = sqlStatement + "WHERE ut.username = ?";
//            results = jdbcTemplate.queryForRowSet(sql, username);
//        }
//        List<Transfer> list = new ArrayList<>();
//        while(results.next()){
//            list.add(mapRowToTransfer(results));
//        }
//        return list;
//    }
//
//    @Override
//    public List<Transfer> findTransferFromUser(int id, User currentUser) {
//        SqlRowSet results;
//        if(id != currentUser.getId()) {
//            String sql = sqlStatement + "WHERE af.user_id = ? AND at.user_id = ?";
//            results = jdbcTemplate.queryForRowSet(sql, id, currentUser.getId());
//        } else {
//            String sql = sqlStatement + "WHERE af.user_id = ?";
//            results = jdbcTemplate.queryForRowSet(sql, id);
//        }
//        List<Transfer> list = new ArrayList<>();
//        while(results.next()){
//            list.add(mapRowToTransfer(results));
//        }
//        return list;
//    }
//
//    @Override
//    public List<Transfer> findTransferFromUser(String username, User currentUser) {
//        SqlRowSet results;
//        if(username != currentUser.getUsername()) {
//            String sql = sqlStatement + "WHERE uf.username = ? AND at.user_id = ?";
//            results = jdbcTemplate.queryForRowSet(sql, username, currentUser.getId());
//        } else {
//            String sql = sqlStatement + "WHERE uf.username = ?";
//            results = jdbcTemplate.queryForRowSet(sql, username);
//        }
//        List<Transfer> list = new ArrayList<>();
//        while(results.next()){
//            list.add(mapRowToTransfer(results));
//        }
//        return list;
//    }

    public List<Transfer> findTransfer(String username, User currentUser){
        SqlRowSet results;
        if(username != currentUser.getUsername()) {
            String sql = sqlStatement + "WHERE uf.username = ? AND at.user_id = ? OR " +
                    "ut.username = ? AND af.user_id = ?;";
            results = jdbcTemplate.queryForRowSet(sql, username, currentUser.getId(), username, currentUser.getId());
        } else {
            String sql = sqlStatement + "WHERE uf.username = ? or ut.username = ?;";
            results = jdbcTemplate.queryForRowSet(sql, username);
        }
        List<Transfer> list = new ArrayList<>();
        while(results.next()){
            list.add(mapRowToTransfer(results));
        }
        return list;
    }

    @Override
    public List<Transfer> findTransfer(int id, User currentUser) {
        SqlRowSet results;
        if(id != currentUser.getId()) {
            String sql = sqlStatement + "WHERE af.user_id = ? AND at.user_id = ? OR af.user_id = ? AND at.user_id = ?;";
            results = jdbcTemplate.queryForRowSet(sql, id, currentUser.getId(), currentUser.getId(), id);
        } else {
            String sql = sqlStatement + "WHERE af.user_id = ? OR at.user_id = ?;";
            results = jdbcTemplate.queryForRowSet(sql, id, id);
        }
        List<Transfer> list = new ArrayList<>();
        while(results.next()){
            list.add(mapRowToTransfer(results));
        }
        return list;
    }

    @Override
    public List<Transfer> findTransfer(String username, User currentUser, int isFrom) {
        SqlRowSet results;
        if(isFrom == 1){
            if(username != currentUser.getUsername()) {
                String sql = sqlStatement + "WHERE uf.username = ? AND at.user_id = ?;";
                results = jdbcTemplate.queryForRowSet(sql, username, currentUser.getId());
            } else {
                String sql = sqlStatement + "WHERE uf.username = ?;";
                results = jdbcTemplate.queryForRowSet(sql, username);
            }
            List<Transfer> list = new ArrayList<>();
            while(results.next()){
                list.add(mapRowToTransfer(results));
            }
            return list;
        }
        else{
            if(username != currentUser.getUsername()) {
                String sql = sqlStatement + "WHERE ut.username = ? AND af.user_id = ?;";
                results = jdbcTemplate.queryForRowSet(sql, username, currentUser.getId());
            } else {
                String sql = sqlStatement + "WHERE ut.username = ?;";
                results = jdbcTemplate.queryForRowSet(sql, username);
            }
            List<Transfer> list = new ArrayList<>();
            while(results.next()){
                list.add(mapRowToTransfer(results));
            }
            return list;
        }
    }

    @Override
    public List<Transfer> findTransfer(int id, User currentUser, int isFrom) {
        SqlRowSet results;
        if(isFrom == 1){
            if(id != currentUser.getId()) {
                String sql = sqlStatement + "WHERE af.user_id = ? AND at.user_id = ?;";
                results = jdbcTemplate.queryForRowSet(sql, id, currentUser.getId());
            } else {
                String sql = sqlStatement + "WHERE af.user_id = ?;";
                results = jdbcTemplate.queryForRowSet(sql, id);
            }
            List<Transfer> list = new ArrayList<>();
            while(results.next()){
                list.add(mapRowToTransfer(results));
            }
            return list;
        }
        else {
            if(id != currentUser.getId()) {
                String sql = sqlStatement + "WHERE at.user_id = ? AND af.user_id = ?;";
                results = jdbcTemplate.queryForRowSet(sql, id, currentUser.getId());
            } else {
                String sql = sqlStatement + "WHERE at.user_id = ?;";
                results = jdbcTemplate.queryForRowSet(sql, id);
            }
            List<Transfer> list = new ArrayList<>();
            while(results.next()){
                list.add(mapRowToTransfer(results));
            }
            return list;
        }
    }

    @Override
    public Transfer findById(int transferId, User currentUser) {
        Transfer transfer = null;
        String sql = sqlStatement + "WHERE t.transfer_id = ? AND at.user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId, currentUser);
        while(results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public Transfer findById(int transferId) {
        Transfer transfer = null;
        String sql = sqlStatement + "WHERE t.transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        while(results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public List<Transfer> findAllAdmin() {
        List<Transfer> list = new ArrayList<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlStatement);
        while (results.next()){
            list.add(mapRowToTransfer(results));
        }
        return list;
    }

    public void addTransfer(BigDecimal amount, int to, int from, int status, int type) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?,?,?,?,?);";
        jdbcTemplate.update(sql,type,status,from,to,amount);
    }

    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setId(rs.getInt("t.transfer_id"));
        transfer.setToUser(rs.getString("ut.username"));
        transfer.setFromUser(rs.getString("uf.username"));
        transfer.setAmount(rs.getBigDecimal("t.amount"));
        transfer.setToId(rs.getInt("at.user_id"));
        transfer.setFromId(rs.getInt("af.user_id"));
        transfer.setType(rs.getString("tt.transfer_type_desc"));
        transfer.setStatus(rs.getString("tt.transfer_status_desc"));
        return transfer;
    }
}
