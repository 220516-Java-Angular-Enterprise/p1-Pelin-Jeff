package com.revature.ers.daos;

import com.revature.ers.models.Reimbursement;
import com.revature.ers.util.custom_exceptions.InvalidSQLException;
import com.revature.ers.util.database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDAO implements CrudDAO<Reimbursement> {
    @Override
    public void save(Reimbursement obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO reimbursements(reimb_id, amount, submitted, resolved, description, receipt," +
                                                     " payment_id, author_id, resolver_id, status_id, type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, obj.getId());
            ps.setInt(2, obj.getAmount());
            ps.setDate(3, (Date) obj.getSubmitted());
            ps.setDate(4, (Date) obj.getResolved());
            ps.setString(5, obj.getDescription());
            ps.setByte(6, obj.getReceipt());
            ps.setString(7, obj.getPaymentID());
            ps.setString(8, obj.getAuthorID());
            ps.setString(9, obj.getResolverID());
            ps.setString(10, obj.getStatusID());
            ps.setString(11, obj.getTypeID());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred when tyring to save to the database.");
        }
        
    }

    @Override
    public void update(Reimbursement obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE reimbursements SET" +
                    "amount = ?, submitted = ?, resolved = ?, description = ?," +
                    "receipt = ?, payment_id = ?, author_id = ?, resolver_id = ?," +
                    "status_id = ?, type_id = ?, WHERE reimb_id = ?");
            ps.setInt(1, obj.getAmount());
            ps.setDate(2, (Date) obj.getSubmitted());
            ps.setDate(3, (Date) obj.getResolved());
            ps.setString(4, obj.getDescription());
            ps.setByte(5, obj.getReceipt());
            ps.setString(6, obj.getPaymentID());
            ps.setString(7, obj.getAuthorID());
            ps.setString(8, obj.getResolverID());
            ps.setString(9, obj.getStatusID());
            ps.setString(10, obj.getTypeID());
            ps.setString(11, obj.getId());
            ps.executeUpdate();

        }   catch (SQLException e) {
            throw new RuntimeException("An error occurred when tyring to update the database.");
        }

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Reimbursement getById(String id) {
        Reimbursement reimbursement = new Reimbursement();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursements WHERE reimb_id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                reimbursement.setId(rs.getString("reimb_id"));
                reimbursement.setAmount(rs.getInt("amount"));
                reimbursement.setSubmitted(rs.getDate("submitted"));
                reimbursement.setResolved(rs.getDate("resolved"));
                reimbursement.setDescription(rs.getString("description"));
                reimbursement.setReceipt(rs.getByte("receipt"));
                reimbursement.setPaymentID(rs.getString("payment_id"));
                reimbursement.setAuthorID(rs.getString("author_id"));
                reimbursement.setResolverID(rs.getString("resolver_id"));
                reimbursement.setStatusID(rs.getString("status_id"));
                reimbursement.setTypeID(rs.getString("type_id"));

            }
        }   catch (SQLException e) {
            throw new RuntimeException("An error occurred during the retrieving process.");
        }
        return reimbursement;
    }

    @Override
    public List<Reimbursement> getAll() {
        return null;
    }
}
