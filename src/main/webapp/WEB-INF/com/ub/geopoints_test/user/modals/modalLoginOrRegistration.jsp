<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<div class="modal fade" id="login-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-to-replace">
                <div class="col-xs-6 login active modal-padding">
                    <a href="#login">Войти</a>
                </div>
                <div class="col-xs-6 registration modal-padding">
                    <a href="#registration">Регистрация</a>
                </div>
                <div class="tab-content modal-padding">
                    <div class="tab-pane active" id="login">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="login-email" class="contact-information-black">Адрес электронной
                                        почты</label>
                                    <input class="form-control info-about-user" id="login-email" name="login-email"
                                           type="email" required="true" placeholder="Введите ваш email"/>
                                </div>
                                <div class="form-group">
                                    <label for="login-password" class="contact-information-black">Пароль</label>
                                    <input class="form-control info-about-user" id="login-password"   required="true"
                                           name="login-password" type="password" placeholder="Введите ваш пароль"/>
                                </div>
                                <div id="user-not-exist" style="display: none">
                                    <div class="alert alert-danger">
                                        Неверный логин/пароль
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button class="btn-login">Войти</button>
                                    <a class="forget-underline-blue" id="forgot-password" href="#">Забыли пароль?</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane" id="registration">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="registration-email" class="contact-information-black">Адрес электронной
                                        почты</label>
                                    <input class="form-control info-about-user" id="registration-email"   required="true"
                                           name="registration-email" type="email" placeholder="Введите ваш email"/>
                                </div>
                                <div id="user-exist" style="display: none">
                                    <div class="alert alert-danger">
                                        Пользователь с таким email уже зарегестрирован
                                    </div>
                                </div>
                                <div id="error-format-email" style="display: none">
                                    <div class="alert alert-danger">
                                        Неверный формат email
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="registration-password" class="contact-information-black">Пароль</label>
                                    <input class="form-control info-about-user" id="registration-password"
                                           name="registration-password" type="password"  required="true"
                                           placeholder="Введите ваш пароль"/>
                                </div>
                                <div id="too-short-password" style="display: none">
                                    <div class="alert alert-danger">
                                        Пароль должен содержать латинские буквы, цифры, спец символы и быть не менее 6 символов.
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button class="btn-registrate">Регистрация</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-forgot-password" style="display: none">
                <div class="modal-header text-center">
                    <b>Восстановление пароля</b>
                </div>
                <div class="modal-body">
                    <div class="form-group text-center" style="margin: 0 90px;">
                        <label for="password-forgot" style="padding-left: 0px;">Введите email для восстановления пароля</label>
                        <input class="form-control info-about-user" id="password-forgot" type="text" value="">
                    </div>
                    <div class="text-center message-recovery-password-email-not-exist" style="display: none;">
                        <div class="form-group">
                            <label>Пользователь с таким email не зарегестрирован. Проверьте введенный email и повторите попытку.</label>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="text-center">
                        <button class="filter-btn" id="password-recover">Восстановить</button>
                    </div>
                </div>
            </div>
            <div class="modal-forgot-password-success" style="display: none">
                <div class="modal-header text-center">
                    <b>Восстановление пароля</b>
                </div>
                <div class="modal-body">
                    <div class="text-center">
                        <div class="form-group">
                            <label>Вам на email было отправлено письмо. Для восстановления пароля перейдите по ссылке в письме.</label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-registration-success" style="display: none">
                <div class="modal-header text-center">
                    <b>Регистрация прошла успешно</b>
                </div>
                <div class="modal-body">
                    <div class="text-center">
                        <div class="form-group">
                            <label>Для окончания регистрации подтвердите свой email. На ваш почтовый адрес выслано
                                письмо со ссылкой для подтверждения.</label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>