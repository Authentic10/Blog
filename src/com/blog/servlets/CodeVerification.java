package com.blog.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.beans.User;

/**
 * Servlet implementation class CodeVerification
 */
@WebServlet("/CodeVerification")
public class CodeVerification extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CodeVerification() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher("/VIEWS/codeVerification.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User form = new User();
		
		if(request!=null) {
			form.codeVerification(request);
			
			String check = form.getQueryResult();
						
			request.setAttribute("form", form);
			
			if(check=="ok") {
				this.getServletContext().getRequestDispatcher("/VIEWS/completeProfile.jsp").forward(request, response);
			} else {			
				this.getServletContext().getRequestDispatcher("/VIEWS/codeVerification.jsp").forward(request, response);
			}
		} 
		
	}

}
