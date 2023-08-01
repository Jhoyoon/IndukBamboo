package com.instorage.myproject.filter;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@WebFilter(urlPatterns="/*")
public class PostLimitFilter implements Filter {
    //1분당 보낼수 있는 최대 post 요청 회수
    private static final int MAX_POST_REQUESTS_PER_MINUTE = 40;
    // 1초간 보낼수 있는 최대 POST 요청 회수
    private int MAX_POST_REQUESTS_PER_SECOND = 10;
    // 유저가 차단되는 시간
    private static final int BLOCK_TIME_MINUTES = 1;

    private Map<String, Deque<Long>> postRequestsMapPerMinute = new HashMap<>();
    private Map<String, Deque<Long>> postRequestsMapPerSecond = new HashMap<>();
    private Map<String, Long> blockMap = new HashMap<>();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // session을 통해서 유저를 구분한다.
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        String sessionId = session.getId();

        // post요청인지 확인한다.
        if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
            // 시스템상 현재 시간
            long currentTimeMillis = System.currentTimeMillis();

            // 만약 이미 유저가 차단당한 상태라면
            if (blockMap.containsKey(sessionId) && currentTimeMillis - blockMap.get(sessionId) <= TimeUnit.MINUTES.toMillis(BLOCK_TIME_MINUTES)) {
                // 필터는 spring context 이전에 발생한다.그렇기에 여기서 발생하는 에러는 반드시 서블릿 api에 의해 감지되기에
                // 반드시 servlet api가 제대로 감지할수 있는 에러를 보내줘야 한다.
                // 그렇지 않으면 의도치 않은 serclet api 에러가 발생한다
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendError(HttpStatus.TOO_MANY_REQUESTS.value());
                return;
            }

            // 1분 넘은애들 제거
            Deque<Long> postRequestTimesMinute = postRequestsMapPerMinute.get(sessionId);
            if (postRequestTimesMinute == null) {
                postRequestTimesMinute = new ArrayDeque<>();
                postRequestsMapPerMinute.put(sessionId, postRequestTimesMinute);
            }
            postRequestTimesMinute.add(currentTimeMillis);
            while (!postRequestTimesMinute.isEmpty() && currentTimeMillis - postRequestTimesMinute.peekFirst() > TimeUnit.MINUTES.toMillis(1)) {
                postRequestTimesMinute.pollFirst();
            }

            // 1초 넘은애들 제거
            Deque<Long> postRequestTimesSecond = postRequestsMapPerSecond.get(sessionId);
            if (postRequestTimesSecond == null) {
                postRequestTimesSecond = new ArrayDeque<>();
                postRequestsMapPerSecond.put(sessionId, postRequestTimesSecond);
            }
            postRequestTimesSecond.add(currentTimeMillis);
            while (!postRequestTimesSecond.isEmpty() && currentTimeMillis - postRequestTimesSecond.peekFirst() > TimeUnit.SECONDS.toMillis(1)) {
                postRequestTimesSecond.pollFirst();
            }
            
            // 분당 초당 post 요청 제한
            if (postRequestTimesMinute.size() > MAX_POST_REQUESTS_PER_MINUTE || postRequestTimesSecond.size() > MAX_POST_REQUESTS_PER_SECOND) {
                blockMap.put(sessionId, currentTimeMillis);
                // 필터는 spring context 이전에 발생한다.그렇기에 여기서 발생하는 에러는 반드시 서블릿 api에 의해 감지되기에
                // 반드시 servlet api가 제대로 감지할수 있는 에러를 보내줘야 한다.
                // 그렇지 않으면 의도치 않은 serclet api 에러가 발생한다
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendError(HttpStatus.TOO_MANY_REQUESTS.value());
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}