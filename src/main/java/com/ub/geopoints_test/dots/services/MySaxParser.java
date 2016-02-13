package com.ub.geopoints_test.dots.services;

import com.ub.geopoints_test.dots.models.DotGeopointsDoc;
import com.ub.geopoints_test.tracks.models.TracksGeopointsDoc;
import com.ub.geopoints_test.tracks.services.TracksGeopointsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySaxParser extends DefaultHandler{

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private TracksGeopointsService tracksGeopointsService;

    private List<DotGeopointsDoc> dotGeopointsDocList;
    private String xmlFileName;
    private String tmpValue;
    private DotGeopointsDoc dotGeopointsDoc;
    private TracksGeopointsDoc tracksGeopointsDoc = new TracksGeopointsDoc();

    public void setUpMySaxParser(String bookXmlFileName, File file) {
        this.xmlFileName = bookXmlFileName;
        dotGeopointsDocList = new ArrayList<DotGeopointsDoc>();
        dotGeopointsDoc = new DotGeopointsDoc();
        List<TracksGeopointsDoc> tracks = tracksGeopointsService.findAllTracks();
        if (tracks.size() != 0) {
            tracksGeopointsDoc = mongoTemplate.findOne(new Query(Criteria.where("file").is(file)), TracksGeopointsDoc.class);
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
        if (elementName.equalsIgnoreCase("trkpt")) {
            dotGeopointsDoc.setId(new ObjectId());
            dotGeopointsDoc.setLat(attributes.getValue("lat"));
            dotGeopointsDoc.setLon(attributes.getValue("lon"));
            //dotGeopointsDoc.setEle(attributes.getValue("ele"));
            //currentDotGeopointsDoc = dotGeopointsDoc;
        }
        /*if (elementName.equalsIgnoreCase("ele")) {
            dotGeopointsDoc.setEle((attributes.getValue("ele")));
            //currentDotGeopointsDoc = dotGeopointsDoc;
        }*/


    }

    @Override
    public void endElement(String s, String s1, String element) throws SAXException {
        // if end of book element add to list
        if (element.equals("trkpt")) {
            dotGeopointsDocList.add(dotGeopointsDoc);
            mongoTemplate.save(dotGeopointsDoc);
            dotGeopointsDoc = new DotGeopointsDoc();
        }
        if (element.equalsIgnoreCase("trk")) {
            tracksGeopointsDoc.setDots(dotGeopointsDocList);
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
    public void characters(char[] ac, int i, int j) throws SAXException {
        tmpValue = new String(ac, i, j);
    }

}
