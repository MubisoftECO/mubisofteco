$(document).ready(function(){
    $('#role-dropdown').on('change', function(){
        let roleId = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/user/view/getUsers/' + roleId,
            success: function(operationResult) {
                let result = JSON.parse(operationResult);
                let s = '';

                for (let res of result) {
                    s += '<tr>';
                    s += '<th scope="row">' + res.id + '</th>';
                    s += '<td>' + res.name + '</td>';
                    s += '<td>' + res.secondName + '</td>';
                    s += '<td>' + res.username + '</td>';
                    s += '<td><a class="btn btn-danger" href=' + '"/user/delete/' + res.id +'"' + ' role="button">Delete</a></td>';
                    s += '</tr>'
                }
                $('#tbody').html(s);
            }
        });
    });
});