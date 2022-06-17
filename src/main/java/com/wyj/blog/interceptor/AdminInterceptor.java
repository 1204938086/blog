package com.wyj.blog.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyj.blog.config.BlogUserConfig;
import com.wyj.blog.utils.TokenUtils;
import com.wyj.blog.vo.TokenVO;
import org.commonmark.node.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AdminInterceptor implements HandlerInterceptor {

    private BlogUserConfig blogUserConfig;

    public AdminInterceptor(BlogUserConfig blogUserConfig){
        this.blogUserConfig = blogUserConfig;
    }

    /**
     * 请求处理前，可用于拦截请求，权限认证等
     * @param request 请求信息
     * @param response 响应信息
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取token
        String token = request.getHeader("Authorization");
        // 无条件放行 options 方法，防止因浏览器预先执行的options请求导致的CORS错误
        if("OPTIONS".equals(request.getMethod().toUpperCase())) {
            return true;
        }

        Integer ret = TokenUtils.VerifyToken(token,blogUserConfig.getPassword());
        if (ret == 1){
            return true;
        }else{
            tokenFalseResult(response,ret);
            return false;
        }
    }

    /**
     * 验证失败时，如何处理返回信息
     * @param response
     * @param ret
     * @throws IOException
     */
    public void tokenFalseResult(HttpServletResponse response,Integer ret) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        TokenVO resultBody = null;
        if (ret == -1){
            resultBody = new TokenVO(false, TokenVO.TokenCode.ERROR);
        }else if (ret == 0){
            resultBody = new TokenVO(false, TokenVO.TokenCode.TIMEOUT);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().println(objectMapper.writeValueAsString(resultBody));
        return;
    }
    /**
     * 请求处理完成后，视图生成之前，后处理，可用于修改ModelAndView
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }


}
