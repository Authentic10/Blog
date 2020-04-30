package com.blog.beans;

import java.io.File;
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
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


import com.blog.db.DBConnection;

public class Post {
	
	/**
	 * post id
	 */
	int id;
	
	/**
	 * post title
	 */
	String title;
	
	/**
	 * post picture
	 */
	String picture;
	
	/**
	 * post text
	 */
	String text;
	
	/**
	 * post timestamp
	 */
	String timestamp;
	
	/**
	 * username
	 */
	String username;
	
	/**
	 * user avatar
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
	 * Add post
	 */
	public void addPost(HttpServletRequest request, String uploadPath) throws IOException, ServletException {
		
		PreparedStatement state = null;
		
		HttpSession session = request.getSession();
		
		//Get current user
		String current_user = (String) session.getAttribute("username");
		
		//Get the current timestamp
		Timestamp timestamp = getCurrentTimestamp();
		
		//Create unique variable for the filename
		String s = UUID.randomUUID().toString();

		
		String fullPath = ""; //Post picture full path
		String finalName =""; //Post file final name

		//Get the pciture the user choose
		for ( Part part : request.getParts() ) {
	        String fileName = getFileName( part );  // Get the file name
	        if(!fileName.equals("")) {
	        	finalName = s+"-"+fileName;
	        	fullPath = uploadPath + File.separator + finalName;
		        part.write( fullPath ); //Write the file 
		        break;
	        } else {
	        	finalName = "";
	        }
	        
	    }
		
		//Get the current user id
		int current_id = getUserId(current_user);
		
		
		if(current_id!=0) {
			
			connection = DBConnection.getInstance();
			
			title = request.getParameter("title");
			text = request.getParameter("text-post");
			
			 
			try {
				state = connection.prepareStatement("INSERT INTO POST(userID, timestamp, content, media, title) VALUES(?,?,?,?,?)");
					
				state.setInt(1, current_id);
				state.setTimestamp(2, timestamp);
				state.setString(3, text);
				state.setString(4, finalName);
				state.setString(5, title);

				state.executeUpdate();
				
				queryResult = "success";
				
			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
			
		}
					
		DBConnection.close();
		
	}
	
	/**
	 * Get the current user posts
	 */
	public List<Post> getUserPosts(HttpServletRequest request) {
		
		List<Post> posts = new ArrayList<Post>();
		
    	ResultSet result = null;
		PreparedStatement state = null;
						
		String username = (String) request.getParameter("username");
		
		int user_id = getUserId(username);
				
		queryResult = "";
		
		if(username!=null) {
			
			connection = DBConnection.getInstance();
			 						
			try {
				state = connection.prepareStatement("SELECT timestamp, content, media, title, postID FROM Post WHERE userID = ? ORDER BY timestamp DESC");
					
				state.setInt(1, user_id);
				
				result = state.executeQuery();
				
				
				while(result.next()) {
					Timestamp timestamp = result.getTimestamp(1);
					text = result.getString(2);
					picture = result.getString(3);
					title = result.getString(4);
					id = result.getInt(5);
					
					String s = new SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(timestamp);
					
					Post post = new Post();
					post.setTimestamp(s);
					post.setText(text);
					post.setPicture(picture);
					post.setTitle(title);
					post.setId(id);
					
					posts.add(post);
				}
				

				queryResult = "ok";
				
			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
		} 
				
		DBConnection.close();
		
		return posts;
		
	}
	
	
	/**
	 * Get all posts the user can see
	 */
	public List<Post> getPosts(HttpServletRequest request) {
		
		List<Post> posts = new ArrayList<Post>();
		
    	ResultSet result = null;
		PreparedStatement state = null;
				
		HttpSession session = request.getSession();
		
		String username = (String) session.getAttribute("username");
		
		int user_id = getUserId(username);
				
		queryResult = "";
		
		if(username!=null) {
			
			connection = DBConnection.getInstance();
			 						
			try {
				state = connection.prepareStatement("SELECT P.timestamp, P.content, P.media, P.title, U.username, U.avatar, P.postID FROM Post P, Users U, Following F WHERE P.userID = U.userID\n" + 
						"AND U.userID = F.userFollowingID AND F.userID = ? \n" + 
						"UNION ALL\r\n" + 
						"SELECT P.timestamp, P.content, P.media, P.title, U.username, U.avatar, P.postID FROM Post P, Users U WHERE P.userID = U.userID AND U.userID = ? ORDER BY timestamp DESC");
					
				state.setInt(1, user_id);
				state.setInt(2, user_id);
				
				result = state.executeQuery();
				
				
				while(result.next()) {
					Timestamp timestamp = result.getTimestamp(1);
					text = result.getString(2);
					picture = result.getString(3);
					title = result.getString(4);
					this.username = result.getString(5);
					avatar = result.getString(6); 
					id = result.getInt(7);
															
					String s = new SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(timestamp);
					
					Post post = new Post();
					post.setTimestamp(s);
					post.setText(text);
					post.setPicture(picture);
					post.setTitle(title);
					post.setUsername(this.username);
					post.setAvatar(avatar);
					post.setId(id);
					
					posts.add(post);
				}
				

				queryResult = "ok";
				
			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
		} 
						
		DBConnection.close();
		
		return posts;
		
	}
	
	/**
	 * Get the user id
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
	 * Get a specific post
	 */
	public void getPost(HttpServletRequest request) {
		ResultSet result = null;
		PreparedStatement state = null;
				
		String id = request.getParameter("id");
					
		if(id!=null) {
	
			connection = DBConnection.getInstance();
			 
			try {
				state = connection.prepareStatement("SELECT P.timestamp, P.content, P.media, P.title, U.username, U.avatar, P.postID FROM Post P, Users U \n" + 
						"WHERE P.userID = U.userID AND P.postID = ?");
					
				state.setString(1, id);
				
				result = state.executeQuery();
				
				
				while(result.next()) {
					Timestamp timestamp = result.getTimestamp(1);
					String s = new SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(timestamp);
					this.timestamp = s;
					text = result.getString(2);
					picture = result.getString(3);
					title = result.getString(4);
					this.username = result.getString(5);
					avatar = result.getString(6); 
					this.id = result.getInt(7);
					
				}
				
				
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
	 * Get current timestamp
	 */
   public Timestamp getCurrentTimestamp(){
	    //Date object
		 Date date= new Date();
		 
	     //getTime() returns current time in milliseconds
		 long time = date.getTime();
	     //Passed the milliseconds to constructor of Timestamp class 
		 Timestamp ts = new Timestamp(time);
 
		 return ts;
    }
   
   /*
    * Get the file name
    */
   private String getFileName( Part part ) {
       for ( String content : part.getHeader( "content-disposition" ).split( ";" ) ) {
           if ( content.trim().startsWith( "filename" ) )
               return content.substring( content.indexOf( "=" ) + 2, content.length() - 1 );
       }
       return "";
   }
}
