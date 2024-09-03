package net.cloud.interceptor;

import io.jsonwebtoken.Claims;
import net.cloud.enums.BizCodeEnum;
import net.cloud.model.LoginUser;
import net.cloud.util.CommonUtil;
import net.cloud.util.JsonData;
import net.cloud.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("token");
        if(accessToken==null){
            accessToken = request.getParameter("token");
        }
        if(StringUtils.isNotBlank(accessToken)){
            Claims claims = JwtUtil.verifyJwtWebToken(accessToken);
            if(claims==null){
                CommonUtil.sendJsonObject(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_NOT_LOGIN));
                return false;
            }
            long userId = Long.parseLong(claims.get("id").toString());
            String name = claims.get("name").toString();
            String headImg = claims.get("head_img").toString();
            String mail = claims.get("mail").toString();
            String ipAddr = claims.get("ip_addr").toString();

            LoginUser loginUser = new LoginUser();
            loginUser.setId(userId);
            loginUser.setMail(mail);
            loginUser.setHeadImg(headImg);
            loginUser.setName(name);
            loginUser.setIpAddr(ipAddr);
            // request set attribute or thread local
            threadLocal.set(loginUser);
            return true;
        }
        CommonUtil.sendJsonObject(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_NOT_LOGIN));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
