package com.instorage.myproject.filter;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@WebFilter(urlPatterns="/*")
public class AccessLimiterFilter implements Filter {
    // 1분당 최대 get 횟수
    private static final int MAX_REQUESTS_PER_MINUTE = 400;
    // 1초당 최대 get 횟수
    private static final int MAX_REQUESTS_PER_SECOND = 60;
    // 차단되는 시간
    private static final int BLOCK_TIME_SECONDS = 10;
    private Map<String, Deque<Long>> requestsMapMinute = new HashMap<>();
    private Map<String, Deque<Long>> requestsMapSecond = new HashMap<>();
    private Map<String, Long> blockMap = new HashMap<>();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // ip로 유저를 구분한다
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String clientIP = getClientIP(httpRequest);
        long currentTimeMillis = System.currentTimeMillis();

        // 차단되어 있다면 10초간 접근을 막는다.
        if (blockMap.containsKey(clientIP) && currentTimeMillis - blockMap.get(clientIP) <= TimeUnit.SECONDS.toMillis(BLOCK_TIME_SECONDS)) {
            // 필터는 spring context 이전에 발생한다.그렇기에 여기서 발생하는 에러는 반드시 서블릿 api에 의해 감지되기에
            // 반드시 servlet api가 제대로 감지할수 있는 에러를 보내줘야 한다.
            // 그렇지 않으면 의도치 않은 servlet api 에러가 발생한다
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(HttpStatus.TOO_MANY_REQUESTS.value());
            return;

        }

        // 1분이 넘었을시 덱에서 제거한다
        Deque<Long> requestTimesMinute = requestsMapMinute.get(clientIP);
        if (requestTimesMinute == null) {
            requestTimesMinute = new ArrayDeque<>();
            requestsMapMinute.put(clientIP, requestTimesMinute);
        }
        requestTimesMinute.add(currentTimeMillis);
        while (!requestTimesMinute.isEmpty() && currentTimeMillis - requestTimesMinute.peekFirst() > TimeUnit.MINUTES.toMillis(1)) {
            requestTimesMinute.pollFirst();
        }

        // 1초가 넘었을시 덱에서 제거한다.
        Deque<Long> requestTimesSecond = requestsMapSecond.get(clientIP);
        if (requestTimesSecond == null) {
            requestTimesSecond = new ArrayDeque<>();
            requestsMapSecond.put(clientIP, requestTimesSecond);
        }
        requestTimesSecond.add(currentTimeMillis);
        while (!requestTimesSecond.isEmpty() && currentTimeMillis - requestTimesSecond.peekFirst() > TimeUnit.SECONDS.toMillis(1)) {
            requestTimesSecond.pollFirst();
        }

        // 10초간 차단한다.
        if (requestTimesMinute.size() > MAX_REQUESTS_PER_MINUTE || requestTimesSecond.size() > MAX_REQUESTS_PER_SECOND) {
            blockMap.put(clientIP, currentTimeMillis);
            // 필터는 spring context 이전에 발생한다.그렇기에 여기서 발생하는 에러는 반드시 서블릿 api에 의해 감지되기에
            // 반드시 servlet api가 제대로 감지할수 있는 에러를 보내줘야 한다.
            // 그렇지 않으면 의도치 않은 serclet api 에러가 발생한다
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(HttpStatus.TOO_MANY_REQUESTS.value());
            return;
        }

        chain.doFilter(request, response);
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}