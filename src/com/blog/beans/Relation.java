package com.blog.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.blog.db.DBConnection;

public class Relation {
	
	int userId;
	
	int userFollowerId;
	
	int userFollowingId;
	
	int followersSize;
	
	int followingSize;
	
	int follow;
	
	/**
	 * Database connection
	 */
	private Connection connection;
	
	/**
	 * Query result
	 */
	private String queryResult;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserFollowerId() {
		return userFollowerId;
	}

	public void setUserFollowerId(int userFollowerId) {
		this.userFollowerId = userFollowerId;
	}

	public int getUserFollowingId() {
		return userFollowingId;
	}

	public void setUserFollowingId(int userFollowingId) {
		this.userFollowingId = userFollowingId;
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
				
				System.out.println("FOLLOW : "+followUser);
				
				queryResult = "ok";
				
			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
		}
			
		DBConnection.close();
			
}
	

}
