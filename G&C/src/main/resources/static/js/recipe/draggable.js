$(function () {
    $('.wrapper').sortable({
        stop: function (event, ui) {
            reorderStepID();
        }
    });
})