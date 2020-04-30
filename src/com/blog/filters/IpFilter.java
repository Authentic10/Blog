package com.blog.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * On each incoming request on any URL, we store the IP address and the
 * User-Agent HTTP header **if it's different from the ones in the previous
 * request**. When a session attribute is updated our IpListener is called
 * and the user is disconnected by safety, so we prefer calling it only
 * when the IP of the User-Agent is updated.
 */
@WebFilter(filterName="ipFilter", urlPatterns = {"/VIEWS/*"})
public class IpFilter implements Filter {

    /**
     * Stores the IP address and User-Agent in session.
     * 
     * @param req ServletRequest A servlet request (not a HttpServletRequest!)
     * @param res ServletResponse A servlet response (not a HttpServletResponse!)
     * @param FilterChain chain The whole chain containing all the filters and
     * in the last position, the servlet.
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
	throws IOException, ServletException {
	/*
	 * We are handling HTTP requests so we are sure the requests are
	 * HttpServletRequest, but to be generic the method takes a
	 * ServletRequest as a parameter. That's why we have to cast
	 * the request and the response.
	 */
	HttpServletRequest request = (HttpServletRequest) req;
	HttpServletResponse response = (HttpServletResponse) res;
	HttpSession session = request.getSession();
	
	String contextPath = request.getServletContext().getContextPath();
	// The current IP of the user, may be the same or different.
	String newIp = request.getRemoteAddr();
	// The current User-Agent of the user, may be the same or different.
	String newUserAgent = request.getHeader("user-agent");
	// The IP address as we may have already stored.
	String oldIp = (String) session.getAttribute("ip");
	// The User-Agent as we may have already stored.
	String oldUserAgent = (String) session.getAttribute("user-agent");
	
	// We surround the session handling in a try/catch, and it's important
	// to understand why.
	// If you try to stole a session, the server invalidates the session.
	// That means it still exists, but is invalid and unusable. If you
	// try to fill the form after the session has been invalidated, you
	// should have an IllegalStateException, meaning the session is in an
	// invalid state. This exception is thrown when you use the session.
	// By surrounding this part in a try/catch, we detect if the session is
	// invalid and we can redirect the user to the SetSessionServlet page.
	try {
	    // Is the current IP address different from the stored one?
	    if (!newIp.equals(oldIp)) {
		/*
		 * IP of the current request is different. During the
		 * browsing the IP changed. it's possible, but suspect.
		 * We store the new IP, so our listener will be called
		 * and the session will be invalidated. 
		 */
		session.setAttribute("ip", newIp);
	    }
	    
	    // Is the current User-Agent different from the stored one?
	    if (!newUserAgent.equals(oldUserAgent)) {
		/*
		 * User-Agent of the current is different. During
		 * the browsing the user-agent changed. In a normal
		 * navigation it should not happen, except if your
		 * browser has been updated (that's an example). We
		 * store the new user-agent so our listener will be
		 * called and the session will be disabled.
		 * If the session is already invalidated an exception
		 * is thrown in setAttribute.
		 */
		session.setAttribute("user-agent", newUserAgent);
	    }
	    // Call the next filters if any, or the servlet otherwise
	    chain.doFilter(request, response);
	} catch (IllegalStateException e) {
	    // Session seems to be invalid, redirect the user to the form.
	    response.sendRedirect(contextPath + "/SetSessionServlet");
	}
    }
}
