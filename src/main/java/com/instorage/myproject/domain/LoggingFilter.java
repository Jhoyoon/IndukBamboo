package com.instorage.myproject.domain;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

// 필터를 적용할 요청의 패턴 지정 - 모든 요청에 필터를 적용.
@WebFilter(urlPatterns="/*")
public class LoggingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 작업
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        long startTime = System.currentTimeMillis();

        // 2. 서블릿 또는 다음 필터를 호출
        chain.doFilter(request, response);

        // 3. 후처리 작업
        HttpServletRequest req = (HttpServletRequest)request;
        // 현재 요청을 보내는 클라이언트가 어떤 url에 머물렀는지 가져온다
        String referer = req.getHeader("referer");
        // 요청의 종류를 반환한다.get,post등등
        String method =  req.getMethod();
        // 콘솔에 출력해줌
        System.out.print("["+referer+"]"+"->"+"["+method+"]"+"["+((HttpServletRequest)request).getRequestURI()+"]");
        System.out.println(" 소요시간="+(System.currentTimeMillis()-startTime)+"ms");
    }

    @Override
    public void destroy() {
        // 정리 작업
    }

}