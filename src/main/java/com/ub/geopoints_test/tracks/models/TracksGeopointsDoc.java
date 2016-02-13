package com.ub.geopoints_test.tracks.models;

import com.ub.geopoints_test.dots.models.DotGeopointsDoc;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Document
public class TracksGeopointsDoc {

    @Id
    private ObjectId id;

    private List<DotGeopointsDoc> dots = new ArrayList<DotGeopointsDoc>();
    private File file;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public List<DotGeopointsDoc> getDots() {
        return dots;
    }

    public void setDots(List<DotGeopointsDoc> dots) {
        this.dots = dots;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
