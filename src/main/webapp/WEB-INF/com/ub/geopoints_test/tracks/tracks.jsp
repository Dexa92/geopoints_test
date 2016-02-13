<%@ page import="com.ub.geopoints_test.tracks.routes.TracksGeopointsRoutes" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<div class="col-md-12">
    <form action="<%= TracksGeopointsRoutes.TRACKS%>" method="post" enctype="multipart/form-data">

        <div class="form-group">
            <label for="input-file">Загрузить трек</label>
            <input name="file" type="file" id="input-file">
        </div>

        <button type="submit" class="btn btn-default">Загрузить</button>
    </form>
</div>