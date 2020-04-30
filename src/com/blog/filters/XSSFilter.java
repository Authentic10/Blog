package com.blog.filters;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Servlet Filter implementation class XSSFilter
 */
@WebFilter(filterName="XSSFilter", urlPatterns = {"/*"})
public class XSSFilter implements Filter {

    /**
     * Default constructor. 
     */
    public XSSFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
	    chain.doFilter( new XssRequestWrapper( (HttpServletRequest) request), response );

	}
	
	   private static class XssRequestWrapper extends HttpServletRequestWrapper {


	        private static final Pattern [] XSS_PATTERNS = {
	                Pattern.compile( "<.*?>" ),
	                Pattern.compile( "&.*?;" ),
	                Pattern.compile( "%[0-9a-fA-F]*" )
	        };
	        

	        public XssRequestWrapper(HttpServletRequest servletRequest) {
	            super(servletRequest);
	        }

	        @Override
	        public String[] getParameterValues( String parameterName ) {
	            
	            String [] values = super.getParameterValues(parameterName);

	            if (values == null) return null;

	            int count = values.length;
	            for ( int i = 0; i < count; i++ ) {
	                // On remplace chaque chaîne de caractères
	                values[i] = removeTags(values[i]);
	            }

	            return values;
	        }
	        
	        @Override
	        public String getParameter( String parameter ) {
	            return removeTags( super.getParameter(parameter) );
	        }

	        @Override
	        public String getHeader( String name ) {
	            return removeTags( super.getHeader(name) );
	        }

	        
	        private String removeTags( String value ) {
	            if ( value != null ) {
	                // remove ASCII code 0
	                value = value.replaceAll( "\0", "" );

	                // delete all elements found in XSS_PATTERNS
	                for ( Pattern pattern : XSS_PATTERNS ) {
	                    value = pattern.matcher( value ).replaceAll( "" );
	                }
	                
	                //If < and > are used
	                value = value.replaceAll( "<", "" );
	                value = value.replaceAll( ">", "" );
	            }
	            return value;
	        }
	    }

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
