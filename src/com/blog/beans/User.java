package com.blog.beans;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.blog.db.DBConnection;
import com.blog.utilities.Email;
import com.blog.utilities.EncryptPassword;

/**
 * Represent a user as stored in the database.
 */
public class User
{
	/**
	 * The user's identifier.
	 * 
	 * Primary key.
	 */
	private int id;
	
	/**
	 * The user's firstname.
	 */
	private String firstname;
	
	/**
	 * The user's lastname.
	 */
	private String lastname;
	
	/**
	 * The user's username.
	 */
	private String username;
	
	/**
	 * The user's password.
	 */
	private String password;
	
	/**
	 * The user's email.
	 */
	private String email;
	
	/**
	 * The user's code on subscription.
	 */
	private String code;
	
	/**
	 * The user's profile.
	 */
	private String profile;
	
	/**
	 * The user's biography.
	 */
	private String biography;
	
	/**
	 * Database connection
	 */
	private Connection connection;
	
	/**
	 * Query result
	 */
	private String queryResult;
	
	/**
	 * Query result
	 */
	private String account;
	

	public int getId() {
		return id;
	}

	public User setId(int id) {
		this.id = id;
		return this;
	}

	public String getFirstname() {
		return firstname;
	}

	public User setFirstname(String firstname) {
		this.firstname = firstname;
		return this;
	}

	public String getLastname() {
		return lastname;
	}

