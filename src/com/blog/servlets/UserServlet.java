package com.blog.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.blog.beans.Post;
import com.blog.beans.User;


/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
		String account = (String) session.getAttribute("account");
		
		if((account.equals("2"))) {
			
			User user = new User();
			user.loadUserInformation(request);
			
			String profileName = user.getProfile();
			
			user.setProfile("PICTURES/"+profileName);
			
			Post posts = new Post();
					
			request.setAttribute("user", user);
			request.setAttribute("posts", posts.getPosts(request));
			
			this.getServletContext().getRequestDispatcher("/VIEWS/home.jsp").forward(request, response);
		} else if(account.equals("1")) {
			this.getServletContext().getRequestDispatcher("/VIEWS/completeProfile.jsp").forward(request, response);
		} else {
			this.getServletContext().getRequestDispatcher("/VIEWS/codeVerification.jsp").forward(request, response);
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
