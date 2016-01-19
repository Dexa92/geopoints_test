package com.ub.geopoints_test.index.controllers;


import com.ub.core.base.views.breadcrumbs.Breadcrumbs;
import com.ub.geopoints_test.index.routes.IndexGeopointsRoutes;
import com.ub.geopoints_test.index.services.IndexGeopointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexGeopointsController {

    @Autowired
    IndexGeopointsService indexGeopointsService;

    @RequestMapping(value = IndexGeopointsRoutes.ROOT, method = RequestMethod.GET)
    public String index(Model model){

        Breadcrumbs breadcrumbs = indexGeopointsService.breadcrumbs();

        model.addAttribute("breadcrumbs", breadcrumbs);

        return "com.ub.geopoints_test.index";

    }

}
