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
    private static final int MAX_POST_REQUESTS_PER_MINUTE = 50;
    // 1초간 보낼수 있는 최대 POST 요청 회수
    private int MAX_POST_REQUESTS_PER_SECOND = 6;
    // 유저가 차단되는 시간
    private static final int BLOCK_TIME_MINUTES = 5;

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
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setContentType("text/plain; charset=UTF-8");
                httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                httpResponse.getWriter().write("너무 많은 요청을 보냈습니다. 5분 동안 요청을 보낼 수 없습니다.");
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
            Deque<Long> postRequestTimesSecond = postRequestsMapPerMinute.get(sessionId);
            if (postRequestTimesSecond == null) {
                postRequestTimesSecond = new ArrayDeque<>();
                postRequestsMapPerMinute.put(sessionId, postRequestTimesSecond);
            }
            postRequestTimesSecond.add(currentTimeMillis);
            while (!postRequestTimesSecond.isEmpty() && currentTimeMillis - postRequestTimesSecond.peekFirst() > TimeUnit.SECONDS.toMillis(1)) {
                postRequestTimesSecond.pollFirst();
            }
            
            // 분당 초당 post 요청 제한
            if (postRequestTimesMinute.size() > MAX_POST_REQUESTS_PER_MINUTE || postRequestTimesSecond.size() > MAX_POST_REQUESTS_PER_SECOND) {
                blockMap.put(sessionId, currentTimeMillis);
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setContentType("text/plain; charset=UTF-8");
                httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                httpResponse.getWriter().write("너무 많은 요청을 보냈습니다. 5분 동안 요청을 보낼 수 없습니다.");
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