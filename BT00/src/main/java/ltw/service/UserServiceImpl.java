package ltw.service;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.Random;

import ltw.dao.UserDao;
import ltw.dao.impl.UserDaoImpl;
import ltw.mail.SendMail;
import ltw.models.User;
import ltw.util.PasswordUtil;

public class UserServiceImpl implements UserService {
	private final UserDao userDao = new UserDaoImpl();

	@Override
	public User login(String username, String password) {
		User user = this.findByUserName(username);
		if (user != null && PasswordUtil.verify(password, user.getPassWord())) {
			return user;
		}
		return null;
	}

	@Override
	public boolean register(String username, String password, String email, String fullname, String phone) {

		if (userDao.checkExistUsername(username))
			return false;

		long millis = System.currentTimeMillis();
		Date date = new Date(millis);
		String hashed = PasswordUtil.hash(password);

		User u = new User();
		u.setEmail(email);
		u.setUserName(username);
		u.setFullName(fullname);
		u.setPassWord(hashed);
		u.setAvatar(null);
		u.setRoleid(5); 
		u.setPhone(phone);
		u.setCreatedDate(date);
		userDao.insert(u);
		return true;
	}
	@Override
	public boolean sendOTP(String email) {
		if (!userDao.checkExistEmail(email))
			return false;
		String otp = String.valueOf(new Random().nextInt(999999));
		Timestamp expired = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000);
		userDao.updateOTP(email, otp, expired);

		SendMail.send(email, "OTP Reset Password", "Mã OTP của bạn là: " + otp);
		return true;
	}
	@Override
	public boolean resetPassword(String email, String otp, String newPassword) {
		if (userDao.verifyOTP(email, otp)) {
			String hashed = PasswordUtil.hash(newPassword);
			userDao.updatePassword(email, hashed);
			return true;
		}
		return false;
	}

	@Override
	public void insert(User user) {
		userDao.insert(user);
	}

	@Override
	public boolean checkExistEmail(String email) {
		return userDao.checkExistEmail(email);
	}

	@Override
	public boolean checkExistUsername(String username) {
		return userDao.checkExistUsername(username);
	}

	@Override
	public boolean checkExistPhone(String phone) {
		return userDao.checkExistPhone(phone);
	}

	@Override
	public User findByUserName(String username) {
		return userDao.findByUserName(username);
	}

}
