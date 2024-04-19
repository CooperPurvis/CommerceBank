package org.example.commercebank.Authentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
//public class ApiKeyAuthFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        // Get the API key and secret from request headers
//        String requestApiKey = request.getHeader("X-API-KEY");
//
//        // Validate the key and secret
//        String apiKey = "1acea32f-f9fd-4864-b05e-685bdd3048b0";
//        if (apiKey.equals(requestApiKey)) {
//            // Continue processing the request
//            filterChain.doFilter(request, response);
//        } else {
//            // Reject the request and send an unauthorized error
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            response.getWriter().write("Unauthorized");
//        }
//    }
//}
