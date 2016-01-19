package com.ub.geopoints_test.about.controllers;

import com.ub.core.base.views.breadcrumbs.Breadcrumbs;
import com.ub.geopoints_test.about.routes.AboutGeopointsRoutes;
import com.ub.geopoints_test.index.services.IndexGeopointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AboutGeopointsController {

    @Autowired
    IndexGeopointsService indexGeopointsService;

    @RequestMapping(value = AboutGeopointsRoutes.ABOUT, method = RequestMethod.GET)
    public String about(Model model){

        Breadcrumbs breadcrumbs = indexGeopointsService.breadcrumbs();
        breadcrumbs.setCurrentPageTitle("О проекте");

        model.addAttribute("breadcrumbs", breadcrumbs);

        return "com.ub.geopoints_test.about";

    }

}
