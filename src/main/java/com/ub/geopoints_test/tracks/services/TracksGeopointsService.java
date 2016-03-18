
package com.ub.geopoints_test.tracks.services;

import com.ub.geopoints_test.dots.models.DotGeopointsDoc;
import com.ub.geopoints_test.tracks.exceptions.TrackNotExistException;
import com.ub.geopoints_test.tracks.models.TracksGeopointsDoc;
import com.ub.geopoints_test.tracks.views.SearchGeopointsTrackRequest;
import com.ub.geopoints_test.tracks.views.SearchGeopointsTrackResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class TracksGeopointsService {

    @Autowired
    MongoTemplate mongoTemplate;

    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public List<TracksGeopointsDoc> findAllTracks(){

        return mongoTemplate.findAll(TracksGeopointsDoc.class);

    }

    public SearchGeopointsTrackResponse findAll(SearchGeopointsTrackRequest searchGeopointsTrackRequest){

        Sort sort = new Sort(Sort.Direction.DESC, "uploadDate");
        Pageable pageable = new PageRequest(
                searchGeopointsTrackRequest.getCurrentPage(),
                searchGeopointsTrackRequest.getPageSize(),
                sort);

        Criteria criteria = new Criteria();
        Query query = new Query(criteria);
        Long count = mongoTemplate.count(query, TracksGeopointsDoc.class);

        query = query.with(pageable);

        List<TracksGeopointsDoc> result = mongoTemplate.find(query, TracksGeopointsDoc.class);

        SearchGeopointsTrackResponse response = new SearchGeopointsTrackResponse(
                searchGeopointsTrackRequest.getCurrentPage(),
                searchGeopointsTrackRequest.getPageSize(),
                result);
        response.setAll(count.intValue());
        return response;

    }

    public List<TracksGeopointsDoc> findAllTracksAvailable(int limit){

        Query query = new Query(new Criteria("available").is(true)).limit(limit).with(new Sort(Sort.Direction.DESC, "uploadDate"));
        return mongoTemplate.find(query, TracksGeopointsDoc.class);

    }

    public TracksGeopointsDoc saveTrack(TracksGeopointsDoc tracksGeopointsDoc){
        if (tracksGeopointsDoc != null)
        mongoTemplate.save(tracksGeopointsDoc);

        return tracksGeopointsDoc;

    }

    public List<DotGeopointsDoc> getAllTrackDots(ObjectId trackId) throws TrackNotExistException {

        if (trackId != null){
            Criteria criteria = Criteria.where("trackId").is(trackId);
            return mongoTemplate.find(new Query(criteria), DotGeopointsDoc.class);
        } else {
            throw new TrackNotExistException();
        }

    }

    public TracksGeopointsDoc findByUrl(String url){

        return mongoTemplate.findOne(new Query(Criteria.where("url").is(url)), TracksGeopointsDoc.class);

    }

    public TracksGeopointsDoc findById(ObjectId id){

        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), TracksGeopointsDoc.class);

    }

    public void deleteTrack(ObjectId id){

        TracksGeopointsDoc tracksGeopointsDoc = findById(id);
        mongoTemplate.remove(tracksGeopointsDoc);

    }

    public SearchGeopointsTrackResponse findAllTracksOfUser(SearchGeopointsTrackRequest searchGeopointsTrackRequest, ObjectId userId){

        Sort sort = new Sort(Sort.Direction.DESC, "uploadDate");
        Pageable pageable = new PageRequest(
                searchGeopointsTrackRequest.getCurrentPage(),
                searchGeopointsTrackRequest.getPageSize(),
                sort);

        Query query = new Query(Criteria.where("userId").is(userId));
        Long count = mongoTemplate.count(query, TracksGeopointsDoc.class);

        query = query.with(pageable);

        List<TracksGeopointsDoc> result = mongoTemplate.find(query, TracksGeopointsDoc.class);

        SearchGeopointsTrackResponse response = new SearchGeopointsTrackResponse(
                searchGeopointsTrackRequest.getCurrentPage(),
                searchGeopointsTrackRequest.getPageSize(),
                result);
        response.setAll(count.intValue());
        return response;

    }

}
