package com.ub.geopoints_test.index.interceptions;

import com.ub.core.security.service.AutorizationService;
import com.ub.core.security.service.exceptions.UserNotAutorizedException;
import com.ub.core.user.models.UserDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interceptor extends HandlerInterceptorAdapter {

    @Autowired AutorizationService autorizationService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {
            modelAndView.addObject("userAuthorized", false);
            try {
                UserDoc userDoc = autorizationService.getUserFromSession();
                modelAndView.addObject("userAuthorized", true);
                modelAndView.addObject("userDoc", userDoc);
            } catch (UserNotAutorizedException e) {

            }
        }


    }

}
