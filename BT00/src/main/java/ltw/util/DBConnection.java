package ltw.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBConnection {
	private final String serverName = "LAPTOP-DEJAVU";
	private final String dbName = "LapTrinhWeb";
	private final String portNumber = "1433";
	private final String instance = "";

	public Connection getConnection() throws Exception {
		String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName
				+ ";integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
		
		if (instance != null && !instance.trim().isEmpty()) {
            url = "jdbc:sqlserver://" + serverName + "\\" + instance + ":" + portNumber + 
                  ";databaseName=" + dbName + ";integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
		}
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		return DriverManager.getConnection(url);
	}

	public static void main(String[] args) {

		try {

			Connection conn = new DBConnection().getConnection();

			Statement stmt = conn.createStatement();

			long millis = System.currentTimeMillis();
	        Date date = new Date(millis);
	        String plainPassword = "123";  // Password gốc
	        String hashedPassword = PasswordUtil.hash(plainPassword);  // Hash password bằng BCrypt
	        stmt.executeUpdate("INSERT INTO [User](username, password, email, fullname, phone, roleid, createDate, avatar) " 
	            + "VALUES ('user1', '" + hashedPassword + "', 'user1@example.com', 'User One', '0123456789', 5, '" + date + "', NULL)");
	        System.out.println("Insert thành công! Password hashed: " + hashedPassword);
	        
	        // Kiểm tra dữ liệu
	        ResultSet rs = stmt.executeQuery("SELECT * FROM [User] WHERE username = 'user1'");
	        if (rs.next()) {
	            String dbPassword = rs.getString("password");
	            boolean verified = PasswordUtil.verify(plainPassword, dbPassword);
	            System.out.println("Username: " + rs.getString("username") + ", Password match: " + verified + ", Hashed DB Password: " + dbPassword);
	        } else {
	            System.out.println("Không tìm thấy user!");
	        }

			conn.close(); // close connection
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
