<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<div class="row">
    <div class="col-md-12">
        <div class="col-md-6" id="map-track">
        </div>
        <script>
            var map = L.map('map-track');
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
            <div class="col-md-6">
                <p><b>Название трека</b> <a href="${track.file}" download><i class="fa fa-download"></i> Скачать трек</a> </p>
                <p>${track.name}</p>
                <c:if test="${track.description ne ''}">
                    <p><b>Описание</b></p>
                    <p>${track.description}</p>
                </c:if>
                <c:if test="${track.description eq ''}">
                    <p><b>Описание</b></p>
                    <p>Описание отсутствует</p>
                </c:if>
            </div>
            <div class="col-md-6">
                Сюда нужно будет вставить статистику по треку. Перепад высот, скорость и т.д.
            </div>
        </div>
    </div>
</div>