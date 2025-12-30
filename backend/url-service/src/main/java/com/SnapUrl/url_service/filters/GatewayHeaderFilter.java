package com.SnapUrl.url_service.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
public class GatewayHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        String path = request.getRequestURI();

        // public short URL
        if (path.matches("^/[a-zA-Z0-9]+$")) {
            chain.doFilter(request, response);
            return;
        }

        // internal APIs
        String userId = request.getHeader("X-User-Id");
        //System.out.println(userId);
        if (userId == null) {
            response.setStatus(401);
            response.getWriter().write("Unauthenticated");
            return;
        }

        chain.doFilter(request, response);
    }
}

