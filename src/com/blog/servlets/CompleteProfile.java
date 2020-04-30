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
 * Servlet implementation class CompleteProfile
 */
@WebServlet("/CompleteProfile")
@MultipartConfig( fileSizeThreshold = 1024 * 1024, 
				  maxFileSize = 1024 * 1024 * 5,
				  maxRequestSize = 1024 * 1024 * 5 * 5 )
public class CompleteProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
    /*
     * Chemin dans lequel les images seront sauvegardées.
     */
    public static final String IMAGES_FOLDER = "/PICTURES";
        
    public String uploadPath;
    
    /*
     * Si le dossier de sauvegarde de l'image n'existe pas, on demande sa création.
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
    public CompleteProfile() {
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
		
		User form = new User();
		
		if(request!=null) {
			form.profileComplete(request,uploadPath);
			
			String check = form.getQueryResult();
						
			request.setAttribute("form", form);
			
			if(check=="ok") {
							
				this.getServletContext().getRequestDispatcher("/VIEWS/home.jsp").forward(request, response);
			}else {			
				this.getServletContext().getRequestDispatcher("/VIEWS/completeProfile.jsp").forward(request, response);
			}
		}
	}

}
