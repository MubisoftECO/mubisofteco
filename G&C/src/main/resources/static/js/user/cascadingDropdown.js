$(document).ready(function(){
    $('#ac-dropdown').on('change', function(){
        let acId = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/user/create/getProvince/' + acId,
            success: function(operationResult) {
                let result = JSON.parse(operationResult);
                let s = '<option  value="" disabled selected>Province</option>';
                for(let i = 0; i < result.length; i++) {
                    s += '<option value="' + result[i].id + '">' + result[i].name + '</option>';
                }
                $('#province-dropdown').html(s);
            }
        });
    });
    $('#province-dropdown').on('change', function(){
        let provinceId = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/user/create/getCity/' + provinceId,
            success: function(operationResult) {
                let result = JSON.parse(operationResult);
                let s = '<option  value="" disabled selected>City</option>';
                for(let i = 0; i < result.length; i++) {
                    s += '<option value="' + result[i].id + '">' + result[i].name + '</option>';
                }
                $('#city-dropdown').html(s);
            }
        });
    });
});