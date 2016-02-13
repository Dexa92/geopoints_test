package com.ub.geopoints_test.dots.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DotGeopointsDoc {

    @Id
    private ObjectId id;

    private String lon;
    private String lat;
    private String time;
    private String ele;

    private ObjectId trackId;

    public DotGeopointsDoc() {

        lon = "";
        lat = "";
        time = "";
        ele = "";

    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEle() {
        return ele;
    }

    public void setEle(String ele) {
        this.ele = ele;
    }

    public ObjectId getTrackId() {
        return trackId;
    }

    public void setTrackId(ObjectId trackId) {
        this.trackId = trackId;
    }
}
