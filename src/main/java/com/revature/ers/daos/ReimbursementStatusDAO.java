package com.revature.ers.daos;

import com.revature.ers.models.ReimbursementStatus;
import com.revature.ers.util.custom_exceptions.InvalidSQLException;
import com.revature.ers.util.database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementStatusDAO implements CrudDAO<ReimbursementStatus>{
    @Override
    public void save(ReimbursementStatus obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO reimbursements_statuses(status_id, status) VALUES (?, ?)");
                    ps.setString(1, obj.getId());
                    ps.setString(2, obj.getStatus());
                    ps.executeUpdate();
        }   catch (SQLException e) {
            throw new RuntimeException("An error occurred when tyring to save to the database.");
        }
    }

    @Override
    public void update(ReimbursementStatus obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public ReimbursementStatus getById(String id) {
        return null;
    }

    @Override
    public List<ReimbursementStatus> getAll() {
        return null;
    }
}
