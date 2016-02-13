var TracksController = function(){

};

/*TracksController.prototype.openFile = function () {
    var file =
    $.ajax({
        url: '/download',
        type: 'post',
        data: {file: file},
        success: function (){

        }
    })

};*/

TracksController.prototype.downloadMenuShow = function () {
    $('#download-file-dialog').trigger('click');
}

TracksController.prototype.init = function () {

    var that = this;

    $('#download-track').on('click', function () {
        that.openFile();
        return false;
    });

};

$(function(){
    var tracksController = new TracksController();
    tracksController.init();
});