package com.ub.geopoints_test.user.controllers;


import com.ub.core.base.utils.RouteUtils;
import com.ub.core.security.service.AutorizationService;
import com.ub.core.security.service.exceptions.UserNotAutorizedException;
import com.ub.core.user.service.UserService;
import com.ub.core.user.service.exceptions.UserExistException;
import com.ub.geopoints_test.user.routes.UserGeopointsRoutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.regex.Pattern;

@Controller
public class UserGeopointsController {

    private static final String REG_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String REG_PASSWORD = "^(?=\\S+$).{6,}$";
    public static final String EMAIL_SEND_FROM = "info@geopoints.com";

    @Autowired UserService userService;
    //Autowired UserGeopointsService userGeopointsService;
    @Autowired JavaMailSender javaMailSender;
    @Autowired AutorizationService autorizationService;

    @RequestMapping(value = UserGeopointsRoutes.ROOT, method = RequestMethod.GET)
    public String lk(Model model){



        return "com.ub.geopoints_test.user.lk";

    }

    @ResponseBody
    @RequestMapping(value = UserGeopointsRoutes.REGISTRATION, method = RequestMethod.POST)
    public String registration(@RequestParam String email,
                               @RequestParam String password,
                               Model model){

        if (!Pattern.compile(REG_EMAIL).matcher(email).matches())
            return "3";
        if (!password.matches(REG_PASSWORD) || password.length() < 6)
            return "2";
        try {
            userService.createUserByEmail(email, password);
        } catch (UserExistException e) {
            return "1";
        }
        return "ok";

    }

    @ResponseBody
    @RequestMapping(value = UserGeopointsRoutes.LOGIN, method = RequestMethod.POST)
    public String login(@RequestParam String email,
                        @RequestParam String password) {
        try {
            autorizationService.autorizeEmail(email, password);
        } catch (UserNotAutorizedException e) {
            return "1";
        }
        return "ok";
    }

    @RequestMapping(value = UserGeopointsRoutes.LOGOUT, method = RequestMethod.GET)
    public String logout(){

        autorizationService.logout();
        return RouteUtils.redirectTo("/");

    }

}
