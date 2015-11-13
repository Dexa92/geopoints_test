<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Geopoints</title>
    <link rel="stylesheet" href="/static/geopoints_test/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/static/geopoints_test/css/custom.css"/>
    <link rel="stylesheet" href="http://openlayers.org/en/v3.11.1/css/ol.css" type="text/css">
    <script src="http://openlayers.org/en/v3.11.1/build/ol.js" type="text/javascript"></script>
</head>
<body>
<tiles:insertAttribute name="content"/>
<script type="text/javascript">
    var map = new ol.Map({
        target: 'map',
        layers: [
            new ol.layer.Tile({
                source: new ol.source.MapQuest({layer: 'sat'})
            })
        ],
        view: new ol.View({
            center: ol.proj.fromLonLat([37.41, 8.82]),
            zoom: 4
        })
    });
</script>

</body>
</html>
