package com.ub.geopoints_test.tracks.controllers;

import com.ub.core.base.httpResponse.ResourceNotFoundException;
import com.ub.core.base.utils.RouteUtils;
import com.ub.core.base.views.breadcrumbs.Breadcrumbs;
import com.ub.core.file.services.FileService;
import com.ub.geopoints_test.dots.models.DotGeopointsDoc;
import com.ub.geopoints_test.dots.services.MySaxParser;
import com.ub.geopoints_test.index.services.IndexGeopointsService;
import com.ub.geopoints_test.tracks.exceptions.TrackNotExistException;
import com.ub.geopoints_test.tracks.models.TracksGeopointsDoc;
import com.ub.geopoints_test.tracks.routes.TracksGeopointsRoutes;
import com.ub.geopoints_test.tracks.services.TracksGeopointsService;
import com.ub.geopoints_test.tracks.views.SearchGeopointsTrackRequest;
import com.ub.geopoints_test.tracks.views.SearchGeopointsTrackResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class TracksGeopointsController {

    @Autowired
    private IndexGeopointsService indexGeopointsService;
    @Autowired
    private TracksGeopointsService tracksGeopointsService;
    @Autowired
    private FileService fileService;
    @Autowired
    private MySaxParser mySaxParser;


    @RequestMapping(value = TracksGeopointsRoutes.ALL, method = RequestMethod.GET)
    public String all(@RequestParam(required = false, defaultValue = "0") Integer currentPage, Model model){

        //List<TracksGeopointsDoc> tracks = tracksGeopointsService.findAllTracks();
        SearchGeopointsTrackRequest searchGeopointsTrackRequest = new SearchGeopointsTrackRequest(currentPage);
        SearchGeopointsTrackResponse searchGeopointsTrackResponse = tracksGeopointsService.findAll(searchGeopointsTrackRequest);
        Map<ObjectId, List<Double>> latsMap = new HashMap<>();
        Map<ObjectId, List<Double>> lonsMap = new HashMap<>();
        for (TracksGeopointsDoc track : searchGeopointsTrackResponse.getResult()){

            try {
                List<DotGeopointsDoc> dots = tracksGeopointsService.getAllTrackDots(track.getId());
                List<Double> lats = new ArrayList<>();
                List<Double> lons = new ArrayList<>();
                for (DotGeopointsDoc dotGeopointsDoc : dots){
                    lats.add(dotGeopointsDoc.getLat());
                    lons.add(dotGeopointsDoc.getLon());
                }
                latsMap.put(track.getId(), lats);
                lonsMap.put(track.getId(), lons);
                model.addAttribute("latsMap", latsMap);
                model.addAttribute("lonsMap", lonsMap);
            } catch (TrackNotExistException e) {
                e.printStackTrace();
            }

        }
        model.addAttribute("searchGeopointsTrackResponse", searchGeopointsTrackResponse);

        Breadcrumbs breadcrumbs = indexGeopointsService.breadcrumbs();
        breadcrumbs.setCurrentPageTitle("GPS треки");

        model.addAttribute("breadcrumbs", breadcrumbs);

        return "com.ub.geopoints_test.tracks.all";

    }

    @ResponseBody
    @RequestMapping(value = TracksGeopointsRoutes.UPLOAD, method = RequestMethod.POST)
    public String uploadTrack(@RequestParam MultipartFile file,
                      @RequestParam String name,
                      @RequestParam(required = false, defaultValue = "") String description,
                      Model model){

        TracksGeopointsDoc tracksGeopointsDoc = new TracksGeopointsDoc();

        if (name.equals("")) {
            return "1";
        }
        if (file.getSize() == 0 || file == null){
            return "2";
        }
        ObjectId fileId = fileService.saveWithDelete(file, null);
        tracksGeopointsDoc.setFile(fileId);
        tracksGeopointsDoc.setUploadDate(new Date());
        tracksGeopointsDoc.setName(name);
        tracksGeopointsDoc.setDescription(description);
        tracksGeopointsService.saveTrack(tracksGeopointsDoc);
        try {
            File convFile = tracksGeopointsService.convert(file);

            String s = convFile.getAbsolutePath();

            mySaxParser.setUpMySaxParser(convFile.getAbsolutePath(), tracksGeopointsDoc.getId(), convFile.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "ok";//RouteUtils.redirectTo("/");

    }

    @RequestMapping(value = TracksGeopointsRoutes.TRACK, method = RequestMethod.GET)
    public String track(@RequestParam("id") ObjectId id, Model model){

        TracksGeopointsDoc tracksGeopointsDoc = tracksGeopointsService.findById(id);
        if (tracksGeopointsDoc == null){
            throw new ResourceNotFoundException();
        }
        List<Double> lats = new ArrayList<>();
        List<Double> lons = new ArrayList<>();
        for (DotGeopointsDoc dotGeopointsDoc : tracksGeopointsDoc.getDots()){

            lats.add(dotGeopointsDoc.getLat());
            lons.add(dotGeopointsDoc.getLon());

        }
        Breadcrumbs breadcrumbs = indexGeopointsService.breadcrumbs();
        breadcrumbs.addLink(TracksGeopointsRoutes.ALL, "GPS треки");
        breadcrumbs.setCurrentPageTitle("Трек " + tracksGeopointsDoc.getName());

        model.addAttribute("lats", lats);
        model.addAttribute("lons", lons);
        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("track", tracksGeopointsDoc);

        return "com.ub.geopoints_test.tracks.track";

    }

    @RequestMapping(value = TracksGeopointsRoutes.EDIT, method = RequestMethod.GET)
    public String edit(@RequestParam ObjectId id, Model model){

        TracksGeopointsDoc tracksGeopointsDoc = tracksGeopointsService.findById(id);
        if (tracksGeopointsDoc == null){
            throw new ResourceNotFoundException();
        }
        List<Double> lats = new ArrayList<>();
        List<Double> lons = new ArrayList<>();
        for (DotGeopointsDoc dotGeopointsDoc : tracksGeopointsDoc.getDots()){

            lats.add(dotGeopointsDoc.getLat());
            lons.add(dotGeopointsDoc.getLon());

        }

        Breadcrumbs breadcrumbs = indexGeopointsService.breadcrumbs();
        breadcrumbs.addLink(TracksGeopointsRoutes.ALL, "GPS треки");
        breadcrumbs.setCurrentPageTitle("Редактировать трек " + tracksGeopointsDoc.getName());

        model.addAttribute("lats", lats);
        model.addAttribute("lons", lons);
        model.addAttribute("breadcrumbs", breadcrumbs);
        model.addAttribute("track",tracksGeopointsDoc);

        return "com.ub.geopoints_test.tracks.edit";

    }

    @RequestMapping(value = TracksGeopointsRoutes.EDIT, method = RequestMethod.POST)
    public String edit(@RequestParam String name,
                       @RequestParam(required = false, defaultValue = "") String description,
                       @RequestParam ObjectId id,
                       Model model){

        TracksGeopointsDoc tracksGeopointsDoc = tracksGeopointsService.findById(id);
        if (tracksGeopointsDoc == null){
            throw new ResourceNotFoundException();
        }
        if (name.equals("")){
            model.addAttribute("errorName", true);
            model.addAttribute("messageName", "Введите название трека");
        } else {
            tracksGeopointsDoc.setDescription(description);
            tracksGeopointsDoc.setName(name);
            tracksGeopointsService.saveTrack(tracksGeopointsDoc);
        }

        return RouteUtils.redirectTo(TracksGeopointsRoutes.ALL);

    }

    @RequestMapping(value = TracksGeopointsRoutes.DELETE, method = RequestMethod.GET)
    public String delete(@RequestParam("id") ObjectId id, Model model){

        tracksGeopointsService.deleteTrack(id);

        return RouteUtils.redirectTo(TracksGeopointsRoutes.ALL);

    }

}
