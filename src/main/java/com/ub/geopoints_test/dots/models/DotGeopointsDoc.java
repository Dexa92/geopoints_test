package com.ub.geopoints_test.dots.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DotGeopointsDoc {

    @Id
    private ObjectId id;

    private Double lon;
    private Double lat;
    private String time;
    private Double ele;

    private ObjectId trackId;

    public DotGeopointsDoc() {

        lon = 0.0;
        lat = 0.0;
        time = "";
        ele = 0.0;

    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getEle() {
        return ele;
    }

    public void setEle(Double ele) {
        this.ele = ele;
    }

    public ObjectId getTrackId() {
        return trackId;
    }

    public void setTrackId(ObjectId trackId) {
        this.trackId = trackId;
    }
}
