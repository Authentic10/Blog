package com.blog.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.blog.db.DBConnection;

public class Relation {
	
	/**
	 * user followers size
	 */
	int followersSize;
	
	/**
	 * user following size
	 */
	int followingSize;
	
	/**
	 * follow
	 */
	int follow;
	
	/**
	 * Database connection
	 */
	private Connection connection;
	
	/**
	 * Query result
	 */
	private String queryResult;
	
	public void setQueryResult(String queryResult) {
		this.queryResult = queryResult;
	}

	public int getFollowersSize() {
		return followersSize;
	}

	public void setFollowersSize(int followersSize) {
		this.followersSize = followersSize;
	}

	public int getFollowingSize() {
		return followingSize;
	}

	public void setFollowingSize(int followingSize) {
		this.followingSize = followingSize;
	}
	

	public int getFollow() {
		return follow;
	}

	public void setFollow(int follow) {
		this.follow = follow;
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getQueryResult() {
		return queryResult;
	}


	/**
	 * Follow an user
	 */
	public void FollowUser(HttpServletRequest request) {
		
		PreparedStatement state = null;
		
		String current_user = request.getParameter("current_user");
		String user = request.getParameter("user");
		
		int current_id = getUserId(current_user);
		int user_id = getUserId(user);
		
		if(current_id!=0 || user_id!=0) {
			
			connection = DBConnection.getInstance();
			 
			try {
				state = connection.prepareStatement("INSERT INTO Following(userID, userFollowingID) VALUES(?,?)");
					
				state.setInt(1, current_id);
				state.setInt(2, user_id);

				state.executeUpdate();
				
				queryResult = "ok";
				
			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
			
			try {
				state = connection.prepareStatement("INSERT INTO Followers(userID, userFollowerID) VALUES(?,?)");
					
				state.setInt(1, user_id);
				state.setInt(2, current_id);

				state.executeUpdate();
				
				queryResult = "ok";
				
			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
		}
			
		DBConnection.close();
		
	}
	
	/**
	 * Stop following an user
	 */
	public void UnfollowUser(HttpServletRequest request) {
		
		PreparedStatement state = null;
		
		String current_user = request.getParameter("current_user");
		String user = request.getParameter("user");
		
		int current_id = getUserId(current_user);
		int user_id = getUserId(user);
		
		if(current_id!=0 || user_id!=0) {
			
			connection = DBConnection.getInstance();
			 
			try {
				state = connection.prepareStatement("DELETE FROM Following WHERE userID = ? AND userFollowingID = ?");
					
				state.setInt(1, current_id);
				state.setInt(2, user_id);

				state.executeUpdate();
				
				queryResult = "ok";
				
			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
			
			try {
				state = connection.prepareStatement("DELETE FROM Followers WHERE userID = ? AND userFollowerID = ?");
					
				state.setInt(1, user_id);
				state.setInt(2, current_id);

				state.executeUpdate();
				
				queryResult = "ok";
				
			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
		}
			
		DBConnection.close();
		
	}

	/**
	 * Get user id
	 */
	public int getUserId(String username) {
		ResultSet result = null;
		PreparedStatement state = null;
		int id = 0;
			
		if(username!=null) {
	
			connection = DBConnection.getInstance();
			 
			try {
				state = connection.prepareStatement("SELECT userID FROM Users U WHERE U.username = ?");
					
				state.setString(1, username);
				
				result = state.executeQuery();
				
				int dbUserID = 0;	
				
				while(result.next()) {
					dbUserID = result.getInt(1);
				}
				
				id = dbUserID;
				
				queryResult = "ok";
				
			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
		}
			
		DBConnection.close();
		
		return id;
	
}

	/**
	 * Get user following size
	 */
	public void getUserFollwersSize(String username) {
		ResultSet result = null;
		PreparedStatement state = null;
		
		int user_id = getUserId(username);
			
		if(user_id!=0) {
	
			connection = DBConnection.getInstance();
			 
			try {
				state = connection.prepareStatement("SELECT COUNT(F.UserID) FROM FOLLOWERS F, USERS U WHERE F.UserID = U.UserID AND F.UserId = ?");
					
				state.setInt(1, user_id);
				
				result = state.executeQuery();
				
				int dbFollowersSize = 0;	
				
				while(result.next()) {
					dbFollowersSize = result.getInt(1);
				}
				
				followersSize = dbFollowersSize;
				
				queryResult = "ok";
				
			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
		}
			
		DBConnection.close();
			
}

	/**
	 * Get user following size
	 */
	public void getUserFollwingSize(String username) {
		ResultSet result = null;
		PreparedStatement state = null;
		
		int user_id = getUserId(username);
			
		if(user_id!=0) {
	
			connection = DBConnection.getInstance();
			 
			try {
				state = connection.prepareStatement("SELECT COUNT(F.UserID) FROM FOLLOWING F, USERS U WHERE F.UserID = U.UserID AND F.UserId = ?");
					
				state.setInt(1, user_id);
				
				result = state.executeQuery();
				
				int dbFollowingSize = 0;	
				
				while(result.next()) {
					dbFollowingSize = result.getInt(1);
				}
				
				followingSize = dbFollowingSize;
				
				queryResult = "ok";
				
			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
		}
			
		DBConnection.close();
			
}
	
	/**
	 * Check if an user follow another user
	 */
	public void checkIfUserFollow(String user, String current_user) {
		ResultSet result = null;
		PreparedStatement state = null;
		
		int user_id = getUserId(user);
		int current_user_id = getUserId(current_user);
			
		if(user_id!=0) {
	
			connection = DBConnection.getInstance();
			 
			try {
				state = connection.prepareStatement("SELECT COUNT(F.UserID) FROM FOLLOWERS F, USERS U WHERE F.UserID = U.UserID AND F.UserId = ? AND F.UserFollowerID = ?");
					
				state.setInt(1, user_id);
				state.setInt(2, current_user_id);
				
				result = state.executeQuery();
				
				int followUser = 0;	
				
				while(result.next()) {
					followUser = result.getInt(1);
				}
				
				follow = followUser;
								
				queryResult = "ok";
				
			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
		}
			
		DBConnection.close();
			
}
	
	/**
	 * Get user followers
	 */
	public List<User> getUserFollowers(String username) {
		
		List<User> users = new ArrayList<User>();
		
		ResultSet result = null;
		PreparedStatement state = null;
		
		int user_id = getUserId(username);
			
		if(user_id!=0) {
	
			connection = DBConnection.getInstance();
			 
			try {
				state = connection.prepareStatement("SELECT username, firstname, lastname, avatar, biography FROM Users U, Following F WHERE U.UserID = F.UserID AND F.UserFollowingID = ?");
					
				state.setInt(1, user_id);
				
				result = state.executeQuery();
				
				
				while(result.next()) {
					String user_name = result.getString(1);
					String firstname = result.getString(2);
					String lastname = result.getString(3);
					String avatar = result.getString(4);
					String biography = result.getString(5);
					
					User user = new User();
					user.setUsername(user_name);
					user.setFirstname(firstname);
					user.setLastname(lastname);
					user.setProfile(avatar);
					user.setBiography(biography);
					
					users.add(user);
					
				}
				
				
				queryResult = "ok";
				
			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
		}
			
		DBConnection.close();
			
		return users;
	}
	
	/**
	 * Get user following
	 */
	public List<User> getUserFollowing(String username) {
		
		List<User> users = new ArrayList<User>();
		
		ResultSet result = null;
		PreparedStatement state = null;
		
		int user_id = getUserId(username);
			
		if(user_id!=0) {
	
			connection = DBConnection.getInstance();
			 
			try {
				state = connection.prepareStatement("SELECT username, firstname, lastname, avatar, biography FROM Users U, Followers F WHERE U.UserID = F.UserID AND F.UserFollowerID = ?");
					
				state.setInt(1, user_id);
				
				result = state.executeQuery();
				
				
				while(result.next()) {
					String user_name = result.getString(1);
					String firstname = result.getString(2);
					String lastname = result.getString(3);
					String avatar = result.getString(4);
					String biography = result.getString(5);
					
					User user = new User();
					user.setUsername(user_name);
					user.setFirstname(firstname);
					user.setLastname(lastname);
					user.setProfile(avatar);
					user.setBiography(biography);
					
					users.add(user);
					
				}
				
				
				queryResult = "ok";
				
			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
		}
			
		DBConnection.close();
			
		return users;
	}
	

}
