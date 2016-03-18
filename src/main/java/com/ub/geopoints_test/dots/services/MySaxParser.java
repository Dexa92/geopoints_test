package com.ub.geopoints_test.dots.services;

import com.ub.core.security.service.AutorizationService;
import com.ub.core.security.service.exceptions.UserNotAutorizedException;
import com.ub.geopoints_test.dots.models.DotGeopointsDoc;
import com.ub.geopoints_test.tracks.models.TracksGeopointsDoc;
import com.ub.geopoints_test.tracks.services.TracksGeopointsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MySaxParser extends DefaultHandler{

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private TracksGeopointsService tracksGeopointsService;
    @Autowired
    private AutorizationService autorizationService;

    private List<DotGeopointsDoc> dotGeopointsDocList;
    private String xmlFileName;
    private String tmpValue;
    private DotGeopointsDoc dotGeopointsDoc;
    private TracksGeopointsDoc tracksGeopointsDoc;
    private String extention;
    private boolean ifLat;
    private boolean ifLon;
    private boolean ifEle;

    public void setUpMySaxParser(String bookXmlFileName, ObjectId id, String fileName) {
        this.xmlFileName = bookXmlFileName;
        tmpValue = "";
        dotGeopointsDocList = new ArrayList<DotGeopointsDoc>();
        dotGeopointsDoc = new DotGeopointsDoc();
        tracksGeopointsDoc = new TracksGeopointsDoc();
        extention = fileName.split("[.]")[1];
        ifLat = false;
        ifLon = false;
        ifEle = false;
        List<TracksGeopointsDoc> tracks = tracksGeopointsService.findAllTracks();
        if (tracks.size() != 0) {
            tracksGeopointsDoc = mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), TracksGeopointsDoc.class);
            if (tracksGeopointsDoc == null) tracksGeopointsDoc = new TracksGeopointsDoc();
        }
        parseDocument();
    }

    private void parseDocument() {
        // parse
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(xmlFileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }

    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {
        // if current element is book , create new book
        // clear tmpValue on start of element
        //this.dotGeopointsDoc = new DotGeopointsDoc();
        if (extention.equals("gpx")) {
            if (elementName.equalsIgnoreCase("trkpt")) {
                dotGeopointsDoc.setId(new ObjectId());
                dotGeopointsDoc.setLat(new Double(attributes.getValue("lat")));
                dotGeopointsDoc.setLon(new Double(attributes.getValue("lon")));
            }
            if (elementName.equalsIgnoreCase("ele")){
                ifEle = true;
            }
        } else if (extention.equals("kml")){

            if (elementName.equalsIgnoreCase("coordinates")){
               // String coordinates =
            }

        }

    }

    @Override
    public void endElement(String s, String s1, String element) throws SAXException {
        if (element.equalsIgnoreCase("trkpt")) {
            dotGeopointsDoc.setTrackId(tracksGeopointsDoc.getId());
            dotGeopointsDocList.add(dotGeopointsDoc);
            mongoTemplate.save(dotGeopointsDoc);
            dotGeopointsDoc = new DotGeopointsDoc();
        }
        if (element.equalsIgnoreCase("trk")) {
            tracksGeopointsDoc.setDots(dotGeopointsDocList);
            try {
                tracksGeopointsDoc.setUserId(autorizationService.getUserFromSession().getId());
            } catch (UserNotAutorizedException e) {
                e.printStackTrace();
            }
            mongoTemplate.save(tracksGeopointsDoc);
        }
        /*if (element.equalsIgnoreCase("isbn")) {
            bookTmp.setIsbn(tmpValue);
        }
        if (element.equalsIgnoreCase("title")) {
            bookTmp.setTitle(tmpValue);
        }
        if(element.equalsIgnoreCase("author")){
            bookTmp.getAuthors().add(tmpValue);
        }
        if(element.equalsIgnoreCase("price")){
            bookTmp.setPrice(Integer.parseInt(tmpValue));
        }
        if(element.equalsIgnoreCase("regDate")){
            try {
                bookTmp.setRegDate(sdf.parse(tmpValue));
            } catch (ParseException e) {
                System.out.println("date parsing error");
            }
        }*/
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (ifEle) {
            Double d = Double.parseDouble(new String(ch, start, length));
            dotGeopointsDoc.setEle(d);
            ifEle = false;
        }
    }

}
