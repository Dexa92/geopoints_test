<%@ page import="com.ub.geopoints_test.user.routes.UserGeopointsRoutes" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<div class="row">
    <div class="col-md-12">
            <ul class="nav navbar-nav">
                <li>
                    <a href="/">Главная</a>
                </li>
                <li>
                    <a href="#">GPS треки</a>
                </li>
                <li>
                    <a href="#">О проекте</a>
                </li>
                <c:if test="${userAuthorized ne true}">
                    <li>
                        <a href="#" class="menu-registration">Зарегистрироваться</a>
                    </li>
                    <li>
                        <a href="#" class="menu-login">Войти</a>
                    </li>
                </c:if>
                <c:if test="${userAuthorized}">
                    <li>
                        <a href="<%= UserGeopointsRoutes.ROOT%>">Личный кабинет</a>
                    </li>
                    <li>
                        <a class="menu-logout" href="#">Выйти</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</div>