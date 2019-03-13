package com.csthink.bbs.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharsetEncodingFilter implements Filter {

    private String encoding;

    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
