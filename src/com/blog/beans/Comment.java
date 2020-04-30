package com.blog.beans;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.blog.db.DBConnection;

public class Comment {
	/**
	 * comment id
	 */
	int id;
	
	/**
	 * comment content
	 */
	String comment;
	
	/**
	 * comment timestamp
	 */
	String timestamp;
	
	/**
	 * comment username
	 */
	String username;
	
	/**
	 * username avatar
	 */
	String avatar;
	
	/**
	 * Database connection
	 */
	private Connection connection;
	
	/**
	 * sql query result
	 */
	String queryResult;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
	
	/**
	 *Add comment to a post
	 */
	public void addComment(HttpServletRequest request) throws IOException, ServletException {
		
		PreparedStatement state = null;
				
		HttpSession session = request.getSession();
		
		//Get the current username attribute
		String user = (String) session.getAttribute("username");
		
		//Get the username id
		int user_id = getUserId(user);
				
		
		if(user_id!=0) {
			
			connection = DBConnection.getInstance();
			
			//Get parameters from the form
			comment = request.getParameter("comment");
			String postId = request.getParameter("id");
			Timestamp timestamp = getCurrentTimestamp();			
			 
			try {
				state = connection.prepareStatement("INSERT INTO Comments(userID, PostID, timestamp, content) VALUES(?,?,?,?)");
					
				state.setInt(1, user_id);
				state.setString(2, postId);
				state.setTimestamp(3, timestamp);
				state.setString(4, comment);

				state.executeUpdate();
				
				queryResult = "ok";
				
				request.setAttribute("id", postId);
				
			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
			
		}
					
		DBConnection.close();
		
	}
	
	/**
	 * Get a post comments
	 */
	public List<Comment> getPostComments(HttpServletRequest request) {
		
		//A list of comments to save all the comments
		List<Comment> comments = new ArrayList<Comment>();
		
    	ResultSet result = null;
		PreparedStatement state = null;
				
		//Get the post id
		String postId = request.getParameter("id");
		
		queryResult = "";
		
		if(postId!=null) {
			
			connection = DBConnection.getInstance();
			 						
			try {
				state = connection.prepareStatement("SELECT C.timestamp, C.content, C.commentID, U.username, U.avatar FROM Comments C, Users U, Post P \n"
						+" WHERE C.userID = U.userID AND C.postID = P.postID AND P.postID = ? ORDER BY C.timestamp DESC");
					
				state.setString(1, postId);
				 
				result = state.executeQuery();
				
				
				while(result.next()) {
					Timestamp timestamp = result.getTimestamp(1);
					comment = result.getString(2);
					id = result.getInt(3);
					username = result.getString(4);
					avatar = result.getString(5);

					//Set timestamp format
					String s = new SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(timestamp);
					
					Comment comment = new Comment();
					comment.setTimestamp(s);
					comment.setComment(this.comment);
					comment.setId(id);
					comment.setUsername(username);
					comment.setAvatar(avatar);
					
					comments.add(comment);
				}
				

				queryResult = "ok";
				
			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
		} 
				
		DBConnection.close();
		
		return comments;
		
	}
	
	/**
	 * Get the user id using his username
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
	
	  public Timestamp getCurrentTimestamp(){
		    //Date object
			 Date date= new Date();
			 
		     //getTime() returns current time in milliseconds
			 long time = date.getTime();
		     //Passed the milliseconds to constructor of Timestamp class 
			 Timestamp ts = new Timestamp(time);
	 
			 return ts;
	    }

}
