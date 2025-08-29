package ltw.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ltw.util.DBConnection;
import ltw.dao.CategoryDao;
import ltw.models.Category;

public class CategoryDaoImpl extends DBConnection implements CategoryDao{

	@Override
	public void insert(Category c) {
		String sql = "INSERT INTO Category(cate_name, icons, user_id) VALUES (?,?,?)";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, c.getCateName());
			ps.setString(2, c.getIcons());
			ps.setInt(3, c.getUserId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void edit(Category c) {
		String sql = "UPDATE Category SET cate_name=?, icons=? WHERE cate_id=? AND user_id=?";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, c.getCateName());
			ps.setString(2, c.getIcons());
			ps.setInt(3, c.getCateId());
			ps.setInt(4, c.getUserId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int cateId, int userId) {
		String sql = "DELETE FROM Category WHERE cate_id=? AND user_id=?";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, cateId);
			ps.setInt(2, userId);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Category get(int cateId, int userId) {
		String sql = "SELECT cate_id, cate_name, icons, user_id FROM Category WHERE cate_id=? AND user_id=?";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, cateId);
			ps.setInt(2, userId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Category c = new Category();
					c.setCateId(rs.getInt("cate_id"));
					c.setCateName(rs.getString("cate_name"));
					c.setIcons(rs.getString("icons"));
					c.setUserId(rs.getInt("user_id"));
					return c;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Category> getAllByUser(int userId) {
		String sql = "SELECT cate_id, cate_name, icons, user_id FROM Category WHERE user_id=? ORDER BY cate_id DESC";
		List<Category> list = new ArrayList<>();
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Category c = new Category();
					c.setCateId(rs.getInt("cate_id"));
					c.setCateName(rs.getString("cate_name"));
					c.setIcons(rs.getString("icons"));
					c.setUserId(rs.getInt("user_id"));
					list.add(c);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Category> searchByName(int userId, String keyword) {
		String sql = "SELECT cate_id, cate_name, icons, user_id FROM Category "
				+ "WHERE user_id=? AND cate_name LIKE ?";
		List<Category> list = new ArrayList<>();
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ps.setString(2, "%" + keyword + "%");
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Category c = new Category();
					c.setCateId(rs.getInt("cate_id"));
					c.setCateName(rs.getString("cate_name"));
					c.setIcons(rs.getString("icons"));
					c.setUserId(rs.getInt("user_id"));
					list.add(c);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
