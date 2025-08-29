package ltw.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ltw.dao.UserDao;
import ltw.models.User;
import ltw.util.DBConnection;

public class UserDaoImpl implements UserDao {

	@Override
	public User findByUserName(String username) {
	    String sql = "SELECT * FROM [User] WHERE username = ?";
	    try (Connection conn = new DBConnection().getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	      ps.setString(1, username);
	      try (ResultSet rs = ps.executeQuery()) {
	        if (rs.next()) {
	          User u = new User();
	          u.setId(rs.getInt("id"));
	          u.setEmail(rs.getString("email"));
	          u.setUserName(rs.getString("username"));
	          u.setFullName(rs.getString("fullname"));
	          u.setPassWord(rs.getString("password")); // mật khẩu đã băm
	          u.setAvatar(rs.getString("avatar"));
	          u.setRoleid(rs.getInt("roleid"));
	          u.setPhone(rs.getString("phone"));
	          u.setCreatedDate(rs.getDate("createDate"));
	          return u;
	        }
	      }
	    } catch (Exception e) { e.printStackTrace(); }
	    return null;
	}

	@Override
	public void insert(User user) {
		String sql = "INSERT INTO [User](email, username, fullname, password, avatar, roleid, phone, createDate) "
				+ "VALUES (?,?,?,?,?,?,?,?)";
		try (Connection conn = new DBConnection().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getUserName());
			ps.setString(3, user.getFullName());
			ps.setString(4, user.getPassWord()); // đã băm
			ps.setString(5, user.getAvatar());
			ps.setInt(6, user.getRoleid());
			ps.setString(7, user.getPhone());
			ps.setDate(8, user.getCreatedDate());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkExistEmail(String email) {
		String sql = "SELECT 1 FROM [User] WHERE email = ?";
		try (Connection conn = new DBConnection().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkExistUsername(String username) {
		String sql = "SELECT 1 FROM [User] WHERE username = ?";
		try (Connection conn = new DBConnection().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, username);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkExistPhone(String phone) {
		String sql = "SELECT 1 FROM [User] WHERE phone = ?";
		try (Connection conn = new DBConnection().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, phone);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void updateOTP(String email, String otp, Timestamp expired) {
		// TODO Auto-generated method stub
		String sql = "UPDATE [User] SET otp=?, otp_expired=? WHERE email=?";
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, otp);
            ps.setTimestamp(2, expired);
            ps.setString(3, email);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public boolean verifyOTP(String email, String otp) {
		String sql = "SELECT 1 FROM [User] WHERE email=? AND otp=? AND otp_expired > GETDATE()";
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, otp);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
	}

	@Override
	public void updatePassword(String email, String newPassword) {
		String sql = "UPDATE [User] SET password=?, otp=NULL, otp_expired=NULL WHERE email=?";
        try (Connection conn = new DBConnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setString(2, email);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	@Override
	public List<User> findAll() {
	  String sql = "SELECT id, username, email, fullname, phone, roleid, avatar, createDate FROM [User] ORDER BY id DESC";
	  List<User> list = new ArrayList<>();
	  try (Connection con = new DBConnection().getConnection();
	       PreparedStatement ps = con.prepareStatement(sql);
	       ResultSet rs = ps.executeQuery()) {
	    while (rs.next()) {
	      User u = new User();
	      u.setId(rs.getInt("id"));
	      u.setUserName(rs.getString("username"));
	      u.setEmail(rs.getString("email"));
	      u.setFullName(rs.getString("fullname"));
	      u.setPhone(rs.getString("phone"));
	      u.setRoleid(rs.getInt("roleid"));
	      u.setAvatar(rs.getString("avatar"));
	      u.setCreatedDate(rs.getDate("createDate"));
	      list.add(u);
	    }
	  } catch (Exception e) { e.printStackTrace(); }
	  return list;
	}

	@Override
	public User findById(int id) {
		String sql = "SELECT * FROM [User] WHERE id = ?";
	    try (Connection con = new DBConnection().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, id);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                User u = new User();
	                u.setId(rs.getInt("id"));
	                u.setUserName(rs.getString("username"));
	                u.setPassWord(rs.getString("password"));
	                u.setEmail(rs.getString("email"));
	                u.setFullName(rs.getString("fullname"));
	                u.setPhone(rs.getString("phone"));
	                u.setRoleid(rs.getInt("roleid"));
	                u.setAvatar(rs.getString("avatar"));
	                u.setCreatedDate(rs.getDate("createDate"));
	                return u;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return null;
	}


}
