package com.blog.listners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Application Lifecycle Listener implementation class IpListener
 *
 */
@WebListener
public class IpListener implements HttpSessionAttributeListener {

	 /**
     * Invalidate the session if the attribute "ip" or "user-agent" is
     * updated.
     * 
     * @param se HttpSessionBindingEvent
     */
    public void attributeReplaced(HttpSessionBindingEvent se)
    {
	HttpSession session = se.getSession();
	// This method is called every time any session attribute is called
	// so we have to check if the changed attribute is interesting for
	// us. If the attribute is "ip" or "user-agent", we suspect something
	// malicious, it's safer to disconnect this user.
	if (se.getName().equals("ip") || se.getName().equals("user-agent")) {
	    session.invalidate();
	 }
    }
	
}
