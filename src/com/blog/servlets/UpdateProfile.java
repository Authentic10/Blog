package com.blog.servlets;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.blog.beans.User;

/**
 * Servlet implementation class UpdateProfile
 */
@WebServlet("/UpdateProfile")
@MultipartConfig( fileSizeThreshold = 1024 * 1024, 
					maxFileSize = 1024 * 1024 * 5,
					maxRequestSize = 1024 * 1024 * 5 * 5 )
public class UpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*
     * Path where the pictures will be saved
     */
    public static final String IMAGES_FOLDER = "/PICTURES";
        
    public String uploadPath;
    
    /*
     * Create the directory if it doesn't exist
     */ 
    @Override
    public void init() throws ServletException {
        uploadPath = getServletContext().getRealPath( IMAGES_FOLDER );
        File uploadDir = new File( uploadPath );
        if ( ! uploadDir.exists() ) uploadDir.mkdir();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = new User();
		user.loadCurrentUser(request);
		
		String profileName = user.getProfile();
		
		user.setProfile("PICTURES/"+profileName);
								
		request.setAttribute("user", user);
		
		this.getServletContext().getRequestDispatcher("/VIEWS/updateProfile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		User form = new User();
		
		if(request!=null) {
			form.profileComplete(request,uploadPath);
			
			String check = form.getQueryResult();
						
			request.setAttribute("form", form);
			
			if(check=="ok") {
					
				response.sendRedirect(request.getContextPath() + "/profile?username="+session.getAttribute("username")); 
			}else {			
				this.getServletContext().getRequestDispatcher("/VIEWS/updateProfile.jsp").forward(request, response);
			}
		}
	}

}
