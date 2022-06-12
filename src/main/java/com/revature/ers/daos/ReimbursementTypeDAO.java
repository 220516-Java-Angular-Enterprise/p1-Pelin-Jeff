package com.revature.ers.daos;

import com.revature.ers.models.ReimbursementType;
import com.revature.ers.util.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ReimbursementTypeDAO implements CrudDAO<ReimbursementType>{
    @Override
    public void save(ReimbursementType obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSER INTO reimbursements_types(type_id, type) VALUES (?, ?)");
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getType());
            ps.executeUpdate();

        }   catch (SQLException e) {
            throw new RuntimeException("An error occurred when tyring to save to the database.");
        }
    }

    @Override
    public void update(ReimbursementType obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public ReimbursementType getById(String id) {
        return null;
    }

    @Override
    public List<ReimbursementType> getAll() {
        return null;
    }
}
