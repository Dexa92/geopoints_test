package com.ub.geopoints_test.tracks.models;

import com.ub.geopoints_test.dots.models.DotGeopointsDoc;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
public class TracksGeopointsDoc {

    @Id
    private ObjectId id;

    private String name = "";
    private Boolean available = true;
    private String description = "";
    private List<DotGeopointsDoc> dots = new ArrayList<DotGeopointsDoc>();
    private ObjectId file;
    private String url = "";
    @NotNull
    private Date uploadDate;
    private ObjectId userId;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DotGeopointsDoc> getDots() {
        return dots;
    }

    public void setDots(List<DotGeopointsDoc> dots) {
        this.dots = dots;
    }

    public ObjectId getFile() {
        return file;
    }

    public void setFile(ObjectId file) {
        this.file = file;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }
}
