$(document).ready(function(){
    $('#ac-dropdown').on('change', function(){
        var acId = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/user/create/getProvince/' + acId,
            success: function(result) {
                var result = JSON.parse(result);
                var s = '';
                s+='<option  value="" disabled selected>Province</option>';
                for(var i = 0; i < result.length; i++) {
                    s += '<option value="' + result[i].id + '">' + result[i].name + '</option>';
                }
                $('#province-dropdown').html(s);
            }
        });
    });
    $('#province-dropdown').on('change', function(){
        var provinceId = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/user/create/getCity/' + provinceId,
            success: function(result) {
                var result = JSON.parse(result);
                var s = '';
                s+='<option  value="" disabled selected>City</option>';
                for(var i = 0; i < result.length; i++) {
                    s += '<option value="' + result[i].id + '">' + result[i].name + '</option>';
                }
                $('#city-dropdown').html(s);
            }
        });
    });
});