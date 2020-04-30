package com.blog.servlets;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.beans.Post;

/**
 * Servlet implementation class AddPost
 */
@WebServlet("/AddPost")
@MultipartConfig( fileSizeThreshold = 1024 * 1024, 
					maxFileSize = 1024 * 1024 * 5,
					maxRequestSize = 1024 * 1024 * 5 * 5 )
public class AddPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*
     * The path where pictures will be saved
     */
    public static final String IMAGES_FOLDER = "/PICTURES";
        
    public String uploadPath;
    
    /*
     * If the directory does not exist, we create it
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
    public AddPost() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher("/VIEWS/addPost.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Post post = new Post();
		
		if(request!=null) {
			post.addPost(request, uploadPath);
			
			String check = post.getQueryResult();
						
			request.setAttribute("post", post);
			
			if(check=="success") {
							
				response.sendRedirect(request.getContextPath() + "/userServlet");
			}else {			
				this.getServletContext().getRequestDispatcher("/VIEWS/addPost.jsp").forward(request, response);
			}
		}
	}

}
