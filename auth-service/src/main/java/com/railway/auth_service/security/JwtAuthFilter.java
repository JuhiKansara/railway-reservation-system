//Request arrives
//  → Read "Authorization" header
//  → Does it start with "Bearer "?
//      NO  → skip filter, pass request through (SecurityConfig decides)
//      YES → strip "Bearer ", get the raw token
//          → isTokenValid()?
//              NO  → don't set auth, pass through (SecurityConfig blocks it)
//              YES → extract email + role
//                  → create Authentication object
//                  → store in SecurityContextHolder
//                  → pass request through (now marked as authenticated)

package com.railway.auth_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
//OncePerRequestFilter guarantees this runs exactly once per request even in complex filter chains.
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException{
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(7);

        if (jwtUtil.isTokenValid(token)){
            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);

            var auth = new UsernamePasswordAuthenticationToken(email, null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));
            //SecurityContextHolder is Spring Security's thread-local storage — whatever you put in here is readable by Spring Security for the rest of that request's lifecycle.
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request,response);
    }
}
