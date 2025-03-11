import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseAdapter {
	private static final Random RANDOM = new Random();
	
    public static Connection connect(String url) {
        Connection connection = null;
        Statement statement;
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            String createTableSql = "CREATE TABLE users (" +
            	    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            	    "unique_id INTEGER NOT NULL UNIQUE," +
            	    "username TEXT NOT NULL UNIQUE," +
            	    "password TEXT NOT NULL," +
            	    "currency TEXT NOT NULL," +
            	    "balance INTEGER DEFAULT 0," +
            	    "user_type TEXT NOT NULL CHECK (user_type IN ('admin', 'user')));";
        	statement.executeUpdate(createTableSql);
            statement.close();
        } catch (SQLException e) {
            String s = e.getMessage();
            if (!s.contains("table card already exists")) {
                System.err.println(s);
            }
        }
        return connection;
        
    }

    public static void addUser(Connection connection, String username, String password, String currency, String userType) {
        int uniqueId = RANDOM.nextInt(89999) + 10000; // Generate a unique identifier of 5 digits
        String insertUserSql = "INSERT INTO users (unique_id, username, password, currency, balance, user_type) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertUserSql)) {
            pstmt.setInt(1, uniqueId);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, currency);
            pstmt.setInt(5, 0);
            pstmt.setString(6, userType);
            
            pstmt.executeUpdate();

            User user = new User(uniqueId, username, password, currency, 0, userType);

            System.out.println("Added user: " + user);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    public static void deleteUser(Connection connection, String username) {
        String deleteUserSql = "DELETE FROM users WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(deleteUserSql)) {
            pstmt.setString(1, username);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static List<User> getAllUsers(Connection connection) {
        List<User> users = new ArrayList<>();
        
        String selectAllUsersSql = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectAllUsersSql)) {
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("currency"),
                        rs.getInt("balance"),
                        rs.getString("user_type")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


        return users;
    }
    
}
    
