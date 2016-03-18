<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Geopoints</title>
    <link rel="stylesheet" href="/static/a/css/font-icons/entypo/css/entypo.css"/>
    <link rel="stylesheet" href="/static/a/css/font-icons/font-awesome/css/font-awesome.css"/>
    <link rel="stylesheet" href="/static/geopoints_test/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/static/geopoints_test/css/custom.css"/>
    <link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.css"/>
    <link rel="stylesheet" href="/static/geopoints_test/js/leaflet-routing-machine-2.6.2/css/leaflet-routing-machine.css"/>

    <script src="/static/geopoints_test/js/leaflet/leaflet.js"></script>
    <script src="/static/geopoints_test/js/jquery-1.11.3.js"></script>
    <script src="/static/geopoints_test/js/leaflet-routing-machine-2.6.2/dist/leaflet-routing-machine.js"></script>
    <script src="/static/geopoints_test/js/simplify-js-master/simplify.js"></script>
</head>
<body>
<div class="container">
    <jsp:include page="components/header.jsp"/>
    <tiles:insertAttribute name="content"/>
    <jsp:include page="components/footer.jsp"/>
    <jsp:include page="../user/modals/modalLoginOrRegistration.jsp"/>
    <jsp:include page="../tracks/modals/uploadTrackModal.jsp"/>
</div>

<script src="/static/geopoints_test/js/bootstrap.js"></script>
<script src="/static/geopoints_test/js/geopoints_test/loginOrRegistration.js"></script>
<script src="/static/geopoints_test/js/geopoints_test/tracks.js"></script>

</body>
</html>
