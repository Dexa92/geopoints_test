package com.ub.geopoints_test.tracks.controllers;

import com.ub.core.base.views.breadcrumbs.Breadcrumbs;
import com.ub.core.file.services.FileService;
import com.ub.geopoints_test.dots.models.DotGeopointsDoc;
import com.ub.geopoints_test.dots.services.MySaxParser;
import com.ub.geopoints_test.index.services.IndexGeopointsService;
import com.ub.geopoints_test.tracks.models.TracksGeopointsDoc;
import com.ub.geopoints_test.tracks.routes.TracksGeopointsRoutes;
import com.ub.geopoints_test.tracks.services.TracksGeopointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class TracksGeopointsController {

    @Autowired
    private IndexGeopointsService indexGeopointsService;
    @Autowired
    private TracksGeopointsService tracksGeopointsService;
    @Autowired
    private FileService fileService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MySaxParser mySaxParser;

    @RequestMapping(value = TracksGeopointsRoutes.TRACKS, method = RequestMethod.GET)
    public String tracks(Model model){

        Breadcrumbs breadcrumbs = indexGeopointsService.breadcrumbs();
        breadcrumbs.setCurrentPageTitle("GPS треки");

        model.addAttribute("breadcrumbs", breadcrumbs);

        return "com.ub.geopoints_test.tracks";

    }

    @RequestMapping(value = TracksGeopointsRoutes.TRACKS, method = RequestMethod.POST)
    public String tracks(@RequestParam MultipartFile file){

        TracksGeopointsDoc tracksGeopointsDoc = new TracksGeopointsDoc();
        //ObjectId fileId = fileService.saveWithDelete(file, null);
        //dotGeopointsDoc.setFile(fileId);
        DotGeopointsDoc dotGeopointsDoc = new DotGeopointsDoc();
        try {
            File convFile = tracksGeopointsService.convert(file);
            List<TracksGeopointsDoc> tracks = tracksGeopointsService.findAllTracks();
            if (tracks.size() != 0) {
                for (TracksGeopointsDoc track : tracks) {
                    if (track.getFile() != convFile) {
                        tracksGeopointsDoc.setFile(convFile);
                        tracksGeopointsService.saveTrack(tracksGeopointsDoc);
                    }
                }
            } else {
                tracksGeopointsDoc.setFile(convFile);
               tracksGeopointsService.saveTrack(tracksGeopointsDoc);


                String s = tracksGeopointsDoc.getFile().getAbsolutePath();

                mySaxParser.setUpMySaxParser(tracksGeopointsDoc.getFile().getAbsolutePath(), convFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "com.ub.geopoints_test.index";

    }

}
