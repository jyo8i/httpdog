package DAO;

import MODEL.ResponseCodeList;
import MODEL.SavedList;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class ListDAO {

	public boolean saveList(int userId, String listName, Date date, ArrayList<ResponseCodeList> searchResults) {
	    try (Connection conn = DBConnection.getConnection()) {
	        conn.setAutoCommit(false);

	        // Use consistent table name (either 'saved_lists' or 'lists')
	        String insertList = "INSERT INTO lists(user_id, name, created_at) VALUES (?, ?, ?)";
	        PreparedStatement ps = conn.prepareStatement(insertList, Statement.RETURN_GENERATED_KEYS);
	        ps.setInt(1, userId);
	        ps.setString(2, listName);
	        ps.setTimestamp(3, new Timestamp(date.getTime()));
	        int rowsAffected = ps.executeUpdate();

	        if (rowsAffected == 0) {
	            conn.rollback();
	            return false;
	        }

	        ResultSet rs = ps.getGeneratedKeys();
	        int listId = -1;
	        if (rs.next()) {
	            listId = rs.getInt(1);
	        } else {
	            conn.rollback();
	            return false;
	        }

	        String insertItems = "INSERT INTO list_items(list_id, code, description, image_url) VALUES (?, ?, ?, ?)";
	        PreparedStatement ps2 = conn.prepareStatement(insertItems);
	        for (ResponseCodeList item : searchResults) {
	            ps2.setInt(1, listId);
	            ps2.setInt(2, item.getCode());
	            ps2.setString(3, item.getDescription());
	            ps2.setString(4, item.getImageUrl());
	            ps2.addBatch();
	        }

	        int[] batchResults = ps2.executeBatch();
	        for (int result : batchResults) {
	            if (result == Statement.EXECUTE_FAILED) {
	                conn.rollback();
	                return false;
	            }
	        }

	        conn.commit();
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public List<SavedList> getListsByUser(int userId) {
	    System.out.println("\nDAO: Starting getListsByUser for user ID: " + userId);
	    List<SavedList> result = new ArrayList<>();
	    
	    try (Connection conn = DBConnection.getConnection()) {
	        System.out.println("DAO: Database connection established");
	        
	        String sql = "SELECT id, name, created_at, user_id FROM lists WHERE user_id = ? ORDER BY created_at DESC";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, userId);
	        
	        System.out.println("DAO: Executing query: " + ps.toString());
	        ResultSet rs = ps.executeQuery();
	        
	        int count = 0;
	        while (rs.next()) {
	            SavedList list = new SavedList();
	            list.setId(rs.getInt("id"));
	            list.setName(rs.getString("name"));
	            list.setCreatedAt(rs.getTimestamp("created_at"));
	            list.setUserId(rs.getInt("user_id"));
	            result.add(list);
	            count++;
	            
	            System.out.println("DAO: Found list - ID: " + list.getId() + ", Name: " + list.getName());
	        }
	        System.out.println("DAO: Total lists found: " + count);
	    } catch (SQLException e) {
	        System.err.println("DAO ERROR: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return result;
	}

    public boolean updateList(int listId, String listName, ArrayList<ResponseCodeList> updatedList) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            String updateName = "UPDATE lists SET name=? WHERE id=?";
            PreparedStatement ps1 = conn.prepareStatement(updateName);
            ps1.setString(1, listName);
            ps1.setInt(2, listId);
            ps1.executeUpdate();

            String deleteOldItems = "DELETE FROM list_items WHERE list_id=?";
            PreparedStatement ps2 = conn.prepareStatement(deleteOldItems);
            ps2.setInt(1, listId);
            ps2.executeUpdate();

            String insertItems = "INSERT INTO list_items(list_id, code, description, image_url) VALUES (?, ?, ?, ?)";
            PreparedStatement ps3 = conn.prepareStatement(insertItems);
            for (ResponseCodeList item : updatedList) {
                ps3.setInt(1, listId);
                ps3.setInt(2, item.getCode());
                ps3.setString(3, item.getDescription());
                ps3.setString(4, item.getImageUrl());
                ps3.addBatch();
            }

            ps3.executeBatch();
            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
