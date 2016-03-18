<%@ page import="com.ub.geopoints_test.tracks.routes.TracksGeopointsRoutes" %>
<%@ page import="com.ub.geopoints_test.user.routes.UserGeopointsRoutes" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<div class="row">
    <div class="col-md-12">
        <c:forEach var="track" items="${searchGeopointsTrackResponse.result}" varStatus="count">
            <div class="row insert-map lk-map" id="lk-map-${count.index}">
                <script>
                    var r = document.getElementById("lk-map-${count.index}");
                    var d = document.createElement("div");
                    var lats = [];
                    var lons = [];
                    lats = ${latsMap.get(track.id)};
                    lons = ${lonsMap.get(track.id)};
                    d.className += "col-md-1 col-md-offset-3";
                    d.id = "map" + "${count.index}";
                    r.appendChild(d);
                    d.style.height = document.getElementById("map" + "${count.index}").offsetWidth + "px";
                    r.appendChild(d);
                    var map = L.map('map' + '${count.index}', { zoomControl:false });
                    // create the tile layer with correct attribution
                    var osmUrl='http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
                    var osm = new L.TileLayer(osmUrl);

                    map.addLayer(osm);

                    var wps = [];
                    for (var j = 0; j < lats.length; j++){
                        wps.push({latLng: L.latLng(lats[j], lons[j])});

                    }
                    var simplifiedWps = simplify(wps, 1.0);
                    L.Routing.control({
                        waypoints: simplifiedWps,
                        createMarker: function(idx, wp, n) {if (idx == 0 || idx == n - 1) L.marker(wp.latLng).addTo(map);},
                        show: false
                    }).addTo(map);
                </script>
                <div class="col-md-5">
                    <p><b>Название</b></p>
                    <c:url value="<%= TracksGeopointsRoutes.TRACK%>" var="trackUrl">
                        <c:param name="id" value="${track.id}"/>
                    </c:url>
                    <p><a href="${trackUrl}">${track.name}</a>
                        <c:if test="${userAuthorized eq true}">
                            <c:if test="${userDoc.id eq track.userId}">
                                <c:url value="<%= TracksGeopointsRoutes.EDIT%>" var="editUrl">
                                    <c:param name="id" value="${track.id}"/>
                                </c:url>
                                <a href="${editUrl}">
                                    <i class="fa fa-pencil-square-o"></i>
                                </a>
                                <a href="#"><i class="fa fa-trash-o" id="delete-track" data-id="${track.id}"></i></a>
                            </c:if>
                        </c:if>
                    </p>
                    <p>${track.description}</p>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<c:if test="${searchGeopointsTrackResponse.result.size() > 0}">
    <div class="row">
        <div class="col-md-12 text-center">
            <ul class="pagination pagination-sm">
                <c:url value="<%= UserGeopointsRoutes.ROOT%>" var="urlPrev">
                    <c:param name="currentPage" value="${searchGeopointsTrackResponse.prevNum()}"/>
                </c:url>
                <li>
                    <a href="${urlPrev}"><i class="entypo-left-open-mini"></i> </a>
                </li>

                <c:forEach items="${searchGeopointsTrackResponse.paginator()}" var="page">
                    <c:url value="<%= UserGeopointsRoutes.ROOT%>" var="url">
                        <c:param name="currentPage" value="${page}"/>
                    </c:url>
                    <li>
                        <a href="${url}"
                           class="<c:if test="${searchGeopointsTrackResponse.currentPage eq page}">active</c:if> ">${page+1}</a>
                    </li>
                </c:forEach>

                <c:url value="<%= UserGeopointsRoutes.ROOT%>" var="urlNext">
                    <c:param name="currentPage" value="${searchGeopointsTrackResponse.nextNum()}"/>
                </c:url>
                <li><a href="${urlNext}"><i class="entypo-right-open-mini"></i> </a></li>
            </ul>
        </div>
    </div>
</c:if>