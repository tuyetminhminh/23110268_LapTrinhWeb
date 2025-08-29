package ltw.service;

import ltw.models.User;

public interface UserService {
	User login(String username, String password);

	void insert(User user);

	boolean register(String username, String password, String email, String fullname, String phone);

	boolean checkExistEmail(String email);

	boolean checkExistUsername(String username);

	boolean checkExistPhone(String phone);

	boolean sendOTP(String email);

	boolean resetPassword(String email, String otp, String newPassword);

	User findByUserName(String username);
}
