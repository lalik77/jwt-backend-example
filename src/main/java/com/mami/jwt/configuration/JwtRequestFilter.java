package com.mami.jwt.configuration;

import com.mami.jwt.controller.JwtController;
import com.mami.jwt.service.JwtService;
import com.mami.jwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtController.class);

    private JwtService jwtService;
    private JwtUtil jwtUtil;

    public JwtRequestFilter(JwtService jwtService, JwtUtil jwtUtil) {
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwtToken = null;

        if (header == null || !header.startsWith("Bearer ")) {

            logger.info("JwtRequestFilter#doFilterInternal() + header: {}", header);
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = header.split(" ")[1].trim();
        final UserDetails userDetails = jwtService.loadUserByUsername(jwtUtil.getUsernameFromToken(jwtToken));

        if (!jwtUtil.isValidToken(jwtToken, userDetails)) {
            System.out.println("2");
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }
}
