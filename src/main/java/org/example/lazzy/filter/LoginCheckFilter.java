package org.example.lazzy.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.lazzy.common.BaseContext;
import org.example.lazzy.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  verify the login status
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*") // filter all requests
public class LoginCheckFilter implements Filter {

    // Spring util class, used to match path and support wild card.
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        log.info("get the path: {}", request.getRequestURI());
        log.info("current Thread id in filter ---> {}", Thread.currentThread().getId());

        // define an url array which contains the uris that do not need to deal with
        String[] urls = new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/login",
//                "/user/sendMsg"
                // below four are used for knife4j (swagger)
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };


        // 1. get the request URI from current request
        String uri = request.getRequestURI();

        // 2. determine whether to deal with this request
        boolean isChecked = checkURI(urls, uri);
        // 3. pass the request if it does not need deal with
        if(isChecked){
            log.info("{} request does not need to be processed", uri);
            // pass
            filterChain.doFilter(request, response);
            return;
        }
        // 4. check the login status for employee

        Long id = (Long) request.getSession().getAttribute("employee");
        if(id != null) {
            // user already login, store the id in threadLocal
            BaseContext.setCurrentId(id);

            log.info("user already login, user id is {}", id);
            // user already login, pass
            filterChain.doFilter(request, response);
            return;
        }

        // 4.1 check the login status for user
        Long userId = (Long) request.getSession().getAttribute("user");
        if(userId != null) {
            // user already login, store the id in threadLocal
            BaseContext.setCurrentId(userId);

            log.info("user already login, user id is {}", userId);
            // user already login, pass
            filterChain.doFilter(request, response);
            return;
        }

        // user does not login
        // user response object to write Result.error to front
        log.info("user does not login");
        String errorMsg = JSON.toJSONString(Result.error("NOTLOGIN"));
        response.getWriter().write(errorMsg);
    }

    // helper function. Determine whether the uri  can be matched in the urls
    private boolean checkURI(String[] urls, String uri){
        for (String url: urls){
            if(PATH_MATCHER.match(url, uri)){
                return true;
            }
        }

        return false;
    }
}


