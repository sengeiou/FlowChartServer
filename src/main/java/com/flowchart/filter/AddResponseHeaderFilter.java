package com.flowchart.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @className: AddResponseHeaderFilter
 * @Description: TODO
 * @author: ct
 * @date: 2022/5/9 16:27
 */
@Component
public class AddResponseHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.err.println(httpServletRequest.getRequestURL());
//        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}