$(document).ready(function(){
    $('#flag-multiselect').on('hidden.bs.select', buildUrl);
    $('#search-submit').on('click', buildUrl);

    $("#search-box").on('keydown',function (event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            buildUrl();
        }
    });

    $("#create_recipe").on('click', function (e) {
        updateCounter(e);
    })

});

function buildUrl(){
    let select = document.getElementById("flag-multiselect");
    let options = select && select.options;
    let result = [];

    for (let opt of options) {
        if (opt.selected) {
            result.push(opt.value);
            console.log(opt.value);
        }
    }
    let url = "/recipe/view"
    for (let res of result) {
        if (url.indexOf('?') === -1) {
            url = url + '?flags=' + res;
        }else {
            url = url + '&flags=' + res;
        }
    }

    let keyword = document.getElementById("search-box").value;
    if (url.indexOf('?') === -1) {
        url = url + "?keyword=" + keyword;
    }else {
        url = url + "&keyword=" + keyword;
    }

    window.location = url;
}


function updateCounter(e) {
    let id = e.target.id;
    let parameters = {id : id};

    if (e.target.tagName === "A") {
        $.post("/metric/counter",parameters);
    }
}