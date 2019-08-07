package lovebird.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import lovebird.accounts.MySQLServer;

public class Utilities {

	public Utilities() {
		
	}
	
	
	/**
	 * Checks if both passwords provided by the user match
	 * 
	 * @return true if passwords are equal and false if they are not equal.
	 */ 
	public static String checkPasswordMatch(String password1, String password2) {
		
		if(password1.isEmpty() || password2.isEmpty())
			return "";
		
		if(!(password1.equals(password2)))
			return "Passwords do not match\n";
		else 
			return "";
		
	}
	
	public static ArrayList<String> checkEmptyInputs(String...inputValues) {
		
		ArrayList<String> errorMessages = new ArrayList<>();
		
		try { 
		
			for(int i = 0; i < inputValues.length; i += 2)
				if(inputValues[i].isEmpty()) 
					errorMessages.add("Invalid input value for " + inputValues[i + 1]);
				

		} catch(IndexOutOfBoundsException ex) {
			
			// Catch exception
			
		}
		
		return errorMessages;
		
	}
	
	
	/** 
	 * 	Hashes given {@code String} using SHA-256 (Usually a password)
	 * 
	 * @param input
	 * 		  {@code String} that wants to be hashed.
	 * 
	 * @return hashed {@code String}.
	 */
	public static String getSHA(String input) { 
		  
        try { 
  
            // Static getInstance method is called with hashing SHA 
            MessageDigest md = MessageDigest.getInstance("SHA-256"); 
  
            // digest() method called 
            // to calculate message digest of an input 
            // and return array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
  
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
  
            return hashtext; 
        } 
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            System.out.println("Exception thrown"
                               + " for incorrect algorithm: " + e); 
  
            return null; 
            
        } 
    } 
	
	
	
	public static String takenUsername(String username) {
		
		Connection c = null;
		
		try {
			String url = "jdbc:mysql://localhost:3306/cs3220stu02?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			String db_username = MySQLServer.USERNAME;
			String db_password = MySQLServer.PASSWORD;

			// Make a connection to the database
			c = DriverManager.getConnection(url, db_username, db_password);
			
			String findUsernameQuery = "SELECT username FROM love_bird_accounts WHERE username = ?;";
			PreparedStatement findUsernameQueryStatement = c.prepareStatement(findUsernameQuery);
			
			findUsernameQueryStatement.setString(1, username);
			
			ResultSet result = findUsernameQueryStatement.executeQuery();
			
			if(result.next()) {
				
				c.close();
				
				return "Username is already taken";
				
			}

			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return "";
		
	}
	
	public static void insertNewUserToDB(String username, String password, String firstName, String lastName, String email, String age, String city, String state, String gender, String bio, String preference) {
		
		Connection c = null;
		
		try {
			String url = "jdbc:mysql://localhost:3306/cs3220stu02?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			String db_username = MySQLServer.USERNAME;
			String db_password = MySQLServer.PASSWORD;

			// Make a connection to the database
			c = DriverManager.getConnection(url, db_username, db_password);
			
			String insertNewUser = "INSERT INTO love_bird_accounts (username, hashed_pass, first_name, last_name, email, age, city, state, gender, bio, preference) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement insertNewUserStatement = c.prepareStatement(insertNewUser);
			
			insertNewUserStatement.setString(1, username);
			insertNewUserStatement.setString(2, Utilities.getSHA(password));
			insertNewUserStatement.setString(3, firstName);
			insertNewUserStatement.setString(4, lastName);
			insertNewUserStatement.setString(5, email);
			insertNewUserStatement.setInt(6, Integer.parseInt(age));
			insertNewUserStatement.setString(7, city);
			insertNewUserStatement.setString(8, state);
			insertNewUserStatement.setString(9, gender);
			insertNewUserStatement.setString(10, bio);
			insertNewUserStatement.setString(11, preference);
			
			insertNewUserStatement.executeUpdate();
			
			Utilities.insertNewImageRecord(Utilities.getUserID(username));
			
			c.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	public static String validUserCredentials(String username, String password) {
		
		Connection c = null;
		
		try {
			String url = "jdbc:mysql://localhost:3306/cs3220stu02?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			String db_username = MySQLServer.USERNAME;
			String db_password = MySQLServer.PASSWORD;

			// Make a connection to the database
			c = DriverManager.getConnection(url, db_username, db_password);
			
			String findUserQuery = "SELECT username FROM love_bird_accounts WHERE username = ? AND hashed_pass = ?";
			PreparedStatement findUserQueryStatement = c.prepareStatement(findUserQuery);
			
			findUserQueryStatement.setString(1, username);
			findUserQueryStatement.setString(2, Utilities.getSHA(password));
			
			ResultSet result = findUserQueryStatement.executeQuery();
			
			if(result.next()) {
				
				c.close();
				
				return "";
				
			}
			
			c.close();

			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return "Username or password is incorrect";
		
	}
	
	public static String getUserID(String username) {
		
		String userInfo = "";
		
		System.out.println(username);
		
		Connection c = null;
		
		try {
			String url = "jdbc:mysql://localhost:3306/cs3220stu02?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			String db_username = MySQLServer.USERNAME;
			String db_password = MySQLServer.PASSWORD;

			// Make a connection to the database
			c = DriverManager.getConnection(url, db_username, db_password);
			
			String findUserInfoQuery = "SELECT user_id FROM love_bird_accounts WHERE username = ?;";
			PreparedStatement findUserInfoQueryStatement = c.prepareStatement(findUserInfoQuery);
			
			findUserInfoQueryStatement.setString(1, username);
			//findUserInfoQueryStatement.setString(2, username);
			
			ResultSet result = findUserInfoQueryStatement.executeQuery();
			
			result.next();
			
			userInfo = result.getString("user_id");
			System.out.println(userInfo);
		
				c.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return userInfo;
		
	}
	
	public static void saveUserInfoToSession(HttpSession session, String username) {
		
		Connection c = null;
		
		try {
			String url = "jdbc:mysql://localhost:3306/cs3220stu02?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			String db_username = MySQLServer.USERNAME;
			String db_password = MySQLServer.PASSWORD;

			// Make a connection to the database
			c = DriverManager.getConnection(url, db_username, db_password);
			
			String findUserInfoQuery = "SELECT first_name, last_name, email, age, city, state, gender, bio, preference FROM love_bird_accounts WHERE username = ?;";
			PreparedStatement findUserInfoQueryStatement = c.prepareStatement(findUserInfoQuery);
			
			findUserInfoQueryStatement.setString(1, username);
			
			ResultSet result = findUserInfoQueryStatement.executeQuery();
			
			if(result.next()) {
				
				session.setAttribute("firstName", result.getString("first_name")); 
				session.setAttribute("lastName", result.getString("last_name")); 
				session.setAttribute("email", result.getString("email")); 
				session.setAttribute("age", result.getString("age")); 
				session.setAttribute("city", result.getString("city")); 
				session.setAttribute("state", result.getString("state")); 
				session.setAttribute("gender", result.getString("gender")); 
				session.setAttribute("bio", result.getString("bio"));
				session.setAttribute("preference", result.getString("preference"));
				
			}

			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
	}
	
	public static void saveOtherUserInfoToApplicationScope(HttpSession session, int user_id) {
		
		Connection c = null;
		
		try {
			String url = "jdbc:mysql://localhost:3306/cs3220stu02?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			String db_username = MySQLServer.USERNAME;
			String db_password = MySQLServer.PASSWORD;

			// Make a connection to the database
			c = DriverManager.getConnection(url, db_username, db_password);
			
			String findUserInfoQuery = "SELECT first_name, last_name, email, age, city, state, gender, bio, preference FROM love_bird_accounts WHERE user_id = ?;";
			PreparedStatement findUserInfoQueryStatement = c.prepareStatement(findUserInfoQuery);
			
			findUserInfoQueryStatement.setInt(1, user_id);
			
			ResultSet result = findUserInfoQueryStatement.executeQuery();
			
			if(result.next()) {
				
				session.setAttribute("firstName", result.getString("first_name")); 
				session.setAttribute("lastName", result.getString("last_name")); 
				session.setAttribute("email", result.getString("email")); 
				session.setAttribute("age", result.getString("age")); 
				session.setAttribute("city", result.getString("city")); 
				session.setAttribute("state", result.getString("state")); 
				session.setAttribute("gender", result.getString("gender")); 
				session.setAttribute("bio", result.getString("bio"));
				session.setAttribute("preference", result.getString("preference"));
				
			}

			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
	}
	
	public static ArrayList<String> getImagesID(HttpSession session, int user_id) {
		
		System.out.println("HELLO WORLD");
		
		ArrayList<String> imageIDs = new ArrayList<>(); // parameter of existing imageIDs
		
		Connection c = null;
		
		try {
			String url = "jdbc:mysql://localhost:3306/cs3220stu02?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			String db_username = MySQLServer.USERNAME;
			String db_password = MySQLServer.PASSWORD;

			// Make a connection to the database
			c = DriverManager.getConnection(url, db_username, db_password);
			
			String imagesQuery = "SELECT image_1, image_2, image_3, image_4, image_5, image_6 FROM love_bird_images WHERE user_id = ?;";
			PreparedStatement imagesQueryStatement = c.prepareStatement(imagesQuery);
			
			imagesQueryStatement.setInt(1, user_id);
			
			ResultSet result = imagesQueryStatement.executeQuery();
			
			if(result.next()) {
				
				if(result.getString("image_1") != null)
					imageIDs.add("imageID=1");
				if(result.getString("image_2") != null)
					imageIDs.add("imageID=2");
				if(result.getString("image_3") != null)
					imageIDs.add("imageID=3");
				if(result.getString("image_4") != null)
					imageIDs.add("imageID=4");
				if(result.getString("image_5") != null)
					imageIDs.add("imageID=5");
				if(result.getString("image_6") != null)
					imageIDs.add("imageID=6");
				
			}
			
			c.close();

			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return imageIDs;
				
	}
	
	public static String getImageName(int image_id, int user_id) {
		
		String image_name = "";
		
		Connection c = null;
		
		try {
			String url = "jdbc:mysql://localhost:3306/cs3220stu02?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			String db_username = MySQLServer.USERNAME;
			String db_password = MySQLServer.PASSWORD;

			// Make a connection to the database
			c = DriverManager.getConnection(url, db_username, db_password);
			
			String imageNameQuery;
			
			if(image_id == 1) {
				
				imageNameQuery = "SELECT image_1 FROM love_bird_images WHERE user_id = ?;";
				PreparedStatement imageNameQueryStatement = c.prepareStatement(imageNameQuery);
				
				imageNameQueryStatement.setInt(1, user_id);
				
				ResultSet result = imageNameQueryStatement.executeQuery();
				
				if(result.next())
					image_name = result.getString(1);
			
			} else if(image_id == 2) {
				
				imageNameQuery = "SELECT image_2 FROM love_bird_images WHERE user_id = ?;";
				PreparedStatement imageNameQueryStatement = c.prepareStatement(imageNameQuery);
				
				imageNameQueryStatement.setInt(1, user_id);
				
				ResultSet result = imageNameQueryStatement.executeQuery();
				
				if(result.next())
					image_name = result.getString(1);
			
			} else if(image_id == 3) {
				
				imageNameQuery = "SELECT image_3 FROM love_bird_images WHERE user_id = ?;";
				PreparedStatement imageNameQueryStatement = c.prepareStatement(imageNameQuery);
				
				imageNameQueryStatement.setInt(1, user_id);
				
				ResultSet result = imageNameQueryStatement.executeQuery();
				
				if(result.next())
					image_name = result.getString(1);
			
			} else if(image_id == 4) {
				
				imageNameQuery = "SELECT image_4 FROM love_bird_images WHERE user_id = ?;";
				PreparedStatement imageNameQueryStatement = c.prepareStatement(imageNameQuery);
				
				imageNameQueryStatement.setInt(1, user_id);
				
				ResultSet result = imageNameQueryStatement.executeQuery();
				
				if(result.next())
					image_name = result.getString(1);
			
			} else if(image_id == 5) {
				
				imageNameQuery = "SELECT image_5 FROM love_bird_images WHERE user_id = ?;";
				PreparedStatement imageNameQueryStatement = c.prepareStatement(imageNameQuery);
				
				imageNameQueryStatement.setInt(1, user_id);
				
				ResultSet result = imageNameQueryStatement.executeQuery();
				
				if(result.next())
					image_name = result.getString(1);
			         
			} else if(image_id == 6) {
				
				imageNameQuery = "SELECT image_6 FROM love_bird_images WHERE user_id = ?;";
				PreparedStatement imageNameQueryStatement = c.prepareStatement(imageNameQuery);
				
				imageNameQueryStatement.setInt(1, user_id);
				
				ResultSet result = imageNameQueryStatement.executeQuery();
				
				if(result.next())
					image_name = result.getString(1);
			
			}
			
			c.close();

			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return image_name;
				
	}
	
	public static int getNextImageID(int user_id) {
		
		int image_id = 0;
		
		Connection c = null;
		
		try {
			String url = "jdbc:mysql://localhost:3306/cs3220stu02?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			String db_username = MySQLServer.USERNAME;
			String db_password = MySQLServer.PASSWORD;

			// Make a connection to the database
			c = DriverManager.getConnection(url, db_username, db_password);
			
			String imageNameQuery;
			
			imageNameQuery = "SELECT image_1, image_2, image_3, image_4, image_5, image_6 FROM love_bird_images WHERE user_id = ?;";
			PreparedStatement imageNameQueryStatement = c.prepareStatement(imageNameQuery);
			
			imageNameQueryStatement.setInt(1, user_id);
			
			ResultSet result = imageNameQueryStatement.executeQuery();
			
			if(result.next()) {
				
				if(result.getString(1) == null)
					image_id = 1;
				else if(result.getString(2) == null)
					image_id = 2;
				else if(result.getString(3) == null)
					image_id = 3;
				else if(result.getString(4) == null)
					image_id = 4;
				else if(result.getString(5) == null)
					image_id = 5;
				else if(result.getString(6) == null)
					image_id = 6;
				
			}
			
		
			c.close();

			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return image_id;
				
	}
	
	public static void insertNewImageRecord(String userID) {
		
		int user_id = Integer.parseInt(userID);
		
		Connection c = null;
		
		try {
			String url = "jdbc:mysql://localhost:3306/cs3220stu02?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			String db_username = MySQLServer.USERNAME;
			String db_password = MySQLServer.PASSWORD;

			// Make a connection to the database
			c = DriverManager.getConnection(url, db_username, db_password);
			
			String insertNewImageRecord = "INSERT INTO love_bird_images (user_id) VALUES (?);";
			PreparedStatement insertNewImageRecordStatement = c.prepareStatement(insertNewImageRecord);
			
			insertNewImageRecordStatement.setInt(1, user_id);
			
			
			insertNewImageRecordStatement.executeUpdate();
			
			c.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static void upateImageRecord(String userID, int image_id, String fileName) {
		
		int user_id = Integer.parseInt(userID);
		
		Connection c = null;
		
		try {
			String url = "jdbc:mysql://localhost:3306/cs3220stu02?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			String db_username = MySQLServer.USERNAME;
			String db_password = MySQLServer.PASSWORD;

			// Make a connection to the database
			c = DriverManager.getConnection(url, db_username, db_password);
			
			if(image_id == 1) {
				
				String insertNewImageRecord = "UPDATE love_bird_images SET image_1 = ? WHERE user_id = ?;";
				PreparedStatement insertNewImageRecordStatement = c.prepareStatement(insertNewImageRecord);
				
				insertNewImageRecordStatement.setString(1, fileName);
				insertNewImageRecordStatement.setInt(2, user_id);
				
				
				insertNewImageRecordStatement.executeUpdate();
			
			} else if(image_id == 1) {
				
				String insertNewImageRecord = "UPDATE love_bird_images SET image_1 = ? WHERE user_id = ?;";
				PreparedStatement insertNewImageRecordStatement = c.prepareStatement(insertNewImageRecord);
				
				insertNewImageRecordStatement.setString(1, fileName);
				insertNewImageRecordStatement.setInt(2, user_id);
				
				
				insertNewImageRecordStatement.executeUpdate();
			
			} else if(image_id == 2) {
				
				String insertNewImageRecord = "UPDATE love_bird_images SET image_2 = ? WHERE user_id = ?;";
				PreparedStatement insertNewImageRecordStatement = c.prepareStatement(insertNewImageRecord);
				
				insertNewImageRecordStatement.setString(1, fileName);
				insertNewImageRecordStatement.setInt(2, user_id);
				
				
				insertNewImageRecordStatement.executeUpdate();
			
			} else if(image_id == 3) {
				
				String insertNewImageRecord = "UPDATE love_bird_images SET image_3 = ? WHERE user_id = ?;";
				PreparedStatement insertNewImageRecordStatement = c.prepareStatement(insertNewImageRecord);
				
				insertNewImageRecordStatement.setString(1, fileName);
				insertNewImageRecordStatement.setInt(2, user_id);
				
				
				insertNewImageRecordStatement.executeUpdate();
			
			} else if(image_id == 4) {
				
				String insertNewImageRecord = "UPDATE love_bird_images SET image_4 = ? WHERE user_id = ?;";
				PreparedStatement insertNewImageRecordStatement = c.prepareStatement(insertNewImageRecord);
				
				insertNewImageRecordStatement.setString(1, fileName);
				insertNewImageRecordStatement.setInt(2, user_id);
				
				
				insertNewImageRecordStatement.executeUpdate();
			
			} else if(image_id == 5) {
				
				String insertNewImageRecord = "UPDATE love_bird_images SET image_5 = ? WHERE user_id = ?;";
				PreparedStatement insertNewImageRecordStatement = c.prepareStatement(insertNewImageRecord);
				
				insertNewImageRecordStatement.setString(1, fileName);
				insertNewImageRecordStatement.setInt(2, user_id);
				
				
				insertNewImageRecordStatement.executeUpdate();
			
			} else if(image_id == 6) {
				
				String insertNewImageRecord = "UPDATE love_bird_images SET image_6 = ? WHERE user_id = ?;";
				PreparedStatement insertNewImageRecordStatement = c.prepareStatement(insertNewImageRecord);
				
				insertNewImageRecordStatement.setString(1, fileName);
				insertNewImageRecordStatement.setInt(2, user_id);
				
				
				insertNewImageRecordStatement.executeUpdate();
			
			}
			
			c.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static void updateUserProfile(String username, String bio, String firstName, String lastName, String preference) {
		
		Connection c = null;
		
		try {
			String url = "jdbc:mysql://localhost:3306/cs3220stu02?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			String db_username = MySQLServer.USERNAME;
			String db_password = MySQLServer.PASSWORD;

			// Make a connection to the database
			c = DriverManager.getConnection(url, db_username, db_password);
			
			String updateUserProfile = "UPDATE love_bird_accounts SET bio = ?, first_name = ?, last_name = ?, preference = ? WHERE username = ?;";
			PreparedStatement updateUserProfileStatement = c.prepareStatement(updateUserProfile);
			
			updateUserProfileStatement.setString(1,  bio);
			updateUserProfileStatement.setString(2, firstName);
			updateUserProfileStatement.setString(3, lastName);
			updateUserProfileStatement.setString(4, preference);
			updateUserProfileStatement.setString(5, username);
			
			updateUserProfileStatement.executeUpdate();
			
			c.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
