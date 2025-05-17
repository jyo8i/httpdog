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
	    List<SavedList> result = new ArrayList<>();
	    String sql = "SELECT id, name, created_at, user_id FROM lists WHERE user_id = ? ORDER BY created_at DESC";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, userId);

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                SavedList list = new SavedList();
	                list.setId(rs.getInt("id"));
	                list.setName(rs.getString("name"));
	                list.setCreatedAt(rs.getTimestamp("created_at"));
	                list.setUserId(rs.getInt("user_id"));
	                result.add(list);
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error in getListsByUser: " + e.getMessage());
	        throw new RuntimeException("Database error", e);
	    }

	    return result;
	}
	public SavedList getListById(int id) {
	    String sql = "SELECT * FROM lists WHERE id = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, id);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                SavedList list = new SavedList();
	                list.setId(rs.getInt("id"));
	                list.setName(rs.getString("name"));
	                list.setCreatedAt(rs.getTimestamp("created_at"));
	                list.setUserId(rs.getInt("user_id"));
	                return list;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public void deleteListById(int id) {
	    String sql = "DELETE FROM lists WHERE id = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, id);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
//	public SavedList getListById(int listId) {
//	    String sql = "SELECT * FROM lists WHERE id = ?";
//	    try (Connection conn = DBConnection.getConnection();
//	         PreparedStatement ps = conn.prepareStatement(sql)) {
//
//	        ps.setInt(1, listId);
//	        try (ResultSet rs = ps.executeQuery()) {
//	            if (rs.next()) {
//	                SavedList list = new SavedList();
//	                list.setId(rs.getInt("id"));
//	                list.setName(rs.getString("name"));
//	                list.setCreatedAt(rs.getTimestamp("created_at"));
//	                list.setUserId(rs.getInt("user_id"));
//	                return list;
//	            }
//	        }
//	    } catch (SQLException e) {
//	        System.err.println("Error in getListById: " + e.getMessage());
//	        e.printStackTrace();
//	        throw new RuntimeException("Database error", e);
//	    }
//	    return null; // Not found
//	}

	public void updateListName(int id, String newName) {
	    String sql = "UPDATE lists SET name = ? WHERE id = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, newName);
	        ps.setInt(2, id);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	public List<ResponseCodeList> getItemsByListId(int listId) {
	    List<ResponseCodeList> items = new ArrayList<>();
	    String sql = "SELECT * FROM list_items WHERE list_id = ?";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, listId);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                ResponseCodeList item = new ResponseCodeList();
	                item.setId(rs.getInt("id"));
	                item.setListId(rs.getInt("list_id"));
	                item.setCode(rs.getInt("code"));
	                item.setDescription(rs.getString("description"));
	                item.setImageUrl(rs.getString("image_url"));
	                items.add(item);
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error in getItemsByListId: " + e.getMessage());
	        e.printStackTrace();
	        throw new RuntimeException("Database error", e);
	    }
	    return items;
	}}
