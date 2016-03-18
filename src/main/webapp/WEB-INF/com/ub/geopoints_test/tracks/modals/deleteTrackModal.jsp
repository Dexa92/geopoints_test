<%@ page import="com.ub.geopoints_test.tracks.routes.TracksGeopointsRoutes" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<div class="modal fade" id="delete-track-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <p>
                    Вы уверены, что хотите удалить трек <b>${track.name}</b>?
                </p>
            </div>
            <div class="modal-footer">
                <div class="col-md-12">
                    <c:url value="<%= TracksGeopointsRoutes.DELETE%>" var="deleteTrack">
                        <c:param name="id" value="${track.id}"/>
                    </c:url>
                    <a href="${deleteTrack}"><button class="btn btn-danger"><i class="fa fa-trash-o"></i> Удалить трек</button></a>
                    <button class="btn btn-default" id="cancel-delete" data-dismiss="modal">Отмена</button>
                </div>
            </div>
        </div>
    </div>
</div>