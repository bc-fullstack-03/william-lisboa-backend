package com.sysmap.parrot.jwt;

import com.sysmap.parrot.service.user.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    private final UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String token = request.getHeader("Authorization");
        final String userId = request.getHeader("userId");


        if (token == null || userId == null || !token.startsWith("Bearer ")) {
            response.getWriter().write("User not Authenticated!");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        String authToken = jwtTokenUtil.parseJwt(request);

        if (!jwtTokenUtil.isValidToken(authToken,userId)) {
            response.getWriter().write("Invalid tokens!");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        chain.doFilter(request,response);
    }
}
