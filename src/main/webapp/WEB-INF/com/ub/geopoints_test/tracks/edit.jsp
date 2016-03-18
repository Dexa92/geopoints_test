<%@ page import="com.ub.geopoints_test.tracks.routes.TracksGeopointsRoutes" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<div class="col-md-12">
    <div class="col-md-6 map-edit" id="map-edit" style="height: 525px">

    </div>
    <script>
        var map = L.map('map-edit');
        // create the tile layer with correct attribution
        var osmUrl='http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
        var osmAttrib='Map data © <a href="http://openstreetmap.org">OpenStreetMap</a> contributors';
        var osm = new L.TileLayer(osmUrl, {minZoom: 1, maxZoom: 18, attribution: osmAttrib});
        var lats = [];
        var lons = [];
        lats = ${lats};
        lons = ${lons};
        map.addLayer(osm);

        var wps = [];
        for (var j = 0; j < lats.length; j++){
            wps.push({latLng: L.latLng(lats[j], lons[j])});

        }
        L.Routing.control({
            waypoints: wps,
            createMarker: function(idx, wp, n) {if (idx == 0 || idx == n - 1) L.marker(wp.latLng).addTo(map);},
            show: false
        }).addTo(map);
    </script>
    <div class="col-md-6">
        <form action="<%= TracksGeopointsRoutes.EDIT%>" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="name">Название трека</label>
                <input class="form-control" name="name" id="name" type="text" value="${track.name}" placeholder="Введите название трека" required>
                <input class="form-control" name="id" type="text" value="${track.id}" style="display: none">
            </div>
            <c:if test="${errorName eq true}">
                <div class="alert alert-danger">${messageName}</div>
            </c:if>
            <div class="form-group">
                <label for="description">Описание</label>
                <textarea class="form-control" name="description" id="description" rows="4" placeholder="Введите описание трека">${track.description}</textarea>
            </div>
            <button type="submit" class="btn btn-success"><i class="fa fa-check"></i> Сохранить</button>
            <button class="btn btn-danger" id="delete-track" data-id="${track.id}"><i class="fa fa-trash-o"></i> Удалить трек</button>
        </form>
    </div>
</div>

<jsp:include page="modals/deleteTrackModal.jsp"/>