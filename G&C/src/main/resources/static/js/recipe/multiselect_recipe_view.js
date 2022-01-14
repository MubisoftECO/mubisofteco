$(document).ready(function(){
    $('#flag-multiselect').on('hidden.bs.select', function(){
        document.getElementById("form-select-pages").submit();
    });
});
