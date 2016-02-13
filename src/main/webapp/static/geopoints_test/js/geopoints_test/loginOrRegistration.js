var LoginOrRegistrationController = function(){

};

LoginOrRegistrationController.prototype.loginMenu = function () {

    $('.modal-to-replace').show();
    $('.modal-forgot-password').hide();
    $('.modal-forgot-password-success').hide();
    $('.modal-registration-success').hide();
    $('#registration-email').val('');
    $('#registration-password').val('');
    $('#login-modal .login').addClass('active');
    $('#login-modal .registration').removeClass('active');
    $('#registration').hide();
    $('#login').show();
    $('#user-not-exist').hide();

};

LoginOrRegistrationController.prototype.login = function () {

    var email = $('#login-email').val();
    var password = $('#login-password').val();

    /*if (password.trim() == '') {
     $('#empty-password').show();
     }
     if (email.trim() == '' || !validateEmail(email)) {
     $('#error-email-format').show();
     }*/
    $.ajax({
        url: '/login',
        type: 'post',
        data: {
            email: email,
            password: password
        },
        success: function (data) {
            if (data == 1) {
                $('#user-not-exist').show();
            }
            else {
                location.reload(true);
            }
        },
        error: function (data) {

        }
    });
};

LoginOrRegistrationController.prototype.registrationMenu = function () {
    $('#login').hide();
    $('#error-format-email').hide();
    $('#too-short-password').hide();
    $('#registration-successful').hide();
    $('#user-exist').hide();
    $('.modal-registration-success').hide();
    $('#registration-email').val('');
    $('#registration-name').val('');
    $('#registration-password').val('');
    $('#login-modal .login').removeClass('active');
    $('#login-modal .registration').addClass('active');
    $('#registration').show();

};

LoginOrRegistrationController.prototype.registration = function () {
    var email = $('#registration-email').val();
    /*var name = $('#registration-name').val();*/
    var password = $('#registration-password').val();
    var isValidate = true;

    if (email.trim() == '' || !validateEmail(email)) {
        $('#error-format-email').show();
        isValidate = false;
    }
    if (password.trim() == '') {
        $('#too-short-password').show();
        isValidate = false;
    }
    if (!isValidate) {
        return;
    }

    $.ajax({
        url: '/registration',
        type: 'post',
        data: {
            email: email,
            password: password/*,
             name: name*/
        },
        success: function (data) {
            if (data == 1) {
                $('#error-format-email').hide();
                $('#too-short-password').hide();
                $('#registration-successful').hide();
                $('#user-exist').show();
            } else if (data == 2) {
                $('#user-exist').hide();
                $('#error-format-email').hide();
                $('#registration-successful').hide();
                $('#too-short-password').show();
            } else if (data == 3) {
                $('#user-exist').hide();
                $('#too-short-password').hide();
                $('#registration-successful').hide();
                $('#error-format-email').show();
            } else {
                $('#user-exist').hide();
                $('#too-short-password').hide();
                $('#error-format-email').hide();
                $('.modal-to-replace').hide();
                $('.modal-registration-success').show();
            }
            $('#login-modal').modal('show');
        }/*,
         error: function(data) {
         if (data == 1) {
         $('#user-exist').show();
         } else if (data == 2) {
         $('#too-short-password').show();
         }
         $('#login-modal').modal('show');
         }*/
    });
};

LoginOrRegistrationController.prototype.hideErrorEmailFormat = function () {

    $('#error-format-email').hide();

};

LoginOrRegistrationController.prototype.hideErrorpasswordLength = function () {

    $('#too-short-password').hide();

};

LoginOrRegistrationController.prototype.logout = function () {
    $.ajax({
        url: '/logout',
        type: 'get',
        success: function () {
            location.reload(true);
        },
        error: function () {

        }
    });
};

LoginOrRegistrationController.prototype.init = function () {

    var that = this;

    $('.menu-registration').on('click', function () {
        that.registrationMenu();
        $('#login-modal').modal('toggle');
        return false;
    });

    $('.menu-login').on('click', function () {
        that.loginMenu();
        $('#login-modal').modal('toggle');
        return false;
    });

    $('.login').on('click', function () {
        that.loginMenu();
    });

    $('.registration').on('click', function () {
        that.registrationMenu();
    });

    $('.btn-login').on('click', function () {
        that.login();
    });

    $('.btn-registrate').on('click', function () {
        that.registration();
        return false;
    });

    $('.menu-logout').on('click', function () {
        that.logout();
        return false;
    });

    $('#registration-email').on('click', function () {
        that.hideErrorEmailFormat();
        return false;
    });

    $('#registration-password').on('click', function () {
        that.hideErrorpasswordLength();
        return false;
    });

};

$(function(){
    var loginOrRegistrationController = new LoginOrRegistrationController();
    loginOrRegistrationController.init();
});

function validateEmail(email) {
    var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
    return re.test(email);
}