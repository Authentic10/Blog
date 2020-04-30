package com.blog.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.blog.beans.Post;
import com.blog.beans.Relation;
import com.blog.beans.User;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String IMAGES_FOLDER = "/PICTURES";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
		String current_user = (String) session.getAttribute("username");
		String username = (String) request.getParameter("username");
				
		User user = new User();
		user.loadUserInformation(request);
		
		String profileName = user.getProfile();
		
		user.setProfile("PICTURES/"+profileName);
		
		Relation relation = new Relation();
		
		relation.getUserFollwersSize(username);
		relation.getUserFollwingSize(username);
		
		relation.checkIfUserFollow(username, current_user);
				
		Post posts = new Post();	
				
		request.setAttribute("user", user);
		
		request.setAttribute("relation", relation);
		
		request.setAttribute("posts", posts.getUserPosts(request));
		
		this.getServletContext().getRequestDispatcher("/VIEWS/profile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
