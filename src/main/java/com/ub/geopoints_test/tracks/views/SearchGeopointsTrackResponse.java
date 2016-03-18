package com.ub.geopoints_test.tracks.views;

import com.ub.core.base.search.SearchResponse;
import com.ub.geopoints_test.tracks.models.TracksGeopointsDoc;

import java.util.List;

public class SearchGeopointsTrackResponse extends SearchResponse {

    private List<TracksGeopointsDoc> result;

    public SearchGeopointsTrackResponse(Integer currentPage, Integer pageSize, List<TracksGeopointsDoc> result) {
        this.result = result;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public SearchGeopointsTrackResponse(Integer currentPage, List<TracksGeopointsDoc> result) {
        this.result = result;
        this.currentPage = currentPage;
    }

    public List<TracksGeopointsDoc> getResult() {
        return result;
    }

    public void setResult(List<TracksGeopointsDoc> result) {
        this.result = result;
    }

}
