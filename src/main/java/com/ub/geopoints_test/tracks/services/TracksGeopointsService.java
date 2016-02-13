
package com.ub.geopoints_test.tracks.services;

import com.ub.geopoints_test.tracks.models.TracksGeopointsDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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

    /*public void parseFile(MultipartFile file) throws IOException {

        File newFile = convert(file);

        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

                boolean blon = false;
                boolean blat = false;
                boolean bele = false;
                //boolean bsalary = false;

                public void startElement(String uri, String localName,String qName,
                                         Attributes attributes) throws SAXException {

                    //System.out.println("Start Element :" + qName);

                    if (qName.equalsIgnoreCase("lon")) {
                        blon = true;
                    }

                    if (qName.equalsIgnoreCase("lat")) {
                        blat = true;
                    }

                    if (qName.equalsIgnoreCase("ele")) {
                        bele = true;
                    }

                }

                public void endElement(String uri, String localName,
                                       String qName) throws SAXException {

                    // System.out.println("End Element :" + qName);

                }

                public void characters(char ch[], int start, int length) throws SAXException {

                    DotGeopointsDoc dotGeopointsDoc = new DotGeopointsDoc();
                    if (blon) {
                        dotGeopointsDoc.setLon(new String(ch, start, length));
                        blon = false;
                    }

                    if (blat) {
                        dotGeopointsDoc.setLat(new String(ch, start, length));
                        blat = false;
                    }

                    if (bele) {
                        dotGeopointsDoc.setEle(new String(ch, start, length));
                        bele = false;
                    }
                    */
/*try {
                        save(dotGeopointsDoc);
                    } catch (DotExistException e) {
                        e.printStackTrace();
                    }*//*


                }

            };

            saxParser.parse(newFile, handler);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public List<TracksGeopointsDoc> findAllTracks(){

        List<TracksGeopointsDoc> tracks = mongoTemplate.findAll(TracksGeopointsDoc.class);

        return tracks;

    }

    public TracksGeopointsDoc saveTrack(TracksGeopointsDoc tracksGeopointsDoc){
        if (tracksGeopointsDoc != null)
        mongoTemplate.save(tracksGeopointsDoc);

        return tracksGeopointsDoc;

    }

}

    /*public DotGeopointsDoc save(DotGeopointsDoc dotGeopointsDoc) throws DotExistException{

        if (dotGeopointsDoc.getId() == null) {
            DotGeopointsDoc old = findById(dotGeopointsDoc.getId());
            if (old != null) {
                throw new DotExistException();
            }
        }

        mongoTemplate.save(dotGeopointsDoc);
        return dotGeopointsDoc;

    }

    public DotGeopointsDoc findById(ObjectId id){

        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), DotGeopointsDoc.class);

    }

}
*/
