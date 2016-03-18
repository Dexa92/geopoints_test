package com.ub.geopoints_test.tracks.views;

import com.ub.core.base.search.SearchRequest;

public class SearchGeopointsTrackRequest extends SearchRequest {

    public SearchGeopointsTrackRequest() {
    }

    public SearchGeopointsTrackRequest(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