	public User setLastname(String lastname) {
		this.lastname = lastname;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * Sign-in user method
	 */
	public void signInUser(HttpServletRequest request) {
		
		ResultSet result = null;
		PreparedStatement state = null;
		
		username = request.getParameter("username");
		password = request.getParameter("password");
				
		String encrypted = EncryptPassword.encrypt(password);
				
		if(!username.equals(null)) {
			
			connection = DBConnection.getInstance();
			 
			try {
				state = connection.prepareStatement("SELECT username, password, accountvalidation, avatar FROM Users U \n " 
						+"WHERE U.username = ? AND U.password = ? ");
					
				
				state.setString(1, username);
				state.setString(2, encrypted);
				
				result = state.executeQuery();
				
				String dbUsername="";
				String dbPassword="";
				String dbUserAV ="";
				String dbAvatar ="";
				
				
				while(result.next()) {
					dbUsername = result.getString(1);
					dbPassword = result.getString(2);
					dbUserAV = result.getString(3);
					dbAvatar = result.getString(4);
				}
				
				
				if(!dbUsername.equals("")) {
					if(dbUsername.equalsIgnoreCase(username) && dbPassword.equalsIgnoreCase(encrypted)) {
						if(dbUserAV.equals("2")) {
							queryResult = "ok";
						} else if(dbUserAV.equals("0")) {
							queryResult = "av"; //account not validated
						} else if(dbUserAV.equals("1")) {
							queryResult = "ci"; //Complete information
						}
						profile = dbAvatar;
						account = dbUserAV;
					}
					else {
						queryResult = "no";
					}
				} else {
					queryResult = "na"; //Account not found
				}

			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
		}
		else {
			queryResult= "no";
		}		
		
		DBConnection.close();
		
	}
	
	/**
	 * Sign up user
	 */
	public void signUpUser(HttpServletRequest request) {
		
		PreparedStatement state = null;
		
		email = request.getParameter("email");
		username = request.getParameter("username");
		password = request.getParameter("password");
		String password_confirmation = request.getParameter("password_confirmation");
		
		String encrypted = EncryptPassword.encrypt(password);
		
		queryResult = "";
		
		if((!email.equals(null)) || (!username.equals(null)) || (!password.equals(null)) || 
				(!password_confirmation.equals(null))) {
			
			formValidation(username,password,password_confirmation,email);
			
		}
	
			
		if(queryResult.equals("")) {

			Random r = new Random();
			int x = r.nextInt(999999-100001)+100001;
			String USER_CODE = String.valueOf(x);
			
			connection = DBConnection.getInstance();
			 
			try {
				state = connection.prepareStatement("INSERT INTO Users(email,username,password,code) VALUES(?,?,?,?)");
					
				state.setString(1, email);
				state.setString(2, username);
				state.setString(3, encrypted);
				state.setString(4, USER_CODE);
				
				state.executeUpdate();
				
				Email.sendEmail(email, USER_CODE);

				queryResult = "ok";
				
				account = "0";
				
			}
			catch(SQLException e){
				queryResult= "There was an error, please retry !";
				e.printStackTrace();
			}
		}
			
		DBConnection.close();
		
	}
	
	/**
	 * Check the user subscription confirmation code
	 */
	public void codeVerification(HttpServletRequest request) {
		ResultSet result = null;
		PreparedStatement state = null;
		
		code = request.getParameter("code");
		
		HttpSession session = request.getSession();
		
		String username = (String) session.getAttribute("username");
						
		if(!username.equals(null)) {
			
			connection = DBConnection.getInstance();
			 
			try {
				state = connection.prepareStatement("SELECT code FROM Users U WHERE U.username = ?");
					
				
				state.setString(1, username);
				
				result = state.executeQuery();
				
				String dbCode="";	
				
				while(result.next()) {
					dbCode = result.getString(1);
	
				}
						
				
				if(dbCode.equals(code)) {
						queryResult = "ok";
						
						try {
							state = connection.prepareStatement("UPDATE Users SET accountValidation = ? WHERE username = ?");
								
							state.setString(1, "1");
							state.setString(2, username);
							
							state.executeUpdate();

							queryResult = "ok";
							
							session.setAttribute("account", "1");
							
						}
						catch(SQLException e){
							queryResult= "no";
							e.printStackTrace();
						}
				}
				else {
					queryResult = "ic"; //Complete information
				}

			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
		}
		else {
			queryResult= "no";
		}		
		
		DBConnection.close();
		
	}
	
	/**
	 * Complete user profile
	 */
	public void profileComplete(HttpServletRequest request, String uploadPath) throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		
		String username = (String) session.getAttribute("username");
		
		PreparedStatement state = null;
		String fullPath = "";
		String finalName ="";
		
		lastname = request.getParameter("lastname");
		firstname = request.getParameter("firstname");
		biography = request.getParameter("biography");
		
		
		for ( Part part : request.getParts() ) {
	        String fileName = getFileName( part );
	        if(!fileName.equals("")) {
	        	finalName = username+"-"+fileName;
	        	fullPath = uploadPath + File.separator + finalName;
		        part.write( fullPath );
		        break;
	        } else {
	        	finalName = "no-profile-picture.jpg";
	        }
	        
	    }
		
		queryResult = "";
						
		if((lastname!=null) || (firstname!=null)) {
			completeProfileValidation(firstname, lastname);
		}
		
		if(queryResult.equals("")) {
			
			connection = DBConnection.getInstance();
			 						
			try {
				state = connection.prepareStatement("UPDATE Users SET firstname = ?, lastname = ?, avatar = ?, \n"
						+" biography = ?, accountValidation = ? WHERE username = ?");
					
				state.setString(1, firstname);
				state.setString(2, lastname);
				state.setString(3, finalName);
				state.setString(4, biography);
				state.setString(5, "2");
				state.setString(6, username);
				
				
				state.executeUpdate();

				queryResult = "ok";
				
				session.setAttribute("avatar","PICTURES/"+finalName);
				session.setAttribute("account", "2");
				
			}
			catch(SQLException e){
				queryResult= "no";
				e.printStackTrace();
			}
		} 
		
		DBConnection.close();
		
	}
	
	/**
	 * Get an user information
	 */
    public void loadUserInformation(HttpServletRequest request) {
    	ResultSet result = null;
		PreparedStatement state = null;
						
		String username = (String) request.getParameter("username");
				
		queryResult = "";
		
		if(username!=null) {
			
			connection = DBConnection.getInstance();
			 						
			try {
				state = connection.prepareStatement("SELECT firstname, lastname, biography, avatar, username FROM Users WHERE username = ?");
					
				state.setString(1, username);
				
				result = state.executeQuery();
				
				
				while(result.next()) {
					firstname = result.getString(1);
					lastname = result.getString(2);
					biography = result.getString(3);
					profile = result.getString(4);
					this.username = result.getString(5);
					//request.setAttribute("av", result.getString(5));
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
	 * Load current user information
	 */
    public void loadCurrentUser(HttpServletRequest request) {
    	ResultSet result = null;
		PreparedStatement state = null;
				
		HttpSession session = request.getSession();
		
		String username = (String) session.getAttribute("username");
				
		queryResult = "";
		
		if(username!=null) {
			
			connection = DBConnection.getInstance();
			 						
			try {
				state = connection.prepareStatement("SELECT firstname, lastname, biography, avatar, username FROM Users WHERE username = ?");
					
				state.setString(1, username);
				
				result = state.executeQuery();
				
				
				while(result.next()) {
					firstname = result.getString(1);
					lastname = result.getString(2);
					biography = result.getString(3);
					profile = result.getString(4);
					this.username = result.getString(5);
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
	 * Search an user
	 */
    public List<User> searchUser(String username) {
    	
    	List<User> users = new ArrayList<User>();
    	
    	ResultSet result = null;
		PreparedStatement state = null;
										
		queryResult = "";
	
		
		if(username!=null) {
			
			connection = DBConnection.getInstance();
			 						
			try {
				state = connection.prepareStatement("SELECT firstname, lastname, avatar, username FROM Users WHERE username LIKE ?");
					
				state.setString(1, username+"%");
				
				result = state.executeQuery();
				
				
				while(result.next()) {
					firstname = result.getString(1);
					lastname = result.getString(2);
					profile = result.getString(3);
					this.username = result.getString(4);
					
					User user = new User();
					user.setFirstname(firstname);
					user.setLastname(lastname);
					user.setProfile(profile);
					user.setUsername(this.username);
					
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
	 * email address validation
	 */
	private void emailValidation( String email ) throws Exception {
	    if ( email != null && email.trim().length() != 0 ) {
	        if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
	            throw new Exception( "Please, enter a valid email address" );
	        }
	    } else {
	        throw new Exception( "Please, enter an email adress" );
	    }
	}

	/**
	 * password validation
	 */
	private void passwordValidation( String password, String confirmation ) throws Exception{
	    if (password != null && password.trim().length() != 0 && confirmation != null && confirmation.trim().length() != 0) {
	        if (!password.equals(confirmation)) {
	            throw new Exception("Your passwords do not match, please try again !");
	        } else if (password.trim().length() < 6) {
	            throw new Exception("Passwords must have at least 6 carachters");
	        }
	    } else {
	        throw new Exception("Please, make sure you enter password and confirm it");
	    }
	}

	/**
	 * Username Validation
	 */
	private void usernameValidation(String username) throws Exception {
	
	    if (username != null && username.trim().length() < 2 ) {
	        throw new Exception( "The username must have at least 2 carachters" );
	    } else {
	    	
	    	ResultSet result = null;
			PreparedStatement state = null;
			

			if(!username.equals(null)) {
				
				connection = DBConnection.getInstance();
				 
				try {
					state = connection.prepareStatement("SELECT username FROM Users U WHERE U.username = ? ");
						
					
					state.setString(1, username);
					
					result = state.executeQuery();
					
					String dbUsername="";
					
					while(result.next()) {
						dbUsername = result.getString(1);
					}
							
					
					if(dbUsername.equalsIgnoreCase(username)) {
							queryResult = "ut"; // username taken
					}
					else {
						queryResult = "";
					}

				}
				catch(SQLException e){
					queryResult= "no";
					e.printStackTrace();
				}
			}
			else {
				queryResult= "no";
			}		
			
			DBConnection.close();
	    }
	}
	
	/**
	 * Username Validation
	 */
	private void firstnameValidation(String firstname) throws Exception {
	
	    if (firstname != null && firstname.trim().length() < 2 ) {
	        throw new Exception( "The firstname must have at least 2 carachters" );
	    }
	}
	
	private void lastnameValidation(String lastname) throws Exception {
		
	    if (lastname != null && lastname.trim().length() < 2 ) {
	        throw new Exception( "The lastname must have at least 2 carachters" );
	    }
	}
	
	/**
	 * Sign-up form validation
	 */
	private void formValidation(String username, String password, String confirmation, String email) {
		
		try {
			 passwordValidation(password, confirmation);
		} catch(Exception e){
			queryResult = e.getMessage();		
		}
		
		try {
			 usernameValidation(username);
		} catch(Exception e){
			queryResult = e.getMessage();
		}
		
		try {
			 emailValidation(email);	
		} catch(Exception e){
			queryResult = e.getMessage();		
		}
	}
	
	/**
	 * Complete profile form validation
	 */
	private void completeProfileValidation(String firstname, String lastname) {
		
		try {
			 firstnameValidation(lastname);
		} catch(Exception e){
			queryResult = e.getMessage();		
		}
		
		try {
			 lastnameValidation(firstname);
		} catch(Exception e){
			queryResult = e.getMessage();
		}
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
