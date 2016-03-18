var TracksController = function(){

};

TracksController.prototype.uploadMenuShow = function () {

    $('#modal-upload-success').hide();
    $('#error-upload-track-file').hide();
    $('#error-upload-track-name').hide();
    $('#upload-track-success').hide();
    $('.modal-to-replace').show();

};

TracksController.prototype.hideErrorTrackName = function () {

    $('#error-upload-track-name').hide();

};

TracksController.prototype.uploadTrack = function () {

    var name = $('#upload-track-name').val();
    var description = $('#upload-track-description').val();
    var file = document.getElementById('input-file').files[0];

    var isValidate = true;

    if (name.trim() == '') {
        $('#error-upload-track-name').show();
        isValidate = false;
    }
    if (!file) {
        $('#error-upload-track-file').show();
        isValidate = false;
    }
    if (!isValidate) {
        return;
    }

    $.ajax({
        url: '/tracks/upload',
        type: 'post',
        data: {
            file: file,
            name: name,
            description: description
        },
        success: function (data){

            if (data == 1) {
                $('#error-upload-track-file').hide();
                $('#error-upload-track-name').show();
            } else if (data == 2) {
                $('#error-upload-track-name').hide();
                $('#error-upload-track-file').show();
            } else {

                $('.modal-to-replace').hide();
                $('.modal-upload-success').show();
            }

        }
    })

};

TracksController.prototype.init = function () {

    var that = this;

    $('#upload-file-dialog').on('click', function () {
        that.uploadMenuShow();
        $('#upload-track-modal').modal('toggle');
        return false;
    });

    $('.upload-track').on('click', function () {
        that.uploadTrack();
        return false;
    });

    $('#upload-track-name').on('click', function () {
        that.hideErrorTrackName();
        return false;
    });

    $('#delete-track').on('click', function () {
        $('#delete-track-modal').modal('toggle');
        return false;
    });

};

$(function(){
    var tracksController = new TracksController();
    tracksController.init();
});