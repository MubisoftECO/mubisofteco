$(document).ready(function(){
    $('#role-dropdown').on('change', function(){
        var roleId = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/user/view/getUsers/' + roleId,
            success: function(result) {
                var result = JSON.parse(result);
                var s = '';

                for(var i = 0; i < result.length; i++) {
                    s += '<tr>';
                    s += '<th scope="row">' + result[i].id + '</th>';
                    s += '<td>' + result[i].name + '</td>';
                    s += '<td>' + result[i].secondName + '</td>';
                    s += '<td>' + result[i].username + '</td>';
                    s += '<td><a class="btn btn-danger" href=' + '"/user/delete/' + result[i].id +'"' + 'role="button">Delete</a></td>';
                    s += '</tr>'
                }
                $('#tbody').html(s);
            }
        });
    });
});