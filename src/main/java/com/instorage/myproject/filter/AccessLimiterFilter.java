package com.instorage.myproject.filter;

import org.springframework.http.HttpStatus;

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

// 필터를 적용할 요청의 패턴 지정 - 모든 요청에 필터를 적용.
//@WebFilter(urlPatterns="/*")
//public class RataLimiterFilter implements Filter {
//    // 현재 동기 방식으로 요청을 수행하고 있다.병렬 처리 혹은 비동기 방식을 고안해야 한다.
//    private int MAX_REQUESTS_PER_MINUTE = 50;
//    private int MAX_REQUESTS_PER_SECOND = 4;
//    private Map<String, Deque<Long>> requestsMapMinute = new HashMap<>();
//    private Map<String, Deque<Long>> requestsMapSecond = new HashMap<>();
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        // 유저의 ip를 String 형태로 받아온다.
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        String clientIP = getClientIP(httpRequest);
//        // 현재 시스템상의 시간을 받아온다.
//        long currentTimeMillis = System.currentTimeMillis();
//        // 현재 requestMap에는 Map<clientIp : Deque<유저가 요청한 시간>>이 들어있다.
//        // 클라이언트의 요청 timestamp를 할당한다.
//        Deque<Long> requestTimesMinute = requestsMapMinute.get(clientIP);
//        // timestamp가 비어있다 == 1분간 요청을 보낸적이 없다.
//        if (requestTimesMinute == null) {
//            requestTimesMinute = new ArrayDeque<>();
//            requestsMapMinute.put(clientIP, requestTimesMinute);
//        }
//        // '요청시간'을 넣어준다
//        requestTimesMinute.add(currentTimeMillis);
//        // 만일 1분을 넘은 '요청시간'이 있을시 deque에서 제거해준다.
//        while (!requestTimesMinute.isEmpty() && currentTimeMillis - requestTimesMinute.peekFirst() > TimeUnit.MINUTES.toMillis(1)) {
//            requestTimesMinute.pollFirst();
//        }
//        // 초에도 똑같은 작업을 해준다.1초에 4번
//        Deque<Long> requestTimesSecond = requestsMapSecond.get(clientIP);
//        if (requestTimesSecond == null) {
//            requestTimesSecond = new ArrayDeque<>();
//            requestsMapSecond.put(clientIP, requestTimesSecond);
//        }
//        requestTimesSecond.add(currentTimeMillis);
//        while (!requestTimesSecond.isEmpty() && currentTimeMillis - requestTimesSecond.peekFirst() > TimeUnit.SECONDS.toMillis(1)) {
//            requestTimesSecond.pollFirst();
//        }
//        System.out.println("초당 요청을 보낸 횟수= "+requestTimesSecond.size());
//        System.out.println("분당 요청을 보낸 횟수= "+requestTimesMinute.size());
//        if (requestTimesMinute.size() > MAX_REQUESTS_PER_MINUTE || requestTimesSecond.size() > MAX_REQUESTS_PER_SECOND) {
//            HttpServletResponse httpResponse = (HttpServletResponse) response;
//            httpResponse.setContentType("text/plain; charset=UTF-8");
//            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
//            httpResponse.getWriter().write("너무 많은 요청을 보냈습니다.");
//            return;
//        }
//
//        chain.doFilter(request, response);
//    }
//    private String getClientIP(HttpServletRequest request) {
//        // 유저가 프록시 서버를 사용할시 ip에 얻어내기
//        String xfHeader = request.getHeader("X-Forwarded-For");
//        // 사용하지 않을시 ip 얻어내기
//        if (xfHeader == null){
//            return request.getRemoteAddr();
//        }
//        //유저가 여러개의 프록시 서버를 거쳤을시 ip가 배열의 형태로 여러개 전달된다
//        // 유저의 ip만 얻어내기 위해서 이렇게 작성
//        return xfHeader.split(",")[0];
//    }
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // 초기화 작업
//    }
//
//    @Override
//    public void destroy() {
//        // 정리 작업
//    }
//
//}
//@WebFilter(urlPatterns="/*")
//public class RataLimiterFilter implements Filter {
//    private static final int MAX_REQUESTS_PER_MINUTE = 50;
//    private static final int MAX_REQUESTS_PER_SECOND = 4;
//    private static final int BLOCK_TIME_SECONDS = 10;
//    private Map<String, Deque<Long>> requestsMapMinute = new HashMap<>();
//    private Map<String, Deque<Long>> requestsMapSecond = new HashMap<>();
//    private Map<String, Long> blockMap = new HashMap<>();
//
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        String clientIP = getClientIP(httpRequest);
//        long currentTimeMillis = System.currentTimeMillis();
//
//        // Check if user is blocked
//        if (blockMap.containsKey(clientIP) && currentTimeMillis - blockMap.get(clientIP) <= TimeUnit.SECONDS.toMillis(BLOCK_TIME_SECONDS)) {
//            HttpServletResponse httpResponse = (HttpServletResponse) response;
//            httpResponse.setContentType("text/plain; charset=UTF-8");
//            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
//            httpResponse.getWriter().write("너무 많은 요청을 보냈습니다. 10초 동안 요청을 보낼 수 없습니다.");
//            return;
//        }
//
//        // Check requests per minute
//        Deque<Long> requestTimesMinute = requestsMapMinute.get(clientIP);
//        if (requestTimesMinute == null) {
//            requestTimesMinute = new ArrayDeque<>();
//            requestsMapMinute.put(clientIP, requestTimesMinute);
//        }
//        requestTimesMinute.add(currentTimeMillis);
//        while (!requestTimesMinute.isEmpty() && currentTimeMillis - requestTimesMinute.peekFirst() > TimeUnit.MINUTES.toMillis(1)) {
//            requestTimesMinute.pollFirst();
//        }
//
//        // Check requests per second
//        Deque<Long> requestTimesSecond = requestsMapSecond.get(clientIP);
//        if (requestTimesSecond == null) {
//            requestTimesSecond = new ArrayDeque<>();
//            requestsMapSecond.put(clientIP, requestTimesSecond);
//        }
//        requestTimesSecond.add(currentTimeMillis);
//        while (!requestTimesSecond.isEmpty() && currentTimeMillis - requestTimesSecond.peekFirst() > TimeUnit.SECONDS.toMillis(1)) {
//            requestTimesSecond.pollFirst();
//        }
//
//        // If exceeded, add user to block map
//        if (requestTimesMinute.size() > MAX_REQUESTS_PER_MINUTE || requestTimesSecond.size() > MAX_REQUESTS_PER_SECOND) {
//            blockMap.put(clientIP, currentTimeMillis);
//            HttpServletResponse httpResponse = (HttpServletResponse) response;
//            httpResponse.setContentType("text/plain; charset=UTF-8");
//            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
//            httpResponse.getWriter().write("너무 많은 요청을 보냈습니다. 10초 동안 요청을 보낼 수 없습니다.");
//            return;
//        }
//
//        chain.doFilter(request, response);
//    }
//
//    private String getClientIP(HttpServletRequest request) {
//        String xfHeader = request.getHeader("X-Forwarded-For");
//        if (xfHeader == null){
//            return request.getRemoteAddr();
//        }
//        return xfHeader.split(",")[0];
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void destroy() {
//    }
//}
@WebFilter(urlPatterns="/*")
public class AccessLimiterFilter implements Filter {
    // 1분당 최대 get 횟수
    private static final int MAX_REQUESTS_PER_MINUTE = 300;
    // 1초당 최대 get 횟수
    private static final int MAX_REQUESTS_PER_SECOND = 2;
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
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("text/plain; charset=UTF-8");
            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResponse.getWriter().write("너무 많은 요청을 보냈습니다. 10초 동안 요청을 보낼 수 없습니다.");
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
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("text/plain; charset=UTF-8");
            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResponse.getWriter().write("너무 많은 요청을 보냈습니다. 10초 동안 요청을 보낼 수 없습니다.");
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