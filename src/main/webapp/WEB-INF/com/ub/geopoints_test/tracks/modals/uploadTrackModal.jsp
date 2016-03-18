<%@ page import="com.ub.geopoints_test.tracks.routes.TracksGeopointsRoutes" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<div class="modal fade" id="upload-track-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-to-replace">
                <div class="row">
                    <div class="modal-body">
                        <div class="col-md-12">
                            <form action="<%= TracksGeopointsRoutes.UPLOAD%>" method="post" enctype="multipart/form-data">
                                <div class="form-group">
                                    <label for="upload-track-name">Название трека</label>
                                    <input class="form-control" name="name" id="upload-track-name" type="text" placeholder="Введите название трека" required>
                                </div>
                                <div class="alert alert-danger" id="error-upload-track-name" style="display: none">
                                    Введите название трека
                                </div>
                                <div class="form-group">
                                    <label for="upload-track-description">Описание</label>
                                    <textarea class="form-control" name="description" id="upload-track-description" rows="4" placeholder="Введите описание трека"></textarea>
                                </div>
                                <div class="form-group">
                                    <label for="input-file">Загрузить трек</label>
                                    <input name="file" type="file" id="input-file">
                                </div>
                                <div class="alert alert-danger" id="error-upload-track-file" style="display: none">
                                    Выберите файл трека для загрузки
                                </div>

                                <button type="submit" class="btn btn-default upload-track">Загрузить</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-upload-success" style="display: none">
                <div class="modal-body">
                    <div class="text-center">
                        <div class="form-group">
                            <label>Трек был успешно загружен</label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>