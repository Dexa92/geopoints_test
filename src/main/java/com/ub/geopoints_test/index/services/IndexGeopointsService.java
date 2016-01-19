package com.ub.geopoints_test.index.services;

import com.ub.core.base.views.breadcrumbs.Breadcrumbs;
import com.ub.core.base.views.breadcrumbs.BreadcrumbsLink;
import org.springframework.stereotype.Component;

@Component
public class IndexGeopointsService {

    public Breadcrumbs breadcrumbs(){

        Breadcrumbs breadcrumbs = new Breadcrumbs();
        breadcrumbs.getLinks().add(new BreadcrumbsLink("/","Главная"));

        return breadcrumbs;

    }

}
