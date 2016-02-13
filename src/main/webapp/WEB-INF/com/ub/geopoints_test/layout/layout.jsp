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
<div class="container">
    <jsp:include page="components/header.jsp"/>
    <tiles:insertAttribute name="content"/>
    <script type="text/javascript">
        var map = new ol.Map({
            target: 'map',
            layers: [
                new ol.layer.Tile({
                    source: new ol.source.MapQuest({layer: 'osm'})
                })
            ],
            view: new ol.View({
                center: ol.proj.fromLonLat([44.51,48.71]),
                zoom: 4
            })
        });
    </script>
    <jsp:include page="components/footer.jsp"/>
    <jsp:include page="../user/modals/modalLoginOrRegistration.jsp"/>
</div>

<script src="/static/geopoints_test/js/jquery-1.11.3.js"></script>
<script src="/static/geopoints_test/js/bootstrap.js"></script>
<script src="/static/geopoints_test/js/geopoints_test/loginOrRegistration.js"></script>

</body>
</html>
