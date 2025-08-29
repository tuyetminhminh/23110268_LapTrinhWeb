package ltw.dao;

import java.sql.Timestamp;
import java.util.List;

import ltw.models.User;

public interface UserDao {

	User findByUserName(String username);
	List<User> findAll();
	User findById(int id);
	void insert(User user);

	boolean checkExistEmail(String email);

	boolean checkExistUsername(String username);

	boolean checkExistPhone(String phone);

	void updateOTP(String email, String otp, Timestamp expired);

	boolean verifyOTP(String email, String otp);

	void updatePassword(String email, String newPassword);
}
