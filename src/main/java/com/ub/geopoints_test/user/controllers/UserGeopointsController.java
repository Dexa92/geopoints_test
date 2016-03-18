package com.ub.geopoints_test.user.controllers;


import com.ub.core.base.utils.RouteUtils;
import com.ub.core.base.views.breadcrumbs.Breadcrumbs;
import com.ub.core.security.service.AutorizationService;
import com.ub.core.security.service.exceptions.UserNotAutorizedException;
import com.ub.core.user.models.UserDoc;
import com.ub.core.user.service.UserService;
import com.ub.core.user.service.exceptions.UserExistException;
import com.ub.geopoints_test.dots.models.DotGeopointsDoc;
import com.ub.geopoints_test.index.services.IndexGeopointsService;
import com.ub.geopoints_test.tracks.exceptions.TrackNotExistException;
import com.ub.geopoints_test.tracks.models.TracksGeopointsDoc;
import com.ub.geopoints_test.tracks.services.TracksGeopointsService;
import com.ub.geopoints_test.tracks.views.SearchGeopointsTrackRequest;
import com.ub.geopoints_test.tracks.views.SearchGeopointsTrackResponse;
import com.ub.geopoints_test.user.routes.UserGeopointsRoutes;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
public class UserGeopointsController {

    private static final String REG_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String REG_PASSWORD = "^(?=\\S+$).{6,}$";
    public static final String EMAIL_SEND_FROM = "info@geopoints.com";

    @Autowired
    private UserService userService;
    @Autowired
    private TracksGeopointsService tracksGeopointsService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private AutorizationService autorizationService;
    @Autowired
    private IndexGeopointsService indexGeopointsService;

    @RequestMapping(value = UserGeopointsRoutes.ROOT, method = RequestMethod.GET)
    public String lk(@RequestParam(required = false, defaultValue = "0") Integer currentPage, Model model){

        try {
            UserDoc userDoc = autorizationService.getUserFromSession();
            SearchGeopointsTrackRequest searchGeopointsTrackRequest = new SearchGeopointsTrackRequest(currentPage);
            SearchGeopointsTrackResponse searchGeopointsTrackResponse = tracksGeopointsService.findAllTracksOfUser(searchGeopointsTrackRequest, userDoc.getId());
            Map<ObjectId, List<Double>> latsMap = new HashMap<>();
            Map<ObjectId, List<Double>> lonsMap = new HashMap<>();
            for (TracksGeopointsDoc track : searchGeopointsTrackResponse.getResult()){

                try {
                    List<DotGeopointsDoc> dots = tracksGeopointsService.getAllTrackDots(track.getId());
                    List<Double> lats = new ArrayList<>();
                    List<Double> lons = new ArrayList<>();
                    for (DotGeopointsDoc dotGeopointsDoc : dots){
                        lats.add(dotGeopointsDoc.getLat());
                        lons.add(dotGeopointsDoc.getLon());
                    }
                    latsMap.put(track.getId(), lats);
                    lonsMap.put(track.getId(), lons);
                    model.addAttribute("latsMap", latsMap);
                    model.addAttribute("lonsMap", lonsMap);
                } catch (TrackNotExistException e) {
                    e.printStackTrace();
                }

            }
            model.addAttribute("searchGeopointsTrackResponse", searchGeopointsTrackResponse);

            Breadcrumbs breadcrumbs = indexGeopointsService.breadcrumbs();
            breadcrumbs.setCurrentPageTitle("Личный кабинет");

            model.addAttribute("breadcrumbs", breadcrumbs);
        } catch (UserNotAutorizedException e) {
            e.printStackTrace();
        }

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
