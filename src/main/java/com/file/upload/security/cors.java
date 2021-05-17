package com.file.upload.security;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class cors extends GenericFilterBean implements Filter {




        /**
         * CORS Filter
         *
         * This filter is an implementation of W3C's CORS
         * (Cross-Origin Resource Sharing) specification,
         * which is a mechanism that enables cross-origin requests.
         *
         */

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {

            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpResponse.setHeader("Access-Control-Allow-Methods", "*");
            //httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");

            httpResponse.setHeader("Access-Control-Allow-Headers", "*");
            //httpResponse.setHeader("Access-Control-Allow-Headers",
            //      "Accept, Authorization");

            httpResponse.setHeader("Access-Control-Allow-Credentials", "false");
            httpResponse.setHeader("Access-Control-Max-Age", "3600");

            System.out.println("********** CORS Configuration Completed **********");

            chain.doFilter(request, response);
        }



} // The End...
